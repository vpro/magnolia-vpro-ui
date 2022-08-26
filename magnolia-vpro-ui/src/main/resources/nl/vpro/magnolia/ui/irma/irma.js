
function sign(url, text, attribute, target, debugging=false) {
    if (url === null) {
        url = "https://pop.waag.nl"
    }
    pop_sign(url,
        attribute,
        text, debugging)
        .then(result => {
            document.getElementById(target).value = result;
            nl.vpro.magnolia.ui.irma.callBack("Success", result);
        }).catch(error => {
        if (error === 'Aborted') {
            console.log('We closed it ourselves, so no problem 😅');
            return;
        }
        console.error("Couldn't do what you asked 😢", error);
        nl.vpro.magnolia.ui.irma.callBack(error);
    });
}


//calls irma server specified in url
//to sign the message, using the (disclosed) attribute name
function pop_sign(url, attribute, message, debugging){
    let options = {
        // Developer options
        debugging: debugging,
        language:  'en',
        translations: {
            header:  'Use your <i class="irma-web-logo">IRMA</i> app to sign with your email address',
            loading: 'Just one second please!'
        },
        session: {
            url: url,
            start: {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "@context": "https://irma.app/ld/request/signature/v2",
                    "message" : message,
                    "disclose": [
                        [
                            [ attribute ]
                        ]
                    ]
                })
            }
        }
    };

    let irmaPopup = irma.newPopup(options);

    //return the promise
    return irmaPopup.start().then(result => {
        //strip and encode the signature
        var signature = result.signature;
        if (debugging) {
            console.log("result", JSON.parse(JSON.stringify(result))); // console.log is asynchrounus, and result will be changed in next line
        }
        signature.message = undefined;
        return btoa(JSON.stringify(signature));
    })
}


function sign(url, normalized_text, attribute, target) {
    if (url === null) {
        url = "https://pop.waag.nl"
    }
    pop_sign(url,
        attribute,
        normalized_text)
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
function pop_sign(url, attribute, normalized_message){
    let options = {
        // Developer options
        debugging: false,
        language:  'en',
        translations: {
            header:  'Use your <i class="irma-web-logo">IRMA</i> app to sign with your email adress',
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
                    "message" : normalized_message,
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
        signature.message = undefined;
        return btoa(JSON.stringify(signature));
    })
}

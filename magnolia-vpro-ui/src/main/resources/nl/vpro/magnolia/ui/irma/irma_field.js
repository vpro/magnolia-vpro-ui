// Integrates vaadin field with irma
// The irma javascript itself is loaded dynamiclly via

function irma_sign(url, attribute, message, target, debugging=false) {
    if (url === null) {
        url = "https://pop.waag.nl"
    }
    irma_pop(url,
        attribute,
        message)
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

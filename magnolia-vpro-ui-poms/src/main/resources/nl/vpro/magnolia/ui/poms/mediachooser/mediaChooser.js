window.nl_vpro_magnolia_ui_field_mediachooser = function(id, mediaType, property) {

    nl_vpro_media_CMSSelector.select(function (result) {
        var el = $("#" + id);
        el.val(result[property]);
        el.blur();
    }, {
        returnValue: "data",
        mediaType:  mediaType
    });


};

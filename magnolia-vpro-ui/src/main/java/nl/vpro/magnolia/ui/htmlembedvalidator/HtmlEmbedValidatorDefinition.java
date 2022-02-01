/*
 * Copyright (C) 2018 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.htmlembedvalidator;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author r.jansen
 */
@ValidatorType("htmlEmbedValidator")
public class HtmlEmbedValidatorDefinition extends ConfiguredFieldValidatorDefinition {

    public HtmlEmbedValidatorDefinition() {
        setFactoryClass(HtmlEmbedValidatorFactory.class);
        setErrorMessage("embed.invalid");
        setName("htmlEmbedValidator");
    }

    @Getter
    @Setter
    private String htmlInvalidMessage = "embed.invalid.html";

    @Getter
    @Setter
    private String uriInvalidMessage = "embed.invalid.uri";


}

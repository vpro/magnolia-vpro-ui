/*
 * Copyright (C) 2018 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.htmlembedvalidator;


import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.field.AbstractFieldValidatorFactory;

import javax.inject.Inject;

import com.vaadin.data.Validator;

/**
 * @author r.jansen
 */
public class HtmlEmbedValidatorFactory extends AbstractFieldValidatorFactory<HtmlEmbedValidatorDefinition, String> {

    private final SimpleTranslator translator;
    @Inject
    public HtmlEmbedValidatorFactory(HtmlEmbedValidatorDefinition definition, SimpleTranslator simpleTranslator) {
        super(definition);
        this.translator = simpleTranslator;
    }

    @Override
    public Validator<String> createValidator() {
        return new HtmlEmbedValidator(translator, definition);
    }
}

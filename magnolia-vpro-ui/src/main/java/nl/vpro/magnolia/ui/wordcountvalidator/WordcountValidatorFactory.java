/*
 * Copyright (C) 2016 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.wordcountvalidator;


import info.magnolia.ui.field.AbstractFieldValidatorFactory;

import com.vaadin.data.Validator;

/**
 * @author rico
 */
public class WordcountValidatorFactory extends AbstractFieldValidatorFactory<WordcountValidatorDefinition, String> {
    public WordcountValidatorFactory(WordcountValidatorDefinition definition) {
        super(definition);
    }

    @Override
    public Validator<String> createValidator() {
        return new WordcountValidator(definition);
    }
}

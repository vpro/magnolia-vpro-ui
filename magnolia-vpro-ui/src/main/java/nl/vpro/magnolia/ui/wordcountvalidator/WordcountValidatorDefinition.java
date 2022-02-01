/*
 * Copyright (C) 2016 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.wordcountvalidator;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rico
 */
@ValidatorType("wordcountValidator")
public class WordcountValidatorDefinition extends ConfiguredFieldValidatorDefinition {

    @Getter
    @Setter
    private int wordcount = 10;

    @Getter
    @Setter
    private boolean parseHtml;

    public WordcountValidatorDefinition() {
        setFactoryClass(WordcountValidatorFactory.class);
        setName("wordcountValidator");
    }
}

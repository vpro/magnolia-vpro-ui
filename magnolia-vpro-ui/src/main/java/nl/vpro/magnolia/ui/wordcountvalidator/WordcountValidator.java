/*
 * Copyright (C) 2016 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.wordcountvalidator;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

/**
 * @author rico
 */
public class WordcountValidator extends AbstractValidator<String> {
    private final WordcountValidatorDefinition definition;

    public WordcountValidator(WordcountValidatorDefinition definition) {
        super(definition.getErrorMessage());
        this.definition = definition;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, isValidValue(value));
    }

    protected boolean isValidValue(String value) {
        return countWords(value) <= definition.getWordcount();
    }

    protected int countWords(String value) {
        String body;
        if (definition.isParseHtml()) {
            Document document = Jsoup.parseBodyFragment(value);
            body = document.text();
        } else {
            body = value;
        }
        if (StringUtils.isNotEmpty(body)) {
            return body.split("\\W+").length;
        }
        return 0;
    }
}

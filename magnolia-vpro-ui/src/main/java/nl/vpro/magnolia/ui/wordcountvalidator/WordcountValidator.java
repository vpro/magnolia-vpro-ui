/*
 * Copyright 2022 VPRO
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
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

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
package nl.vpro.magnolia.ui.urlvalidator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import nl.vpro.validation.URI;
import nl.vpro.validation.URIValidator;

/**
 * URL Validator (base on {@link URIValidator}. Not actually using Validation framework. I think it may be feasible to make
 * a validator that simply point to some method in some class.
 */
@URI(mustHaveScheme = true, minHostParts = 2, allowEmptyString = true, lenient = false)
public class URLValidator extends AbstractValidator<String> {

    final URIValidator validator = new URIValidator();

    protected URLValidator(String errorMessage) {
        super(errorMessage);
        validator.initialize(this.getClass().getAnnotation(URI.class));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        boolean valid = validator.isValid(value, null);
        return toResult(value, valid);
    }
}

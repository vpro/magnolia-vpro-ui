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

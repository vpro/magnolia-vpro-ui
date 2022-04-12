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
package nl.vpro.magnolia.ui.validation;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.*;

@ValidatorType("beanValidator")
public class BeanFieldValidatorDefinition extends ConfiguredFieldValidatorDefinition {

    @Getter
    @Setter
    private String bean;

    @Getter
    @Setter
    private String property;

    @Getter
    @Setter
    private Class<?>[] groups = new Class<?>[] {};

    private Class<?> beanClass;

    public BeanFieldValidatorDefinition() {
        setFactoryClass(BeanFieldValidatorFactory.class);
    }

    Class<?> getBeanClass() throws ClassNotFoundException {
        if (beanClass == null) {
            beanClass = Class.forName(getBean());
        }
        return beanClass;
    }
}

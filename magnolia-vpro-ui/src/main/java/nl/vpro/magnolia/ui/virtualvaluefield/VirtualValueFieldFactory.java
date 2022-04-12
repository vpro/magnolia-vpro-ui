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
package nl.vpro.magnolia.ui.virtualvaluefield;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.field.factory.AbstractFieldFactory;

import javax.inject.Inject;
import javax.jcr.Node;

import com.vaadin.ui.Component;


/**
 */
public class VirtualValueFieldFactory extends AbstractFieldFactory<String, VirtualValueFieldDefinition> {
    protected final ValueContext<Node> valueContext;

    @Inject
    public VirtualValueFieldFactory(
        VirtualValueFieldDefinition definition,
        ComponentProvider componentProvider,
        ValueContext<Node> valueContext) {
        super(definition, componentProvider);
        this.valueContext = valueContext;
    }

    @Override
    protected Component createFieldComponent() { // It seems that this must return a field
        // so just wrap it in something silly
        return new VirtualValueField(definition.calculateValue(valueContext), definition.getName());
    }

}

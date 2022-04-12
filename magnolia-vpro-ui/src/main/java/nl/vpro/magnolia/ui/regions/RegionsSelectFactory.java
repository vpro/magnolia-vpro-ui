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
package nl.vpro.magnolia.ui.regions;

import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.ComboBoxFieldFactory;

import javax.inject.Inject;

import org.meeuw.i18n.regions.Region;

import com.vaadin.server.Page;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Dependency;

public class RegionsSelectFactory extends ComboBoxFieldFactory<Region, RegionSelectFieldDefinition> {

    @Inject
    public RegionsSelectFactory(
        RegionSelectFieldDefinition definition,
        ComponentProvider componentProvider,
        RegionsSelectFieldSupport selectFieldSupport) {
        super(definition, componentProvider, selectFieldSupport);
    }

    @Override
    protected ComboBox<Region> createFieldComponent() {
        ComboBox<Region> component = super.createFieldComponent();
        // we can try to use published resources, but I can't figure it out.
        // (it's easier with plain vaadin)
        // Let's then use resource servlet of magnolia itself
        component.addStyleNames("vpro-ui");
        Page.getCurrent().addDependency(
            new Dependency(Dependency.Type.STYLESHEET, MgnlContext.getWebContext().getContextPath() + "/.resources/vpro-ui/vpro-ui.css")
        );
        return component;
    }
}

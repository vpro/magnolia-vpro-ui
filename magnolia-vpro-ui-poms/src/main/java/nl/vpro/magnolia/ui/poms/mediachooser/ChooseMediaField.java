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
package nl.vpro.magnolia.ui.poms.mediachooser;

import lombok.Getter;

import java.util.UUID;

import javax.inject.Named;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

/**
 * @author rico
 * @since 3.0
 * Based on info.magnolia.ui.form.field.LinkField
 */
@JavaScript("mediaChooser.js")
public class ChooseMediaField extends CustomField<String> {
    // Define layout and component
    private final VerticalLayout rootLayout = new VerticalLayout();
    private final HorizontalLayout linkLayout = new HorizontalLayout();

    @Getter
    private final TextField textField = new TextField();

    @Getter
    private final Button selectButton = new NativeButton();

    private final ChooseMediaFieldDefinition definition;
    private final String cmsSelector;

    public ChooseMediaField(
        ChooseMediaFieldDefinition definition,
        @Named("poms.baseUrl") String pomsBaseUrl) {
        this .definition = definition;
        this.cmsSelector = pomsBaseUrl + "/CMSSelector/media2.js";
    }

    @Override
    protected Component initContent() {
        Page.getCurrent().addDependency
            (new Dependency(Dependency.Type.JAVASCRIPT, cmsSelector));

        addStyleName("linkfield");
        // Initialize root
        rootLayout.setSizeFull();
        rootLayout.setSpacing(true);
        // Handle Text Field
        textField.setWidth(100, Unit.PERCENTAGE);
        textField.setId(UUID.randomUUID().toString());
        // Handle Link Layout (Text Field & Select Button)
        linkLayout.setSizeFull();
        linkLayout.addComponent(textField);
        linkLayout.setExpandRatio(textField, 1);
        linkLayout.setComponentAlignment(textField, Alignment.MIDDLE_LEFT);
        // Only Handle Select button if the Text field is not Read Only.
        if (!textField.isReadOnly()) {
            selectButton.addStyleName("magnoliabutton");
            selectButton.setCaption(definition.getButtonLabel());
            selectButton.setDescription(definition.getButtonLabel());
            linkLayout.addComponent(selectButton);
            linkLayout.setExpandRatio(selectButton, 0);
            linkLayout.setComponentAlignment(selectButton, Alignment.MIDDLE_RIGHT);
        }
        rootLayout.addComponent(linkLayout);

        return rootLayout;
    }

    @Override
    public String getValue() {
        return textField.getValue();
    }

    @Override
    protected void doSetValue(String newValue) {
        if (newValue == null) {
            newValue = "";
        }
        textField.setValue(newValue);
    }

}

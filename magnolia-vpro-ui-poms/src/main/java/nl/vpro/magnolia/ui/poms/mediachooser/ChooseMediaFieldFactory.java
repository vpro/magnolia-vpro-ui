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

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * @author rico
 * @since 3.0
 */
@Log4j2
public class ChooseMediaFieldFactory extends AbstractFieldFactory<String, ChooseMediaFieldDefinition> {
    private final ChooseMediaField mediaField;

    @Inject
    public ChooseMediaFieldFactory(
        ChooseMediaFieldDefinition definition,
        ComponentProvider componentProvider,
        @Named("poms.baseUrl") String pomsBaseUrl
        ) {
        super(definition, componentProvider);
        this.mediaField = new ChooseMediaField(definition, pomsBaseUrl);
    }

    @Override
    protected Component createFieldComponent() {
        mediaField.getSelectButton().addClickListener(
            createButtonClickListener()
        );
        return mediaField;
    }

    /**
     * Create the Button click Listener. On click: Create a Dialog and
     * Initialize callback handling.
     */
    private Button.ClickListener createButtonClickListener() {
        return event -> {
            String value = mediaField.getTextField().getValue();
            String id = mediaField.getTextField().getId();
            String mediaType = definition.getMediaType() == null ? "" : definition.getMediaType().stream().map(Enum::name).collect(Collectors.joining(","));
            String property = definition.getMediaProperty();
            Page.getCurrent().getJavaScript().execute(
                String.format("window.nl_vpro_magnolia_ui_field_mediachooser('%s', '%s', '%s')",
                    id, mediaType, property));
        };
    }

}

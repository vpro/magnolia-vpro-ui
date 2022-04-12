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

import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import nl.vpro.i18n.Displayable;
import nl.vpro.domain.media.MediaType;

/**
 * @author rico
 * @since 3.0
 */
@Setter
@Getter
@FieldType("mediaChooserField")
public class ChooseMediaFieldDefinition extends ConfiguredFieldDefinition<String> {

    private String buttonLabel = null;
    private List<MediaType> mediaType;
    private String mediaProperty = "mid";


    public ChooseMediaFieldDefinition() {
        setFactoryClass(ChooseMediaFieldFactory.class);
    }

    public String getButtonLabel() {
        if (buttonLabel != null) {
            return buttonLabel;
        } else if (mediaType == null || mediaType.isEmpty()) {
            return "Kies uit " + MediaType.MEDIA.getDisplayName().toLowerCase();
        } else {
            return "Kies een " + mediaType.stream()
                .map(Displayable::getDisplayName)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" of een "));
        }

    }

}

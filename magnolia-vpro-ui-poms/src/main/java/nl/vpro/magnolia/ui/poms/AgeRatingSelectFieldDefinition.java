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
package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import java.util.regex.Pattern;

import com.vaadin.data.ValueContext;

import nl.vpro.domain.media.AgeRating;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 2.26
 */
@FieldType("ageRatingField")
public class AgeRatingSelectFieldDefinition extends AbstractEnumFieldDefinition<AgeRating> {

    private static final Pattern ICON_SUFFICES = Pattern.compile("^[0-9_]+$");
    public AgeRatingSelectFieldDefinition() {
        super(AgeRating.class);
    }

    @Override
    protected String convertToPresentation(String value, ValueContext context) {
        if (isUseIcons() && value != null && ICON_SUFFICES.matcher(value).matches()) {
            return "";
        }
        return super.convertToPresentation(value, context);
    }


}


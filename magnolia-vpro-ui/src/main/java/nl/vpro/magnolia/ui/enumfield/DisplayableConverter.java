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
package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import nl.vpro.i18n.Displayable;
import nl.vpro.i18n.Locales;

public class DisplayableConverter<E extends Enum<E> & Displayable> extends EnumConverter<E> {


    private final boolean filter;

    public DisplayableConverter(Class<E> clazz, boolean filter) {
        super(clazz);
        this.filter = filter;
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        return value.getDisplayName(
            context.getLocale().orElse(Locales.getDefault())
        ).getValue();
    }

    @Override
    public String getIcon(E value) {
        return value.getIcon().orElse(null);
    }

    @Override
    public boolean filter(E value) {
        return (! filter) || value.display();
    }
}

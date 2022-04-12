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

import com.vaadin.ui.*;

/**
 * Just wraps a {@link Label} in a field, so that it can be used to show some information in a form.
 */
class VirtualValueField extends CustomField<String> {

    private final String value;
    private final String name;

    public VirtualValueField(String value, String name) {
        this.value = value;
        this.name = name;
    }


    @Override
    protected Component initContent() {
        Label field = new Label(value);
        field.addStyleNames("vpro-ui", "vpro-ui-virtualfield", "vpro-ui-virtualfield-" + name);
        return field;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    protected void doSetValue(String value) {

    }
}

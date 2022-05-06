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
package nl.vpro.magnolia.ui.damwithedit;

import info.magnolia.dam.api.Item;
import info.magnolia.dam.app.field.DamLinkFieldDefinition;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.ui.editor.ItemPreviewDefinition;
import info.magnolia.ui.field.FieldType;

import java.util.Arrays;

import lombok.Getter;

import lombok.Setter;

import nl.vpro.magnolia.ui.linkfieldvalidator.LinkFieldValidatorDefinition;

@FieldType("damLinkFieldWithEdit")
public class DamLinkFieldWithEditButtonDefinition extends DamLinkFieldDefinition {

    @Getter
    @Setter
    private boolean showEditButton = false;

    public DamLinkFieldWithEditButtonDefinition() {
        super();
        setTextInputAllowed(true);
        LinkFieldValidatorDefinition linkFieldValidatorDefinition = new LinkFieldValidatorDefinition();
        linkFieldValidatorDefinition.setRepository(DamConstants.WORKSPACE);
        setValidators(Arrays.asList(linkFieldValidatorDefinition));
        //setChooserId("dam-app-core:chooser");
    }

    static ItemPreviewDefinition<Item> getPreviewDefinition() {
        ItemPreviewDefinition<Item> previewDef = new ItemPreviewDefinition<>();
        previewDef.setImplementationClass(DamItemPreviewComponentWithEditButton.class);
        return previewDef;
    }

    @Override
    protected ItemPreviewDefinition<Item> getItemPreviewDefinition() {
        if (showEditButton) {
            return getPreviewDefinition();
        } else {
            return super.getItemPreviewDefinition();
        }
    }


}

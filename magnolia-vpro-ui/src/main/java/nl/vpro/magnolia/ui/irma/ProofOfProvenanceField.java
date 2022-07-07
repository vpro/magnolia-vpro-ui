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
package nl.vpro.magnolia.ui.irma;

import info.magnolia.ui.ValueContext;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.jcr.Node;

import com.vaadin.ui.*;

import nl.vpro.irma.ProofOfProvenanceService;

/**
 * @author Michiel Meeuwissen
 * @since 1.4
 */
@Log4j2
public class ProofOfProvenanceField extends CustomField<SignedText> {

    @Getter
    private final transient ValueContext<Node> valueContext;
    private final ProofOfProvenanceFieldDefinition definition;

    private final AbstractOrderedLayout layout;
    private final TextArea text;
    private final TextArea signature;
    private boolean signatureDirty = false;

    private final ProofOfProvenanceService proofOfProvenanceService;

    public ProofOfProvenanceField(ValueContext<Node> valueContext, ProofOfProvenanceFieldDefinition definition, ProofOfProvenanceService proofOfProvenanceService

    ) {
        this.valueContext = valueContext;
        this.definition = definition;
        this.proofOfProvenanceService = proofOfProvenanceService;
        setCaption(definition.getLabel());
        setRequiredIndicatorVisible(definition.isRequired());
        addStyleName(definition.getStyleName());
        layout = new VerticalLayout();
        layout.addStyleName("proofOfProvenance");
        text = new TextArea();
        signature = new TextArea();
    }

    @Override
    protected Component initContent() {
        Button button = new Button();
        button.setCaption("Sign with Irma");
        //button.setIcon();
        text.addValueChangeListener(
            event -> {
                if (! signatureDirty) {
                    signature.setValue("value change " + event);
                }
            }
        );

        text.addBlurListener(
            event -> {
                if (! signatureDirty) {
                    signature.setValue("value blur" + event);
                }
            }
        );
        signature.addBlurListener(
            event -> {
                signatureDirty = true;
            }

        );
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                signature.setValue(proofOfProvenanceService.sign(text.getValue()));
                signatureDirty = false;
            }
        });
        layout.addComponent(text);
        layout.addComponent(signature);
        layout.addComponent(button);
        return layout;
    }

    @Override
    public SignedText getValue() {
        return new SignedText(text.getValue(), signature.getValue());
    }

    @Override
    protected void doSetValue(SignedText value) {
        if (value.getText() != null) {
            text.setValue(value.getText());
        }
        if (value.getSignature() != null) {
            signature.setValue(value.getSignature());
        }
    }
}

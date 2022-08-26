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
import info.magnolia.ui.field.FieldDefinition;
import info.magnolia.ui.field.TextFieldDefinition;
import info.magnolia.ui.field.factory.FormFieldFactory;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.util.UUID;

import javax.jcr.Node;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import nl.vpro.irma.ProofOfProvenanceService;

import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;

/**
 * @author Michiel Meeuwissen
 * @since 1.4
 */
@Log4j2
@JavaScript({"irma_field.js"})
@StyleSheet({"irma_field.css"})
public class ProofOfProvenanceField extends CustomField<SignedText> {

    @Getter
    private final transient ValueContext<Node> valueContext;
    private final ProofOfProvenanceFieldDefinition definition;

    private final AbstractOrderedLayout layout;

    private final AbstractField<String> text;
    private final TextArea signature;

    private final ProofOfProvenanceService proofOfProvenanceService;


    public ProofOfProvenanceField(
        ValueContext<Node> valueContext,
        ProofOfProvenanceFieldDefinition definition,
        ProofOfProvenanceService proofOfProvenanceService,
        FormFieldFactory formFieldFactory) {
        this.valueContext = valueContext;
        this.definition = definition;
        this.proofOfProvenanceService = proofOfProvenanceService;
        setCaption(definition.getLabel());
        setRequiredIndicatorVisible(definition.isRequired());
        addStyleName(definition.getStyleName());
        layout = new VerticalLayout();
        layout.addStyleName("proofOfProvenance");

        FieldDefinition<String> textFieldDefinition = definition.getDefinition().getField();
        text = formFieldFactory.createField(textFieldDefinition != null ? textFieldDefinition : new TextFieldDefinition());
        signature = new TextArea();
        signature.setRows(1);
        signature.setId(UUID.randomUUID().toString());

    }

    @Override
    protected Component initContent() {
        for (URI javascript : proofOfProvenanceService.getJavaScripts()) {
            Page.getCurrent().addDependency
                (new Dependency(Dependency.Type.JAVASCRIPT, javascript.toString()));
        }

        final Button button = new Button();
        button.setCaption("Sign with Irma");

        button.addClickListener((Button.ClickListener) event -> {
            final String attribute = definition.getDefinition().getAttribute();
            final String url = proofOfProvenanceService.getBaseUrl();
            log.debug("Signing {}", text.getValue());
            Page.getCurrent().getJavaScript()
                .execute(
                    "irma_sign('" + escapeJavaScript(url) +
                        "','" + attribute +
                        "','" + escapeJavaScript(text.getValue()) +
                        "','" + signature.getId() +
                        "'," + proofOfProvenanceService.isDebugging() +
                        ")");
        });

        com.vaadin.ui.JavaScript.getCurrent().addFunction("nl.vpro.magnolia.ui.irma.callBack",
            (JavaScriptFunction) arguments -> {
                if ("Success".equals(arguments.getString(0))) {
                    signature.setValue(arguments.getString(1));
                }
            });

        layout.addComponent(text);
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponent(new Label("Signature:"));
        horizontalLayout.addComponent(signature);
        horizontalLayout.addComponent(button);
        layout.addComponent(horizontalLayout);
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

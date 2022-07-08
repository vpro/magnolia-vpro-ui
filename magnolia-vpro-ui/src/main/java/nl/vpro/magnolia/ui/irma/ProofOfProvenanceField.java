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

import elemental.json.JsonArray;
import info.magnolia.ui.ValueContext;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

import javax.jcr.Node;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
@JavaScript({"irma.js"})
@StyleSheet({"irma.css"})
public class ProofOfProvenanceField extends CustomField<SignedText> {

    @Getter
    private final transient ValueContext<Node> valueContext;
    private final ProofOfProvenanceFieldDefinition definition;

    private final AbstractOrderedLayout layout;
    private final TextArea text;
    private final TextArea signature;

    private final ProofOfProvenanceService proofOfProvenanceService;

    public ProofOfProvenanceField(
        ValueContext<Node> valueContext,
        ProofOfProvenanceFieldDefinition definition,
        ProofOfProvenanceService proofOfProvenanceService

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
        signature.setRows(1);
        signature.setId(UUID.randomUUID().toString());
    }

    @Override
    protected Component initContent() {
        for (String javascript : proofOfProvenanceService.getJavaScripts()) {
            Page.getCurrent().addDependency
                (new Dependency(Dependency.Type.JAVASCRIPT, javascript));
        }

        Button button = new Button();
        button.setCaption("Sign with Irma");

        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Document document = Jsoup.parse(text.getValue());
                String attribute = definition.getDefinition().getAttribute();
                String url = proofOfProvenanceService.getBaseUrl();
                Page.getCurrent().getJavaScript().execute("sign('" + escapeJavaScript(url) + "','" + escapeJavaScript(document.text()) + "','" + attribute + "','" + signature.getId() + "')");
            }
        });

        com.vaadin.ui.JavaScript.getCurrent().addFunction("nl.vpro.magnolia.ui.irma.callBack",
            new JavaScriptFunction() {
                @Override
                public void call(JsonArray arguments) {
                    if ("Success".equals(arguments.getString(0))) {
                        signature.setValue(arguments.getString(1));
                    }
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

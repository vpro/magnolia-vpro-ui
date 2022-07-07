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


import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.field.factory.AbstractFieldFactory;

import javax.inject.Inject;
import javax.jcr.Node;

import nl.vpro.irma.ProofOfProvenanceService;

/**
 * @author Michiel Meeuwissen
 * @since 1.4
 */
public class ProofOfProvenanceFieldFactory extends AbstractFieldFactory<SignedText, ProofOfProvenanceFieldDefinition> {

    private final ValueContext<Node> valueContext;

    private final ProofOfProvenanceService proofOfProvenanceService;


    @Inject
    public ProofOfProvenanceFieldFactory(
        ProofOfProvenanceFieldDefinition definition,
        ComponentProvider componentProvider,
        ValueContext<Node> valueContext,
        ProofOfProvenanceService proofOfProvenanceService) {
        super(definition, componentProvider);
        this.valueContext = valueContext;
        this.proofOfProvenanceService = proofOfProvenanceService;
    }

    @Override
    protected ProofOfProvenanceField createFieldComponent() {
        return new ProofOfProvenanceField(valueContext, definition, proofOfProvenanceService);
    }
}

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
package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import javax.jcr.*;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

@Slf4j
public class LinkFieldValidator extends AbstractValidator<Object> {

    private final LinkFieldValidatorDefinition definition;

    public LinkFieldValidator(LinkFieldValidatorDefinition definition) {
        super(definition.getErrorMessage());
        this.definition = definition;
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        Node node;
        if (value instanceof Node) {
            node = (Node) value;
        } else if (value instanceof CharSequence) {
            String s = value.toString();
            try {
                node = SessionUtil.getNodeByIdentifier(definition.getRepository(), UUID.fromString(s).toString());
            } catch (IllegalArgumentException invalideUUID) {
                node = SessionUtil.getNode(definition.getRepository(), value.toString());
            }
            if (node == null) {
                return ValidationResult.error("No such node " + s);
            }
        } else {
            throw new IllegalArgumentException("Don't know how to interpret " + value + " as a jcr link");
        }
        try {
            // To check against deleted nodes. Using MissingNode would be easier, but that is package private (again).

            if (node.isNodeType(NodeTypes.Deleted.NAME)) {
                log.error("Can't check if node is deleted for value: {}", value);
                return toResult(value, false);
            }
        } catch (RepositoryException re) {
            return toResult(value, false);
        }
        if (StringUtils.isNotEmpty(definition.getRepository())) {
            try {
                Session session = MgnlContext.getJCRSession(definition.getRepository());
                session.getNodeByIdentifier(node.getIdentifier());
                return toResult(value, true);
            } catch (ItemNotFoundException notfound) {
                return toResult(value, false);
            } catch (RepositoryException re) {
                log.error("Can't validate value: {}", value);
                return toResult(value, false);
            }
        } else {
            return toResult(value, false);
        }
    }
}

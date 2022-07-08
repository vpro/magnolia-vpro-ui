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
package nl.vpro.magnolia.ui.autojcrnameaction;

import info.magnolia.jcr.util.*;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.app.AppContext;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.ContentBrowserSubApp;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.datasource.ItemResolver;
import info.magnolia.ui.editor.EditorView;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.observation.DatasourceObservation;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;

import com.machinezoo.noexception.Exceptions;
import com.vaadin.data.BinderValidationStatus;

/**
 * @author rico
 */
public class AutoJcrNameCommitAction extends CommitAction<Node> {

    private final NodeNameHelper nodeNameHelper;

    private final LocationController locationController;
    private final AppContext appContext;
    private final ItemResolver<Node> itemResolver;

    @Inject
    public AutoJcrNameCommitAction(
        CommitActionDefinition definition,
        CloseHandler closeHandler,
        ValueContext<Node> valueContext,
        EditorView<Node> form,
        Datasource<Node> datasource,
        DatasourceObservation.Manual datasourceObservation,
        NodeNameHelper nodeNameHelper, LocationController locationController, AppContext appContext, ItemResolver<Node> itemResolver) {
        super(definition, closeHandler, valueContext, form, datasource, datasourceObservation);
        this.nodeNameHelper = nodeNameHelper;
        this.locationController = locationController;
        this.appContext = appContext;
        this.itemResolver = itemResolver;
    }

    protected String getPropertyName() {
        CommitActionDefinition definition = getDefinition();
        if (definition instanceof AutoJcrNameCommitActionDefinition) {
            return ((AutoJcrNameCommitActionDefinition) definition).getPropertyName();
        } else {
            return "title";
        }
    }

    protected String getBrowserName() {
        CommitActionDefinition definition = getDefinition();
        if (definition instanceof AutoJcrNameCommitActionDefinition) {
            return ((AutoJcrNameCommitActionDefinition) definition).getBrowserName();
        } else {
            return "browser";
        }
    }


    @Override
    public void execute() throws ActionExecutionException {
        super.execute();
        if (!getBrowserName().isEmpty() && getForm().validate().stream().allMatch(BinderValidationStatus::isOk)) {
            locationController.goTo(new ContentBrowserSubApp.BrowserLocation(
                appContext.getName(),
                getBrowserName(),
                getValueContext().getSingle()
                    .map(itemResolver::getId)
                    .orElse("")
            ));
        }
    }

    @Override
    protected void write() {
        final String propertyName = getPropertyName();

        this.getValueContext().get().forEach(Exceptions.wrap().consumer(node -> {
            final FormView<Node> form = (FormView<Node>) getForm();
            form.write(node);
            final String currentTitle = form.getPropertyValue(propertyName).map(Object::toString).orElseThrow(ActionExecutionException::new);
            final String validatedName = nodeNameHelper.getValidatedName(currentTitle);
            if (! validatedName.equals(node.getName())) {
                // title probably changed
                final String uniqueName = nodeNameHelper.getUniqueName(node.getParent(), validatedName);
                if (StringUtils.isNotBlank(uniqueName)) {
                    NodeUtil.renameNode(node, uniqueName);
                }
            }
            if (node.isNew()) {
                NodeTypes.Created.set(node);
            }
            this.getDatasource().commit(node);
            this.getDatasourceObservation().trigger();
        }));
    }
}


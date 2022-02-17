/*
 * Copyright (C) 2016 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.autojcrnameaction;

import info.magnolia.jcr.util.*;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.editor.EditorView;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.observation.DatasourceObservation;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;

import com.machinezoo.noexception.Exceptions;

/**
 * @author rico
 */
public class AutoJcrNameCommitAction extends CommitAction<Node> {

    private final NodeNameHelper nodeNameHelper;

    @Inject
    public AutoJcrNameCommitAction(
        CommitActionDefinition definition,
        CloseHandler closeHandler,
        ValueContext<Node> valueContext,
        EditorView<Node> form,
        Datasource<Node> datasource,
        DatasourceObservation.Manual datasourceObservation,
        NodeNameHelper nodeNameHelper) {
        super(definition, closeHandler, valueContext, form, datasource, datasourceObservation);
        this.nodeNameHelper = nodeNameHelper;
    }

    protected String getPropertyName() {
        CommitActionDefinition definition = getDefinition();
        if (definition instanceof AutoJcrNameCommitActionDefinition) {
            return  ((AutoJcrNameCommitActionDefinition) definition).getPropertyName();
        } else {
            return  "title";
        }
    }

    @Override
    protected void write() {
        final String propertyName = getPropertyName();

        this.getValueContext().get().forEach(Exceptions.wrap().consumer(node -> {
            FormView<Node> form = (FormView<Node>) getForm();
            form.write(node);
            String title = form.getPropertyValue(propertyName).map(Object::toString).orElseThrow(ActionExecutionException::new);
            title = nodeNameHelper.getUniqueName(node.getParent(), nodeNameHelper.getValidatedName(title));
            if (StringUtils.isNotBlank(title)) {
                NodeUtil.renameNode(node, title);
            }
            if (node.isNew()) {
                NodeTypes.Created.set(node);
            }
            this.getDatasource().commit(node);
            this.getDatasourceObservation().trigger();
        }));
    }
}

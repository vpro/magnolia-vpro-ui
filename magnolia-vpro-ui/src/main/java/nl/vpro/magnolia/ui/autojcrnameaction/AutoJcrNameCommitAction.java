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

    @Override
    public void write() {
        final String propertyName;
        CommitActionDefinition definition = getDefinition();
        if (definition instanceof AutoJcrNameCommitActionDefinition) {
            propertyName = ((AutoJcrNameCommitActionDefinition) definition).getPropertyName();
        } else {
            propertyName = "title";
        }
        this.getValueContext().getSingle().ifPresent(Exceptions.wrap().consumer(item -> {
            FormView<Node> form = (FormView<Node>) getForm();
            form.write(item);
            String title = form.getPropertyValue(propertyName).map(Object::toString).orElseThrow(ActionExecutionException::new);
            title = nodeNameHelper.getUniqueName(item.getParent(), nodeNameHelper.getValidatedName(title));
            if (StringUtils.isNotBlank(title)) {
                NodeUtil.renameNode(item, title);
            }
            if (item.isNew()) {
                NodeTypes.Created.set(item);
            }
            this.getDatasource().commit(item);
            this.getDatasourceObservation().trigger();
        }));
    }
}

/*
 * Copyright (C) 2017 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.virtualvaluefield;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.field.factory.AbstractFieldFactory;

import javax.inject.Inject;
import javax.jcr.Node;

import com.vaadin.ui.Component;


/**
 */
public class VirtualValueFieldFactory extends AbstractFieldFactory<String, VirtualValueFieldDefinition> {
    protected final ValueContext<Node> valueContext;

    @Inject
    public VirtualValueFieldFactory(
        VirtualValueFieldDefinition definition,
        ComponentProvider componentProvider,
        ValueContext<Node> valueContext) {
        super(definition, componentProvider);
        this.valueContext = valueContext;
    }

    @Override
    protected Component createFieldComponent() { // It seems that this must return a field
        // so just wrap it in something silly
        return new VirtualValueField(definition.calculateValue(valueContext), definition.getName());
    }

}

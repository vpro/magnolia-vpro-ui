/*
 * Copyright (C) 2017 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.virtualvaluefield;

import info.magnolia.ui.ValueContext;
import info.magnolia.ui.field.ConfiguredFieldDefinition;

import javax.jcr.Node;

/**
 * Abstract field definition to show some {@link #calculateValue(ValueContext)} as a field value in a form.
 */
public abstract class VirtualValueFieldDefinition extends ConfiguredFieldDefinition<String> {


    protected VirtualValueFieldDefinition() {
        setFactoryClass(VirtualValueFieldFactory.class);
    }


    protected abstract String calculateValue(ValueContext<Node> node);

}

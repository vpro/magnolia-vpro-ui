/*
 * Copyright (C) 2015 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.poms.mediachooser;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.log4j.Log4j2;

/**
 * @author rico
 * @since 3.0
 */
@Log4j2
public class ChooseMediaFieldFactory extends AbstractFieldFactory<String, ChooseMediaFieldDefinition> {
    private final ChooseMediaField mediaField;

    @Inject
    public ChooseMediaFieldFactory(
        ChooseMediaFieldDefinition definition,
        ComponentProvider componentProvider,
        @Named("poms.baseUrl") String pomsBaseUrl
        ) {
        super(definition, componentProvider);
        this.mediaField = new ChooseMediaField(definition, pomsBaseUrl);
    }

    @Override
    protected Component createFieldComponent() {
        mediaField.getSelectButton().addClickListener(
            createButtonClickListener()
        );
        return mediaField;
    }

    /**
     * Create the Button click Listener. On click: Create a Dialog and
     * Initialize callback handling.
     */
    private Button.ClickListener createButtonClickListener() {
        return event -> {
            String value = mediaField.getTextField().getValue();
            String id = mediaField.getTextField().getId();
            String mediaType = definition.getMediaType() == null ? "" : definition.getMediaType().stream().map(Enum::name).collect(Collectors.joining(","));
            String property = definition.getMediaProperty();
            Page.getCurrent().getJavaScript().execute(String.format("window.nl_vpro_magnolia_ui_field_mediachooser('%s', '%s', '%s')", id, mediaType, property));
        };
    }

}

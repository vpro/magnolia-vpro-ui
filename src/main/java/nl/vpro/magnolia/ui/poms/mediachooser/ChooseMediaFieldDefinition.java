/*
 * Copyright (C) 2015 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.poms.mediachooser;

import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import nl.vpro.domain.media.MediaType;
import nl.vpro.i18n.Displayable;

/**
 * @author rico
 * @since 3.0
 */
@Setter
@Getter
@FieldType("mediaChooserField")
public class ChooseMediaFieldDefinition extends ConfiguredFieldDefinition<String> {

    private String buttonLabel = null;
    private List<MediaType> mediaType;
    private String mediaProperty = "mid";


    public ChooseMediaFieldDefinition() {
        setFactoryClass(ChooseMediaFieldFactory.class);
    }

    public String getButtonLabel() {
        if (buttonLabel != null) {
            return buttonLabel;
        } else if (mediaType == null || mediaType.isEmpty()) {
            return "Kies een " + MediaType.MEDIA.getDisplayName();
        } else {
            return "Kies een " + mediaType.stream().map(Displayable::getDisplayName).map(String::toLowerCase).collect(Collectors.joining(" of een "));
        }

    }

}

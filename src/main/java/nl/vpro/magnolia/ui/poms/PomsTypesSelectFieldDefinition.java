package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import nl.vpro.domain.media.MediaType;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

@FieldType("pomsTypesField")
public class PomsTypesSelectFieldDefinition extends AbstractEnumFieldDefinition<MediaType> {

    public PomsTypesSelectFieldDefinition() {
        super(MediaType.class);
    }

}

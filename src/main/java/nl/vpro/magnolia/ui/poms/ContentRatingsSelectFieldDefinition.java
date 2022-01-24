package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import nl.vpro.domain.media.ContentRating;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("contentRatingsField")
public class ContentRatingsSelectFieldDefinition extends AbstractEnumFieldDefinition<ContentRating> {

    public ContentRatingsSelectFieldDefinition() {
        super(ContentRating.class);
        setMultiselect(true);
    }
}

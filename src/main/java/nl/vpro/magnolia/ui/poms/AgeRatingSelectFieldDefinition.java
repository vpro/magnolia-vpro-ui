package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import nl.vpro.domain.media.AgeRating;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 2.26
 */
@FieldType("ageRatingField")
public class AgeRatingSelectFieldDefinition extends AbstractEnumFieldDefinition<AgeRating> {


    public AgeRatingSelectFieldDefinition() {
        super(AgeRating.class);
    }

}


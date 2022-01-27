package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import java.util.regex.Pattern;

import com.vaadin.data.ValueContext;

import nl.vpro.domain.media.AgeRating;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 2.26
 */
@FieldType("ageRatingField")
public class AgeRatingSelectFieldDefinition extends AbstractEnumFieldDefinition<AgeRating> {

    private static final Pattern ICON_SUFFICES = Pattern.compile("^[0-9_]+$");
    public AgeRatingSelectFieldDefinition() {
        super(AgeRating.class);
    }

    @Override
    protected String convertToPresentation(String value, ValueContext context) {
        if (isUseIcons() && value != null && ICON_SUFFICES.matcher(value).matches()) {
            return "";
        }
        return super.convertToPresentation(value, context);
    }


}


package nl.vpro.magnolia.ui.poms;

import info.magnolia.ui.field.FieldType;

import nl.vpro.domain.media.Channel;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("channelField")
public class ChannelSelectFieldDefinition extends AbstractEnumFieldDefinition<Channel> {

    public ChannelSelectFieldDefinition() {
        super(Channel.class);
    }
}


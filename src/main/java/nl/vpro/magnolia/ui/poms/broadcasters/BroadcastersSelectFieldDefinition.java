package nl.vpro.magnolia.ui.poms.broadcasters;

import info.magnolia.ui.field.ComboBoxFieldDefinition;
import info.magnolia.ui.field.FieldType;
import javax.inject.Inject;

/**
 * @author Michiel Meeuwissen
 * @since 2.32
 */
@FieldType("broadcastersField")
public class BroadcastersSelectFieldDefinition extends ComboBoxFieldDefinition<String> {


    @Inject
    public BroadcastersSelectFieldDefinition(BroadcasterDataSource dataSourceDefinition) {
        setDatasource(dataSourceDefinition);
    }
}


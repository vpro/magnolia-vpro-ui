/*
 * Copyright (C) 2018 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.poms.broadcasters;

import info.magnolia.ui.field.FieldType;
import info.magnolia.ui.field.TwinColSelectFieldDefinition;
import javax.inject.Inject;

/**
 * @author r.jansen
 */
@FieldType("broadcastersTwinField")
public class BroadcastersTwinColSelectFieldDefinition extends TwinColSelectFieldDefinition<String> {

    @Inject
    public BroadcastersTwinColSelectFieldDefinition(BroadcasterDataSource broadcasterDataSource) {
        setDatasource(broadcasterDataSource);
    }

}

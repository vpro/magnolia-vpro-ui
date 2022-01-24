package nl.vpro.magnolia.ui.poms.broadcasters;


import info.magnolia.ui.datasource.DatasourceType;
import info.magnolia.ui.datasource.optionlist.OptionListDefinition;
import lombok.Getter;
import lombok.Setter;

@DatasourceType("broadcastersDatasource")
@Getter
@Setter
public class BroadcasterDataSource extends OptionListDefinition {
    private String name = "broadcasters";
}

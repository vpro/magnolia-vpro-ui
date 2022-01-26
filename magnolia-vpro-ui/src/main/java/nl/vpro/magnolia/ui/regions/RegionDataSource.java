package nl.vpro.magnolia.ui.regions;


import info.magnolia.ui.datasource.DatasourceType;
import info.magnolia.ui.datasource.optionlist.OptionListDefinition;
import lombok.Getter;
import lombok.Setter;

@DatasourceType("regionsDatasource")
@Getter
@Setter
public class RegionDataSource extends OptionListDefinition {
    private String name = "regions";
}

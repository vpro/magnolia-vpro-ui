package nl.vpro.magnolia.ui.searchableoptionlist;

import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;

@FieldType("checkBoxSearchField")
public class SearchableCheckBoxFieldDefinition extends ConfiguredFieldDefinition<String> {

    @Setter
    @Getter
    private boolean negate = false;

    @Getter
    @Setter
    private String buttonLabel = "";

    public SearchableCheckBoxFieldDefinition() {
        setType(String.class);
        setFactoryClass(SearchableCheckBoxFieldFactory.class);
    }

}

package nl.vpro.magnolia.ui.regions;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.ComboBoxFieldFactory;

import javax.inject.Inject;

import org.meeuw.i18n.regions.Region;

import com.vaadin.server.Page;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Dependency;

public class RegionsSelectFactory extends ComboBoxFieldFactory<Region, RegionSelectFieldDefinition> {

    @Inject
    public RegionsSelectFactory(
        RegionSelectFieldDefinition definition,
        ComponentProvider componentProvider,
        RegionsSelectFieldSupport selectFieldSupport) {
        super(definition, componentProvider, selectFieldSupport);
    }

    @Override
    protected ComboBox<Region> createFieldComponent() {
        ComboBox<Region> component = super.createFieldComponent();
        // we can try to use published resources, but I can't figure it out.
        // (it's easier with plain vaadin)
        // Let's then use resource servlet of magnolia itself

        Page.getCurrent().addDependency(
            new Dependency(Dependency.Type.STYLESHEET, "/.resources/vpro-ui/vpro-ui.css")
        );
        return component;
    }
}

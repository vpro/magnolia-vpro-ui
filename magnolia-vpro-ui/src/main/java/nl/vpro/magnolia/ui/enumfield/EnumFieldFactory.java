package nl.vpro.magnolia.ui.enumfield;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import javax.inject.Inject;

/**
 * For some reason nothing provides this out of the box, but I keep needing it.
 * @author Michiel Meeuwissen
 * @since 3.0
 */
public class EnumFieldFactory<E extends Enum<E>> extends AbstractFieldFactory<String, AbstractEnumFieldDefinition<E>> {


    @Inject
    public EnumFieldFactory(AbstractEnumFieldDefinition<E> definition, ComponentProvider componentProvider) throws ClassNotFoundException {
        super(definition, componentProvider);
    }

    @Override
    public EnumField<E> createFieldComponent() {
        return new EnumField<E>(definition);
    }

}

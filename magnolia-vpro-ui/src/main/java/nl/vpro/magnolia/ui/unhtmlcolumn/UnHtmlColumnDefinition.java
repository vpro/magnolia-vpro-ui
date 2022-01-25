package nl.vpro.magnolia.ui.unhtmlcolumn;

import com.vaadin.data.ValueProvider;
import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import javax.inject.Inject;
import javax.jcr.Node;
import lombok.SneakyThrows;
import nl.vpro.util.TextUtil;

/**
 * A column definition that presents the value of a rich text field, but stripped from all markup.
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@ColumnType("unhtmlColumn")
public class UnHtmlColumnDefinition extends ConfiguredColumnDefinition<Node> {


    public UnHtmlColumnDefinition() {
        setValueProvider(Provider.class);
    }

    public static class Provider implements ValueProvider<Node, String> {
        final UnHtmlColumnDefinition definition;

        @Inject
        Provider(UnHtmlColumnDefinition definition) {
            this.definition = definition;
        }

        @SneakyThrows
        @Override
        public String apply(Node property) {
            return TextUtil.stripHtml(property.getProperty(definition.getName()).getString());
        }
    }
}
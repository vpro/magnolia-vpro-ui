package nl.vpro.magnolia.ui.referredcolumn;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.renderers.TextRenderer;
import info.magnolia.context.Context;
import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import java.util.List;
import javax.jcr.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Getter
@Setter
@ColumnType("referredColumn")
public class ReferredColumnDefinition extends ConfiguredColumnDefinition<Node> {

    private String otherProperty;
    private String workspace;
    private List<String> forType;


    public ReferredColumnDefinition() {
        setType(Node.class);
        setRenderer(TextRenderer.class);
        setValueProvider(ReferredProvider.class);
    }

    public static class ReferredProvider implements ValueProvider<Node, String> {

        private final ReferredColumnDefinition definition;
        private final Context context;

        public ReferredProvider(ReferredColumnDefinition definition, Context context) {
            this.definition = definition;
            this.context = context;
        }

        @Override
        public String apply(Node node) {

            try {
                String type = node.getPrimaryNodeType().getName();
                if (definition.getForType() == null || definition.getForType().contains(type)) {
                    Property value = node.getProperty(definition.getName());
                    String propertyName = definition.getOtherProperty();
                    return context.getJCRSession(definition.getWorkspace()).getNodeByIdentifier(value.getString()).getProperty(propertyName).getString();
                } else {
                    return "-";
                }
            } catch (RepositoryException e) {
                return e.getMessage();
            }
        }
    }

}

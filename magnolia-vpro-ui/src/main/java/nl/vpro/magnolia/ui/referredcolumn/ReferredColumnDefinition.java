package nl.vpro.magnolia.ui.referredcolumn;

import info.magnolia.context.SystemContext;
import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import javax.jcr.*;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.renderers.TextRenderer;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Getter
@Setter
@ColumnType("referredColumn")
@Log4j2
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
        private final SystemContext context; // using system context to avoid opening sessions we can't close

        public ReferredProvider(ReferredColumnDefinition definition, SystemContext context) {
            this.definition = definition;
            this.context = context;
        }

        @Override
        public String apply(Node node) {
            try {

                String type = node.getPrimaryNodeType().getName();
                if (definition.getForType() == null || definition.getForType().contains(type)) {

                    Session session = context.getJCRSession(definition.getWorkspace());
                    String fieldName = definition.getName();
                    if (node.hasProperty(fieldName)) {
                        Property value = node.getProperty(fieldName);
                        String propertyName = definition.getOtherProperty();
                        return session.getNodeByIdentifier(value.getString()).getProperty(propertyName).getString();
                    } else {
                        return "no " + fieldName;
                    }
                } else {
                    return "-";
                }
            } catch (RepositoryException e) {
                if (e.getCause() instanceof IllegalArgumentException) {
                    // expected because we used to store the name there...
                    log.debug(e.getMessage());
                } else {
                    log.error(e.getMessage(), e);
                }
                return e.getMessage();
            }
        }
    }

}

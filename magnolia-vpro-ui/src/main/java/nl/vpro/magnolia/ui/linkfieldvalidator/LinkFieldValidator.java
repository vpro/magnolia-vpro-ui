package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import javax.jcr.*;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

@Slf4j
public class LinkFieldValidator extends AbstractValidator<Object> {

    private final LinkFieldValidatorDefinition definition;

    public LinkFieldValidator(LinkFieldValidatorDefinition definition) {
        super(definition.getErrorMessage());
        this.definition = definition;
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        Node node;
        if (value instanceof Node) {
            node = (Node) value;
        } else if (value instanceof CharSequence) {
            String s = value.toString();
            try {
                node = SessionUtil.getNodeByIdentifier(definition.getRepository(), UUID.fromString(s).toString());
            } catch (IllegalArgumentException invalideUUID) {
                node = SessionUtil.getNode(definition.getRepository(), value.toString());
            }
            if (node == null) {
                return ValidationResult.error("No such node " + s);
            }
        } else {
            throw new IllegalArgumentException("Don't know how to interpret " + value + " as a jcr link");
        }
        try {
            // To check against deleted nodes. Using MissingNode would be easier, but that is package private (again).

            if (node.isNodeType(NodeTypes.Deleted.NAME)) {
                log.error("Can't check if node is deleted for value: {}", value);
                return toResult(value, false);
            }
        } catch (RepositoryException re) {
            return toResult(value, false);
        }
        if (StringUtils.isNotEmpty(definition.getRepository())) {
            try {
                Session session = MgnlContext.getJCRSession(definition.getRepository());
                session.getNodeByIdentifier(node.getIdentifier());
                return toResult(value, true);
            } catch (ItemNotFoundException notfound) {
                return toResult(value, false);
            } catch (RepositoryException re) {
                log.error("Can't validate value: {}", value);
                return toResult(value, false);
            }
        } else {
            return toResult(value, false);
        }
    }
}

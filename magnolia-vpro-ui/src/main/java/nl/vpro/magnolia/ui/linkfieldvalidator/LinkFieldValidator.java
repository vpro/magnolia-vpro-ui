package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import lombok.extern.slf4j.Slf4j;

import javax.jcr.*;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

@Slf4j
public class LinkFieldValidator extends AbstractValidator<Node> {

    private final LinkFieldValidatorDefinition definition;

    public LinkFieldValidator(LinkFieldValidatorDefinition definition) {
        super(definition.getErrorMessage());
        this.definition = definition;
    }

    @Override
    public ValidationResult apply(Node value, ValueContext context) {
        try {
            // To check against deleted nodes. Using MissingNode would be easier, but that is package private (again).
            if (value.isNodeType(NodeTypes.Deleted.NAME)) {
                log.error("Can't check if node is deleted for value: {}", value);
                return toResult(value, false);
            }
        } catch (RepositoryException re) {
            return toResult(value, false);
        }
        if (StringUtils.isNotEmpty(definition.getRepository())) {
            try {
                Session session = MgnlContext.getJCRSession(definition.getRepository());
                session.getNodeByIdentifier(value.getIdentifier());
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

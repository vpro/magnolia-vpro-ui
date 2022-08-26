package nl.vpro.magnolia.ui.irma;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.field.ConfiguredComplexPropertyDefinition;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import javax.jcr.Node;

import com.machinezoo.noexception.Exceptions;
import com.vaadin.data.*;

@Log4j2
public class ProofOfProvenanceFieldConverter implements Converter<SignedText, Node> {

    public static final String SIGNATURE_POSTFIX = "@signature";

    private final ConfiguredComplexPropertyDefinition<Node> definition;

    public ProofOfProvenanceFieldConverter(ConfiguredComplexPropertyDefinition<Node> definition) {
        this.definition = definition;
    }

    @Override
    public Result<Node> convertToModel(SignedText value, ValueContext context) {
        if (value == null) {
            return Result.error("No value present");
        }

        Node current = getNode(context).orElse(null);
        if (current != null) {
            String fieldName =  definition.getName();

            Exceptions.wrap().run(() -> {
                current.setProperty(fieldName, value.getText());
                current.setProperty(postFix(fieldName), value.getSignature());
            });
            return Result.ok(current);
        }
        return Result.ok(null);
    }

    @Override
    public SignedText convertToPresentation(Node value, ValueContext context) {
        Node currentNode = getNode(context).orElse(null);
        if (currentNode != null) {
            String fieldName = definition.getName();
            return new SignedText(
                PropertyUtil.getString(currentNode, fieldName),
                PropertyUtil.getString(currentNode, postFix(fieldName))
            );
        }

        return new SignedText("", "");
    }

    private String postFix(String fieldName) {
        return fieldName + SIGNATURE_POSTFIX;
    }
    private Optional<Node> getNode(ValueContext valueContext) {
        return valueContext.getComponent()
            .flatMap(a -> ((ProofOfProvenanceField) a).getValueContext().getSingle());
    }
}

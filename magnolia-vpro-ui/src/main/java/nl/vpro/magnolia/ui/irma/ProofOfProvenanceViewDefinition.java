package nl.vpro.magnolia.ui.irma;

import info.magnolia.ui.editor.*;
import info.magnolia.ui.field.ConfiguredComplexPropertyDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.jcr.Node;

@FieldType("proofOfProvenanceField")
public class ProofOfProvenanceViewDefinition extends ConfiguredComplexPropertyDefinition<Node> implements EditorDefinition<Node> {


    @Getter
    @Setter
    String attribute = "pbdf.sidn-pbdf.email.email";

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject
    public ProofOfProvenanceViewDefinition() {
        this.setImplementationClass((Class)ProofOfProvenanceView.class);
        this.setItemProvider(new CurrentItemProviderDefinition<>());
    }

}

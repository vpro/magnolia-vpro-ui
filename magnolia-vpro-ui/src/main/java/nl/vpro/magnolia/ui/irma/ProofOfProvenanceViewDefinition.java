package nl.vpro.magnolia.ui.irma;

import info.magnolia.ui.editor.EditorDefinition;
import info.magnolia.ui.editor.JcrChildNodeProviderDefinition;
import info.magnolia.ui.field.ConfiguredComplexPropertyDefinition;
import info.magnolia.ui.field.FieldType;

import javax.jcr.Node;

@FieldType("proofOfProvenanceField")
public class ProofOfProvenanceViewDefinition extends ConfiguredComplexPropertyDefinition<Node> implements EditorDefinition<Node> {

    public ProofOfProvenanceViewDefinition() {
        this.setImplementationClass((Class)ProofOfProvenanceView.class);
        this.setItemProvider(new JcrChildNodeProviderDefinition());
    }
}

package nl.vpro.magnolia.ui.irma;

import info.magnolia.ui.editor.EditorView;
import info.magnolia.ui.field.factory.FormFieldFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;

import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Component;

public class ProofOfProvenanceView implements EditorView<Node> {

    private final ProofOfProvenanceField field;

    private final ProofOfProvenanceFieldConverter converter;

    private final ProofOfProvenanceViewDefinition definition;

    @Inject
    public ProofOfProvenanceView(FormFieldFactory formFieldFactory, ProofOfProvenanceViewDefinition definition) {
        this.field = formFieldFactory.createField(new ProofOfProvenanceFieldDefinition());
        this.converter = new ProofOfProvenanceFieldConverter(definition);
        this.definition = definition;
    }

    @Override
    public Component asVaadinComponent() {
        return this.field;
    }

    @Override
    public List<BinderValidationStatus<?>> validate() {
        return new ArrayList<>();
    }

    @Override
    public void populate(Node item) {
        this.field.setValue(converter.convertToPresentation(item, new ValueContext(this.field)));
    }

    @Override
    public void write(Node item) {
        converter.convertToModel(this.field.getValue(), new ValueContext(this.field));
    }
}

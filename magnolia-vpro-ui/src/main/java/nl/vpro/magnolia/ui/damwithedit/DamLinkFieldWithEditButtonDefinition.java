package nl.vpro.magnolia.ui.damwithedit;

import info.magnolia.dam.api.Item;
import info.magnolia.dam.app.field.DamLinkFieldDefinition;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.ui.editor.ItemPreviewDefinition;
import info.magnolia.ui.field.FieldType;

import java.util.Arrays;

import nl.vpro.magnolia.ui.linkfieldvalidator.LinkFieldValidatorDefinition;

@FieldType("damLinkFieldWithEdit")
public class DamLinkFieldWithEditButtonDefinition extends DamLinkFieldDefinition {

    public DamLinkFieldWithEditButtonDefinition() {
        super();
        setTextInputAllowed(true);
        LinkFieldValidatorDefinition linkFieldValidatorDefinition = new LinkFieldValidatorDefinition();
        linkFieldValidatorDefinition.setRepository(DamConstants.WORKSPACE);
        setValidators(Arrays.asList(linkFieldValidatorDefinition));
        //setChooserId("dam-app-core:chooser");
    }

    static ItemPreviewDefinition<Item> getPreviewDefinition() {
        ItemPreviewDefinition<Item> previewDef = new ItemPreviewDefinition<>();
        previewDef.setImplementationClass(DamItemPreviewComponentWithEditButton.class);
        return previewDef;
    }

    @Override
    protected ItemPreviewDefinition<Item> getItemPreviewDefinition() {
        return getPreviewDefinition();
    }


}

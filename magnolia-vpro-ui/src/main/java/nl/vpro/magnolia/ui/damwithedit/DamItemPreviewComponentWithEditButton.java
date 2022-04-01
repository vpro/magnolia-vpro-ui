package nl.vpro.magnolia.ui.damwithedit;


import info.magnolia.dam.api.Asset;
import info.magnolia.dam.api.Item;
import info.magnolia.dam.app.data.AssetPropertySetFactory;
import info.magnolia.dam.app.imageprovider.AssetPreviewProvider;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.api.location.*;
import info.magnolia.ui.preview.AbstractItemPreviewComponent;
import info.magnolia.ui.theme.ResurfaceTheme;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.google.common.net.MediaType;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

/**
 * Based on info.magnolia.dam.app.field.link.DamItemPreviewComponent
 * Inheriting did not work properly, strange css issues, plus half had to be reimplemented because it was private.
 *
 * TODO close dialog window on edit click
 *
 * @since 5.0
 */
@Slf4j
public class DamItemPreviewComponentWithEditButton extends AbstractItemPreviewComponent<Item> {

    private final LocationController locationController;

    @Inject
    public DamItemPreviewComponentWithEditButton(SimpleTranslator i18n, AssetPropertySetFactory propertySetFactory, AssetPreviewProvider previewProvider, LocationController locationController) {
        super(i18n, propertySetFactory, previewProvider, getProperties());
        this.locationController = locationController;
        this.setVisible(false);
    }

    private static Map<String, Class> getProperties() {
        Map<String, Class> properties = new HashMap<>();
        properties.put("name", String.class);
        properties.put("fileSize", String.class);
        properties.put("mimeType", String.class);
        return properties;
    }

    @Override
    protected Layout getRootLayout() {
        return rootLayout == null ? new VerticalLayout() : rootLayout;
    }

    @Override
    protected Component createDetailComponent(String propertyName, Object value) {
        Label detailComponent = new Label();
        detailComponent.setCaption(getI18n().translate("dam.damLinkField.fileInfo.".concat(propertyName)));
        detailComponent.setValue(String.valueOf(value));
        return detailComponent;
    }

    @Override
    public void onValueChange(Item item) {
        if (item != null && item.isAsset()) {
            super.onValueChange(item);
        } else {
            clearRootLayout();
        }
    }

    @Override
    protected Component refreshItemPreview(Item item) {
        if (!item.isAsset()) {
            return null;
        }
        MediaType type = getAssetMediaType((Asset) item);
        Image defaultThumbnail = new Image("");
        defaultThumbnail.setIcon(MagnoliaIcons.FILE);

        Component image = type.is(MediaType.ANY_IMAGE_TYPE) ? super.refreshItemPreview(item) : defaultThumbnail;

        this.setVisible(true);
        CssLayout previewLayout = new CssLayout();
        previewLayout.setSizeFull();
        previewLayout.addStyleName("link-file-panel");

        CssLayout controlButtonPanel = new CssLayout();
        controlButtonPanel.addStyleName("control-button-panel");
        controlButtonPanel.setWidth(30, Unit.PIXELS);

        if (type.is(MediaType.ANY_IMAGE_TYPE)) {

            previewLayout.removeStyleName("link-file-panel");
            previewLayout.addStyleName("link-file-panel-large");

            Button openLightBoxViewBtn = createControlPanelButton(MagnoliaIcons.SEARCH);
            openLightBoxViewBtn.addClickListener(event -> openLightBoxView((Asset) item));
            openLightBoxViewBtn.setDescription(getI18n().translate("dam.damUploadField.lightbox"));
            controlButtonPanel.addComponents(openLightBoxViewBtn);
        }
        image.addStyleName("file-preview-thumbnail");

        previewLayout.addComponents(createEditButtonPanel(item), controlButtonPanel, image);

        return previewLayout;
    }

    private Component createEditButtonPanel(Item item) {
        CssLayout editButtonPanel = new CssLayout();
        editButtonPanel.addStyleName("control-button");
        editButtonPanel.setWidth(30, Unit.PIXELS);
        Button editButton = createControlPanelButton(MagnoliaIcons.EDIT);
        editButton.addClickListener(event -> {
            final String appType = Location.LOCATION_TYPE_APP;
            final String appName = "dam";
            final String subAppId = "jcrDetail";

            Location location = new DefaultLocation(appType, appName, subAppId, item.getPath());
            locationController.goTo(location);
        });
        editButton.setDescription(getI18n().translate("dam.damUploadField.edit"));
        editButtonPanel.addComponents(editButton);

        return editButtonPanel;
    }

    private MediaType getAssetMediaType(Asset item) {
        String mimeType = item.getMimeType();
        try {
            return MediaType.parse(mimeType);
        } catch (Exception e) {
            log.error("Cannot parse asset item mime type", e);
            return MediaType.OCTET_STREAM;
        }
    }

    private Button createControlPanelButton(Resource icon) {
        Button button = new Button(icon);
        button.addStyleNames(ResurfaceTheme.BUTTON_ICON, "control-button");
        return button;
    }

    void openLightBoxView(Asset item) {
        try (
            InputStream contentStream = item.getContentStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(IOUtils.toByteArray(contentStream))
        ) {
            StreamResource.StreamSource streamSource = () -> inputStream;
            StreamResource streamResource = new StreamResource(streamSource, "");

            CssLayout imageComponent = new CssLayout(new Image("", streamResource));
            imageComponent.setSizeFull();
            imageComponent.setStyleName("light-box-content");

            Window window = new Window();
            window.addStyleName("light-box");
            window.setDraggable(false);
            window.setResizable(false);
            window.setModal(true);
            window.setContent(imageComponent);
            window.center();

            UI.getCurrent().addWindow(window);

            window.addClickListener((MouseEvents.ClickListener) event -> window.close());
        } catch (IOException e) {
            Notification.show(getI18n().translate("dam.damUploadField.lightbox.error.subject"),
                getI18n().translate("dam.damUploadField.lightbox.error.message"),
                Notification.Type.ERROR_MESSAGE);
            log.error("Could not open asset content stream.", e);
        }
    }
}

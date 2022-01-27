package nl.vpro.magnolia.ui.poms.broadcasters;

import info.magnolia.ui.field.SelectFieldSupport;

import java.util.Comparator;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.vaadin.data.*;
import com.vaadin.data.provider.*;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;

import nl.vpro.domain.user.*;

public class BroadcastersSelectFieldSupport implements SelectFieldSupport<String> {


    private final BroadcasterService broadcasterService;

    @Inject
    public BroadcastersSelectFieldSupport(BroadcasterService broadcasterService) {
        this.broadcasterService = broadcasterService;
    }


    @Override
    public DataProvider<String, String> getDataProvider() {
        return new AbstractDataProvider<String, String>() {
            @Override
            public boolean isInMemory() {
                return true;
            }

            @Override
            public int size(Query<String, String> query) {
                return (int) fetch(query).count();
            }

            @Override
            public Stream<String> fetch(Query<String, String> query) {
                return broadcasterService.findAll().stream()
                    .sorted(Comparator.comparing(Broadcaster::getDisplayName))
                    .map(Organization::getId);
            }
        };
    }

    @Override
    public ItemCaptionGenerator<String> getItemCaptionGenerator() {
        return item -> broadcasterService.find(item).getDisplayName();
    }

    @Override
    public IconGenerator<String> getIconGenerator() {
        return item -> null;
    }

    @Override
    public Converter<String, String> defaultConverter() {
        return new Converter<String, String>() {
            @Override
            public Result<String> convertToModel(String value, ValueContext context) {
                return Result.ok(value);
            }

            @Override
            public String convertToPresentation(String value, ValueContext context) {
                return value;
            }
        };

    }
}

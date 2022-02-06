package nl.vpro.magnolia.ui;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleResource;
import info.magnolia.module.delta.Task;

import java.util.*;

import org.meeuw.i18n.countries.CurrentCountry;

public class VproUiVersionHandler extends DefaultModuleVersionHandler {
    @Override
    protected final List<Task> getExtraInstallTasks(InstallContext installContext) {
        if (CurrentCountry.HAS_WEBJARS_JAR) {
            log.info("Detected webjars, make sure it is bypassed in mgnl filter");
            return Arrays.asList(
                new BootstrapSingleResource(
                    "webjars",
                    "Bypass webjars",
                    "/setup/config.server.filters.bypasses.webjars.yaml")
            );
        } else {
            return Collections.emptyList();
        }
    }
}

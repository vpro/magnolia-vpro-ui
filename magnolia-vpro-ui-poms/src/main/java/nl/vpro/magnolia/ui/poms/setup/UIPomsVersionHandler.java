package nl.vpro.magnolia.ui.poms.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleResource;
import info.magnolia.module.delta.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**

 * @author Michiel Meeuwissen

 */
@Slf4j
public class UIPomsVersionHandler extends DefaultModuleVersionHandler {


    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        return Arrays.asList(
            new BootstrapSingleResource(
                "By pass kijkwijzer",
                "By pass kijkwijzer",
                "/setup/config.server.filters.bypasses.kijkwijzer.yaml")
        );
    }

}

package nl.vpro.magnolia.ui.regions;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

@Log4j2
class RegionsSelectFieldSupportTest {

    @Test
    void getIconGenerator() {

        log.info("{}", RegionsSelectFieldSupport.getCdnWebJars());
        log.info("{}", RegionsSelectFieldSupport.WEBJARS);

    }
}

/*
 * Copyright 2022 VPRO
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
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

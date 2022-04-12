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
package nl.vpro.magnolia.ui.dateonlycolumn;

import info.magnolia.context.Context;
import info.magnolia.ui.framework.util.TimezoneUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Provider;

import com.vaadin.ui.renderers.DateRenderer;

/**
 * Based on: info.magnolia.ui.contentapp.configuration.column.renderer.DefaultDateRenderer
 * but without the time.
 */
public class DateOnlyRenderer extends DateRenderer  {

   private static final String DEFAULT_DATE_TIME_PATTERN = "MMM dd, yyyy";

    @Inject
    public DateOnlyRenderer(Provider<Context> contextProvider) {
        super(
            createDateFormat(
                TimezoneUtil.getUserLocale(contextProvider.get().getUser()),
                TimezoneUtil.getUserTimezone(contextProvider.get().getUser())
            )
        );
    }

    private static SimpleDateFormat createDateFormat(Locale userLocale, TimeZone userTimeZone) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME_PATTERN, userLocale);
        df.setTimeZone(userTimeZone);
        return df;
    }
}

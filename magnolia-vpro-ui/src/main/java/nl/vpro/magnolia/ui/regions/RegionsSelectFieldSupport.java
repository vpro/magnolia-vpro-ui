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
package nl.vpro.magnolia.ui.regions;

import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.ui.field.SelectFieldSupport;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.meeuw.i18n.regions.Region;
import org.meeuw.i18n.regions.RegionService;

import com.vaadin.data.*;
import com.vaadin.data.provider.*;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;

@Log4j2
public class RegionsSelectFieldSupport implements SelectFieldSupport<Region> {

    // uses java service loader
    private static final RegionService regionService = RegionService.getInstance();

    private final RegionSelectFieldDefinition fieldDefinition;
    private final I18nContentSupport i18nContentSupport;

    @Inject
    public RegionsSelectFieldSupport(
        RegionSelectFieldDefinition definition,
        I18nContentSupport i18nContentSupport) {
        this.fieldDefinition = definition;
        this.i18nContentSupport = i18nContentSupport;
    }


    @Override
    public DataProvider<Region, String> getDataProvider() {

        final Locale locale = i18nContentSupport.getLocale();

        return new AbstractDataProvider<Region, String>() {
            @Override
            public boolean isInMemory() {
                return true;
            }

            @Override
            public int size(Query<Region, String> query) {
                return (int) fetch(query).count();
            }

            @Override
            public Stream<Region> fetch(Query<Region, String> query) {
                return fieldDefinition.regions()
                    .sorted(
                        Comparator.comparing(r -> r.getName(locale))
                    );
            }
        };
    }



    @Override
    public ItemCaptionGenerator<Region> getItemCaptionGenerator() {
        return region -> region.getName(i18nContentSupport.getLocale()) + (fieldDefinition.isShowCode() ? " (" + region.getCode() + ")" : "");
    }

    @Override
    public IconGenerator<Region> getIconGenerator() {
        return region ->
            fieldDefinition.isUseIcons() ?
                region.getIcon().map(
                u ->
                    new ExternalResource((u.isAbsolute() ? "" :  MgnlContext.getWebContext().getContextPath()) + u)
                ).orElse(null) :
                null
            ;
    }

    @Override
    public Converter<Region, String> defaultConverter() {
        return new Converter<Region, String>() {
            @Override
            public Result<String> convertToModel(Region value, ValueContext context) {
                return Result.ok(value.getCode());
            }

            @Override
            public Region convertToPresentation(String value, ValueContext context) {
                if (value == null) {
                    return null;
                }
                return regionService.getByCode(value).orElse(null);
            }
        };

    }

}

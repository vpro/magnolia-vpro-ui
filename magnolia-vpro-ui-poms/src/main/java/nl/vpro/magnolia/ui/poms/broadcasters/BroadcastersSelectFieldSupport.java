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

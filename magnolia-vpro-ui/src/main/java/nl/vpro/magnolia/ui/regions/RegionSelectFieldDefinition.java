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

import info.magnolia.ui.contentapp.FilteringMode;
import info.magnolia.ui.field.ComboBoxFieldDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.meeuw.i18n.countries.CurrentCountry;
import org.meeuw.i18n.regions.Region;
import org.meeuw.i18n.regions.RegionService;

import com.neovisionaries.i18n.CountryCode;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@FieldType("regionField")
public class RegionSelectFieldDefinition extends ComboBoxFieldDefinition<Region> {

    // uses java service loader
    private static final RegionService regionService = RegionService.getInstance();

    @Getter
    @Setter
    private boolean useIcons;

    @Getter
    @Setter
    private boolean showCode;

    @Getter
    @Setter
    private List<Region.Type> regionType;

    @Getter
    @Setter
    private Class<? extends Region> regionClass;


    @Getter
    @Setter
    private List<CountryCode.Assignment> countryAssignment;

    @Inject
    public RegionSelectFieldDefinition(
        RegionDataSource dataSourceDefinition) {
        setFactoryClass(RegionsSelectFactory.class);
        setDatasource(dataSourceDefinition);
        setStyleName("region");
        setTextInputAllowed(true);
        setFilteringMode(FilteringMode.CONTAINS);
        setPageLength(40);
    }


    Stream<Region> regions() {
        final List<Region.Type> regionType = getRegionType(); // bytebuddy?
        return regionService
            .values(getRegionClass())
            .filter(r -> {
                List<CountryCode.Assignment> countryAssignment = getCountryAssignment();
                return countryAssignment == null || countryAssignment.isEmpty() ||
                    (!(r instanceof CurrentCountry)) ||
                    countryAssignment.contains(((CurrentCountry) r).getAssignment());
            })
            .map(r -> (Region) r)
            .filter(r -> regionType == null || regionType.isEmpty()
                || regionType.contains(r.getType()));
    }

}

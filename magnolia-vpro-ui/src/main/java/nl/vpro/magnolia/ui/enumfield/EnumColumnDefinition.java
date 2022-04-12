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
package nl.vpro.magnolia.ui.enumfield;

import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import lombok.extern.log4j.Log4j2;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.vaadin.data.ValueProvider;

import nl.vpro.i18n.Displayable;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@ColumnType("enumColumn")
@Log4j2
public class EnumColumnDefinition extends ConfiguredColumnDefinition<Node> {

    private Class<? extends Enum<?>> enumClass;

    public EnumColumnDefinition() {
        setValueProvider(Provider.class);
    }

    public void setEnum(Class<? extends Enum<?>> enumClass) {
        this.enumClass = enumClass;
    }

    public Class<? extends Enum<?>> getEnum() {
        return enumClass;
    }

    public static class Provider implements ValueProvider<Node, String> {

        private final EnumColumnDefinition definition;
        private final Class<? extends Enum<?>> enumClass;

        public Provider(EnumColumnDefinition definition) throws Throwable {
            this.definition = definition;
            this.enumClass = definition.getEnum();
            if (this.enumClass == null) {
                throw new IllegalStateException("No enum defined in '" +  definition.getName() + "' " + definition);
            }
        }


        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public String apply(Node property) {
            try {
                if (property.hasProperty(definition.getName())) {
                    String value = property.getProperty(definition.getName()).getString();
                    if (value == null) {
                        return "";
                    }

                    try {
                        Enum<?> e = Enum.valueOf((Class) enumClass, value);
                        if (Displayable.class.isAssignableFrom(enumClass)) {
                            return ((Displayable) e).getDisplayName();
                        } else {
                            return e.toString();
                        }
                    } catch (IllegalArgumentException iae) {
                        log.warn(iae.getMessage());
                        return value + " (unrecognized)";
                    }
                } else {
                    return "";
                }
            } catch (RepositoryException rp) {
                return rp.getMessage();
            }
        }
    }

}

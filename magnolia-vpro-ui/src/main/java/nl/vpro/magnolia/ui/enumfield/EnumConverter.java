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

import java.util.Optional;

import com.vaadin.data.*;

public class EnumConverter<E extends Enum<E>> implements Converter<String, E> {

    private  final Class<E> clazz;

    public EnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Result<E> convertToModel(String value, ValueContext context) {
        if (value == null) {
            return Result.ok(null);
        }
        Optional<E> e =  fromString(value, context);
        //log.warn("Could not convert " + value + " to " + clazz);
        return e.map(Result::ok).orElseGet(() -> Result.ok(null));
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private Optional<E> fromString(String value, ValueContext context) {
        for (E e : clazz.getEnumConstants()) {
            if (convertToPresentation(e, context).equals(value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public String getIcon(String value, ValueContext context) {
        return getIcon(Enum.valueOf(clazz, value));
    }

    public String getIcon(E value) {
        return null;
    }

    protected boolean filter(E value) {
        return true;
    }

}

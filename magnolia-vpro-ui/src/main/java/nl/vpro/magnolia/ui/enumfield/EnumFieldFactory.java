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

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import javax.inject.Inject;

/**
 * For some reason nothing provides this out of the box, but I keep needing it.
 * @author Michiel Meeuwissen
 * @since 3.0
 */
public class EnumFieldFactory<E extends Enum<E>> extends AbstractFieldFactory<String, AbstractEnumFieldDefinition<E>> {


    @Inject
    public EnumFieldFactory(AbstractEnumFieldDefinition<E> definition, ComponentProvider componentProvider) throws ClassNotFoundException {
        super(definition, componentProvider);
    }

    @Override
    public EnumField<E> createFieldComponent() {
        return new EnumField<E>(definition);
    }

}

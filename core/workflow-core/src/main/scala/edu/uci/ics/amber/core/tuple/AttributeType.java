/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.uci.ics.amber.core.tuple;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public enum AttributeType implements Serializable {

    /**
     * To add a new edu.ics.uci.amber.model.tuple.model.AttributeType, update the following files to handle the new type:
     * 1. AttributeTypeUtils
     * <code>src/main/scala/edu/uci/ics/texera/workflow/common/tuple/schema/AttributeTypeUtils.scala</code>
     * Provide parsing, inferring, and casting logic between other AttributeTypes.
     * <p>
     * 2. SQLSourceOpDesc
     * <code>src/main/scala/edu/uci/ics/texera/workflow/operators/source/sql/SQLSourceOpDesc</code>
     * Especially SQLSources will need to map the input schema to Texera.Schema. edu.ics.uci.amber.model.tuple.model.AttributeType
     * needs to be converted from original source types accordingly.
     * <p>
     * 3. FilterPredicate
     * <code>src/main/scala/edu/uci/ics/texera/workflow/operators/filter/FilterPredicate.java</code>
     * FilterPredicate takes in AttributeTypes and converts them into a comparable type, then do
     * the comparison. New AttributeTypes needs to be mapped to a comparable type there.
     * <p>
     * 4. SpecializedAverageOpDesc.getNumericalValue
     * <code>src/main/scala/edu/uci/ics/texera/workflow/operators/aggregate/SpecializedAverageOpDesc.scala</code>
     * New AttributeTypes might need to be converted into a numerical value in order to perform
     * aggregations.
     * <p>
     * 5. SchemaPropagationService.SchemaAttribute
     * <code>src/app/workspace/service/dynamic-schema/schema-propagation/schema-propagation.service.ts</code>
     * Declare the frontend SchemaAttribute for the new edu.ics.uci.amber.model.tuple.model.AttributeType.
     * <p>
     * 6. ArrowUtils (Java)
     * <code>src/main/scala/edu/uci/ics/amber/engine/architecture/pythonworker/ArrowUtils.scala</code>
     * Provide java-side conversion between ArrowType and edu.ics.uci.amber.model.tuple.model.AttributeType.
     * <p>
     * 7. ArrowUtils (Python)
     * <code>src/main/python/core/util/arrow_utils.py</code>
     * Provide python-side conversion between ArrowType and edu.ics.uci.amber.model.tuple.model.AttributeType.
     */


    // A field that is indexed but not tokenized: the entire String
    // value is indexed as a single token
    STRING("string", String.class),
    INTEGER("integer", Integer.class),
    LONG("long", Long.class),
    DOUBLE("double", Double.class),
    BOOLEAN("boolean", Boolean.class),
    TIMESTAMP("timestamp", Timestamp.class),
    BINARY("binary", byte[].class),
    ANY("ANY", Object.class);

    private final String name;
    private final Class<?> fieldClass;

    AttributeType(String name, Class<?> fieldClass) {
        this.name = name;
        this.fieldClass = fieldClass;
    }

    @JsonValue
    public String getName() {
        if (this.name.equals(ANY.name)) {
            // exclude this enum type in JSON schema
            // JSON schema generator will ignore empty enum type
            return "";
        }
        return this.name;
    }

    public Class<?> getFieldClass() {
        return this.fieldClass;
    }

    public static AttributeType getAttributeType(Class<?> fieldClass) {
        if (fieldClass.equals(String.class)) {
            return STRING;
        } else if (fieldClass.equals(Integer.class)) {
            return INTEGER;
        } else if (fieldClass.equals(Long.class)) {
            return LONG;
        } else if (fieldClass.equals(Double.class)) {
            return DOUBLE;
        } else if (fieldClass.equals(Boolean.class)) {
            return BOOLEAN;
        } else if (fieldClass.equals(Timestamp.class)) {
            return TIMESTAMP;
        } else if (fieldClass.equals(byte[].class)) {
            return BINARY;
        } else {
            return ANY;
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

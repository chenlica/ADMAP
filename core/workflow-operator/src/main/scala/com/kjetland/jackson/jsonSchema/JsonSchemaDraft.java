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

package com.kjetland.jackson.jsonSchema;

public enum JsonSchemaDraft {
    DRAFT_04("http://json-schema.org/draft-04/schema#"),
    DRAFT_06("http://json-schema.org/draft-06/schema#"),
    DRAFT_07("http://json-schema.org/draft-07/schema#"),
    DRAFT_2019_09("http://json-schema.org/draft/2019-09/schema#");

    final String url;

    JsonSchemaDraft(String url) {
        this.url = url;
    }
}
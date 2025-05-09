/**
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

import { GroupInfo, OperatorMetadata, OperatorSchema } from "../../types/operator-schema.interface";
import { CustomJSONSchema7 } from "../../types/custom-json-schema.interface";
import { VIEW_RESULT_OP_TYPE } from "../workflow-graph/model/workflow-graph";
import { PortSchema } from "../../types/workflow-common.interface";

// Exports constants related to operator schema and operator metadata for testing purposes.

export const mockScanSourceSchema: OperatorSchema = {
  operatorType: "ScanSource",
  additionalMetadata: {
    userFriendlyName: "Source: Scan",
    operatorDescription: "Read records from a table one by one",
    operatorGroupName: "Source",
    inputPorts: [],
    outputPorts: [{}],
  },
  jsonSchema: {
    properties: {
      tableName: {
        type: "string",
        description: "name of source table",
        title: "table name",
      },
    },
    required: ["tableName"],
    type: "object",
  },
  operatorVersion: "scan",
};

export const mockPresetEnabledSchema: OperatorSchema = {
  operatorType: "PresetEnabledOp",
  additionalMetadata: {
    userFriendlyName: "DEBUG_userFriendlyName",
    operatorDescription: "DEBUG_operatorDescription",
    operatorGroupName: "Analysis",
    inputPorts: [],
    outputPorts: [{}],
  },
  jsonSchema: {
    properties: {
      presetProperty: {
        type: "string",
        description: "property that can be saved in presets",
        title: "presetProperty",
        "enable-presets": true,
      },
      normalProperty: { type: "string", description: "property that is excluded in presets", title: "normalProperty" },
    },
    required: ["normalProperty"],
    type: "object",
  },
  operatorVersion: "preset1",
};

export const mockFileSourceSchema: OperatorSchema = {
  operatorType: "FileSource",
  jsonSchema: {
    type: "object",
    properties: {
      fileName: { type: "string", title: "file name" },
    },
    required: ["fileName"],
  },
  additionalMetadata: {
    userFriendlyName: "Source: File",
    operatorDescription: "Read the content of one file or multiple files",
    operatorGroupName: "Source",
    inputPorts: [],
    outputPorts: [{}],
  },
  operatorVersion: "fileSource1",
};

export const mockNlpSentimentSchema: OperatorSchema = {
  operatorType: "NlpSentiment",
  additionalMetadata: {
    userFriendlyName: "Sentiment Analysis",
    operatorDescription: "Sentiment analysis based on Stanford NLP package",
    operatorGroupName: "Analysis",
    inputPorts: [{}],
    outputPorts: [{}],
  },
  jsonSchema: {
    properties: {
      attribute: {
        type: "string",
        title: "attribute",
        autofill: "attributeName",
        autofillAttributeOnPort: 0,
      },
      resultAttribute: { type: "string", title: "result attribute" },
    },
    required: ["attribute", "resultAttribute"],
    type: "object",
  },
  operatorVersion: "Nlp1",
};

export const mockKeywordSourceSchema: OperatorSchema = {
  operatorType: "KeywordSource",
  jsonSchema: {
    type: "object",
    properties: {
      query: { type: "string", title: "query" },
      attributes: {
        type: "array",
        items: { type: "string" },
        title: "attributes",
        autofill: "attributeNameList",
        autofillAttributeOnPort: 0,
      },
      tableName: { type: "string", title: "table name" },
      spanListName: { type: "string", title: "span list name" },
    },
    required: ["query", "attributes", "tableName"],
  },
  additionalMetadata: {
    userFriendlyName: "Source: Keyword",
    operatorDescription: "Perform an index-based search on a table using a keyword",
    operatorGroupName: "Analysis",
    inputPorts: [],
    outputPorts: [{}],
  },
  operatorVersion: "keywordSource1",
};

export const mockKeywordSearchSchema: OperatorSchema = {
  operatorType: "KeywordMatcher",
  jsonSchema: {
    type: "object",
    properties: {
      query: { type: "string", title: "query" },
      attributes: {
        type: "array",
        items: { type: "string" },
        title: "attributes",
        autofill: "attributeNameList",
        autofillAttributeOnPort: 0,
      },
      spanListName: { type: "string", title: "span list name" },
    },
    required: ["query", "attributes"],
  },
  additionalMetadata: {
    userFriendlyName: "Keyword Search",
    operatorDescription: "Search the documents using a keyword",
    operatorGroupName: "Analysis",
    inputPorts: [{}],
    outputPorts: [{}],
  },
  operatorVersion: "keywordMatcher1",
};

export const mockAggregationSchema: OperatorSchema = {
  operatorType: "Aggregation",
  jsonSchema: {
    type: "object",
    properties: {
      listOfAggregations: {
        type: "array",
        items: {
          type: "object",
          properties: {
            attribute: {
              type: "string",
              title: "attribute",
              autofill: "attributeName",
              autofillAttributeOnPort: 0,
            },
            aggregator: {
              type: "string",
              enum: ["min", "max", "average", "sum", "count"],
              uniqueItems: true,
              title: "aggregator",
            },
            resultAttribute: { type: "string", title: "result attribute" },
          },
        },
        title: "list of aggregations",
      },
    },
    required: ["listOfAggregations"],
  },
  additionalMetadata: {
    userFriendlyName: "Aggregation",
    operatorDescription: "Aggregate one or more columns to find min, max, sum, average, count of the column",
    operatorGroupName: "Analysis",
    inputPorts: [{}],
    outputPorts: [{}],
  },
  operatorVersion: "agg1",
};

export const mockViewResultsSchema: OperatorSchema = {
  operatorType: VIEW_RESULT_OP_TYPE,
  jsonSchema: {
    properties: {
      limit: {
        default: 10,
        type: "integer",
        title: "limit",
      },
      offset: {
        default: 0,
        type: "integer",
        title: "offset",
      },
    },
    type: "object",
  },
  additionalMetadata: {
    userFriendlyName: "View Results",
    operatorDescription: "View the results of the workflow",
    operatorGroupName: "View Results",
    inputPorts: [{}],
    outputPorts: [],
  },
  operatorVersion: "view1",
};

export const mockMultiInputOutputSchema: OperatorSchema = {
  operatorType: "MultiInputOutput",
  jsonSchema: {
    properties: {},
    type: "object",
  },
  additionalMetadata: {
    userFriendlyName: "3-I/O Mock op",
    operatorDescription: "Mock operator with 3 inputs and 3 outputs",
    operatorGroupName: "Analysis",
    inputPorts: [{}, {}, {}],
    outputPorts: [{}, {}, {}],
  },
  operatorVersion: "multiInput1",
};

export const mockUnionSchema: OperatorSchema = {
  operatorType: "Union",
  jsonSchema: {
    properties: {},
    type: "object",
  },
  additionalMetadata: {
    userFriendlyName: "Union",
    operatorDescription: "Union multiple inputs",
    operatorGroupName: "Analysis",
    inputPorts: [{ allowMultiLinks: true }],
    outputPorts: [{}],
  },
  operatorVersion: "union1",
};

export const mockPythonUDFSchema: OperatorSchema = {
  operatorType: "PythonUDF",
  additionalMetadata: {
    userFriendlyName: "Python UDF",
    operatorDescription: "custom operator in Java",
    operatorGroupName: "UDF",
    inputPorts: [{}],
    outputPorts: [{}],
  },
  jsonSchema: {
    properties: {},
    type: "object",
  },
  operatorVersion: "p1",
};

export const mockJavaUDFSchema: OperatorSchema = {
  operatorType: "JavaUDF",
  additionalMetadata: {
    userFriendlyName: "Java UDF",
    operatorDescription: "custom operator in Java",
    operatorGroupName: "UDF",
    inputPorts: [{}],
    outputPorts: [{}],
  },
  jsonSchema: {
    properties: {},
    type: "object",
  },
  operatorVersion: "p1",
};

export const mockOperatorSchemaList: ReadonlyArray<OperatorSchema> = [
  mockScanSourceSchema,
  mockFileSourceSchema,
  mockKeywordSourceSchema,
  mockKeywordSearchSchema,
  mockNlpSentimentSchema,
  mockAggregationSchema,
  mockViewResultsSchema,
  mockMultiInputOutputSchema,
  mockPresetEnabledSchema,
  mockUnionSchema,
  mockPythonUDFSchema,
  mockJavaUDFSchema,
];

export const mockOperatorGroup: ReadonlyArray<GroupInfo> = [
  { groupName: "Source" },
  { groupName: "Analysis" },
  { groupName: "View Results" },
];

export const mockOperatorMetaData: OperatorMetadata = {
  operators: mockOperatorSchemaList,
  groups: mockOperatorGroup,
};

export const testJsonSchema: CustomJSONSchema7 = {
  properties: {
    attribute: {
      type: "string",
      title: "attribute",
      autofill: "attributeName",
      autofillAttributeOnPort: 0,
    },
    resultAttribute: {
      type: "string",
      title: "result attribute",
    },
  },
  required: ["attribute", "resultAttribute"],
  type: "object",
};

export const mockPortSchema: PortSchema = {
  jsonSchema: {
    type: "object",
    properties: {
      partitionInfo: {
        type: "object",
        oneOf: [
          {
            title: "none",
            properties: {
              type: { const: "none" },
            },
          },
          {
            title: "hash",
            properties: {
              type: { const: "hash" },
              hashAttributeNames: { type: "array", items: { type: "string" }, title: "attribute names" },
            },
          },
          {
            title: "range",
            properties: {
              type: { const: "range" },
              rangeAttributeNames: { type: "array", items: { type: "string" }, title: "attribute names" },
              rangeMin: { type: "integer", title: "range min" },
              rangeMax: { type: "integer", title: "range max" },
            },
          },
          {
            title: "single",
            properties: {
              type: { const: "single" },
            },
          },
          {
            title: "broadcast",
            properties: {
              type: { const: "broadcast" },
            },
          },
        ],
        title: "partition info",
      },
      dependencies: {
        type: "array",
        items: { type: "integer" },
        title: "dependencies",
      },
    },
    required: ["partitionInfo"],
  },
};

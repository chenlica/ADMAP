import { JSONSchema7 } from "json-schema";

/**
 * This file contains multiple type declarations related to workflow-graph.
 * These type declarations should be identical to the backend API.
 */

export interface Point
  extends Readonly<{
    x: number;
    y: number;
  }> {}

export interface LogicalPort
  extends Readonly<{
    operatorID: string;
    portID: string;
  }> {}

export type PartitionInfo =
  | Readonly<{ type: "hash"; hashAttributeNames: string[] }>
  | Readonly<{ type: "range"; rangeAttributeNames: string[]; rangeMin: number; rangeMax: number }>
  | Readonly<{ type: "single" }>
  | Readonly<{ type: "broadcast" }>
  | Readonly<{ type: "none" }>;

export interface PortSchema
  extends Readonly<{
    jsonSchema: Readonly<JSONSchema7>;
  }> {}

export interface PortProperty
  extends Readonly<{ partitionInfo: PartitionInfo; dependencies: { id: number; internal: boolean }[] }> {}

export interface PortDescription
  extends Readonly<{
    portID: string;
    displayName?: string;
    allowMultiInputs?: boolean;
    isDynamicPort?: boolean;
    partitionRequirement?: PartitionInfo;
    dependencies?: { id: number; internal: boolean }[];
  }> {}

export interface OperatorPredicate
  extends Readonly<{
    operatorID: string;
    operatorType: string;
    operatorVersion: string;
    operatorProperties: Readonly<{ [key: string]: any }>;
    inputPorts: PortDescription[];
    outputPorts: PortDescription[];
    dynamicInputPorts?: boolean;
    dynamicOutputPorts?: boolean;
    showAdvanced: boolean;
    isDisabled?: boolean;
    viewResult?: boolean;
    markedForReuse?: boolean;
    customDisplayName?: string;
  }> {}

export interface Comment
  extends Readonly<{
    content: string;
    creationTime: string;
    creatorName: string;
    creatorID: number;
  }> {}

export interface CommentBox {
  commentBoxID: string;
  comments: Comment[];
  commentBoxPosition: Point;
}

export interface OperatorLink
  extends Readonly<{
    linkID: string;
    source: LogicalPort;
    target: LogicalPort;
  }> {}

/**
 * refer to src/main/scalapb/edu/uci/ics/texera/web/workflowruntimestate/ConsoleMessage.scala
 */
export type ConsoleMessage = Readonly<{
  workerId: string;
  timestamp: {
    nanos: number;
    seconds: number;
  };
  msgType: {
    name: string;
  };
  source: string;
  title: string;
  message: string;
}>;

export type ConsoleUpdateEvent = Readonly<{
  operatorId: string;
  messages: ReadonlyArray<ConsoleMessage>;
}>;

export type BreakpointInfo = Readonly<{
  breakpointId: number | undefined;
  condition: string;
  hit: boolean;
}>;

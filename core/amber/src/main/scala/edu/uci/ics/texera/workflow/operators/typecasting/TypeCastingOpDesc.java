package edu.uci.ics.texera.workflow.operators.typecasting;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import edu.uci.ics.amber.engine.common.model.PhysicalOp;
import edu.uci.ics.amber.engine.common.model.SchemaPropagationFunc;
import edu.uci.ics.amber.engine.architecture.deploysemantics.layer.OpExecInitInfo;
import edu.uci.ics.amber.engine.common.model.tuple.AttributeTypeUtils;
import edu.uci.ics.amber.engine.common.virtualidentity.ExecutionIdentity;
import edu.uci.ics.amber.engine.common.virtualidentity.WorkflowIdentity;
import edu.uci.ics.amber.engine.common.workflow.InputPort;
import edu.uci.ics.amber.engine.common.workflow.OutputPort;
import edu.uci.ics.amber.engine.common.workflow.PortIdentity;
import edu.uci.ics.texera.workflow.common.metadata.OperatorGroupConstants;
import edu.uci.ics.texera.workflow.common.metadata.OperatorInfo;
import edu.uci.ics.amber.engine.common.executor.OperatorExecutor;
import edu.uci.ics.texera.workflow.common.operators.map.MapOpDesc;


import edu.uci.ics.amber.engine.common.model.tuple.Schema;
import edu.uci.ics.texera.workflow.operators.util.OperatorDescriptorUtils;
import scala.Tuple2;
import scala.collection.immutable.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static scala.jdk.javaapi.CollectionConverters.asScala;

public class TypeCastingOpDesc extends MapOpDesc {

    @JsonProperty(required = true)
    @JsonSchemaTitle("TypeCasting Units")
    @JsonPropertyDescription("Multiple type castings")
    public java.util.List<TypeCastingUnit> typeCastingUnits = new ArrayList<>();

    @Override
    public PhysicalOp getPhysicalOp(WorkflowIdentity workflowId, ExecutionIdentity executionId) {
        if (typeCastingUnits==null) typeCastingUnits = new ArrayList<>();
        return PhysicalOp.oneToOnePhysicalOp(
                        workflowId,
                        executionId,
                        operatorIdentifier(),
                        OpExecInitInfo.apply(
                                (Function<Tuple2<Object, Object>, OperatorExecutor> & java.io.Serializable)
                                        worker -> new TypeCastingOpExec(typeCastingUnits)
                        )
                )
                .withInputPorts(operatorInfo().inputPorts())
                .withOutputPorts(operatorInfo().outputPorts())
                .withPropagateSchema(
                        SchemaPropagationFunc.apply((Function<Map<PortIdentity, Schema>, Map<PortIdentity, Schema>> & Serializable) inputSchemas -> {
                            // Initialize a Java HashMap
                            java.util.Map<PortIdentity, Schema> javaMap = new java.util.HashMap<>();
                            Schema outputSchema = inputSchemas.values().head();
                            if (typeCastingUnits != null) {
                                for (TypeCastingUnit unit : typeCastingUnits) {
                                    outputSchema = AttributeTypeUtils.SchemaCasting(outputSchema, unit.attribute, unit.resultType);
                                }
                            }

                            javaMap.put(operatorInfo().outputPorts().head().id(), outputSchema);

                            // Convert the Java Map to a Scala immutable Map
                            return OperatorDescriptorUtils.toImmutableMap(javaMap);
                        })
                );
    }

    @Override
    public OperatorInfo operatorInfo() {
        return new OperatorInfo(
                "Type Casting",
                "Cast between types",
                OperatorGroupConstants.CLEANING_GROUP(),
                asScala(singletonList(new InputPort(new PortIdentity(0, false), "", false, asScala(new ArrayList<PortIdentity>()).toSeq()))).toList(),
                asScala(singletonList(new OutputPort(new PortIdentity(0, false), "", false))).toList(),
                false,
                false,
                false,
                false
        );
    }

    @Override
    public Schema getOutputSchema(Schema[] schemas) {
        Schema outputSchema = schemas[0];
        if (typeCastingUnits != null) {
            for (TypeCastingUnit unit : typeCastingUnits) {
                outputSchema = AttributeTypeUtils.SchemaCasting(outputSchema, unit.attribute, unit.resultType);
            }
        }
        return outputSchema;
    }
}

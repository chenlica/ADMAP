package edu.uci.ics.amber.operator.projection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import edu.uci.ics.amber.operator.metadata.annotations.AutofillAttributeName;
import org.jooq.tools.StringUtils;
import java.util.Objects;

public class AttributeUnit{
    @JsonProperty(required = true)
    @JsonSchemaTitle("Attribute")
    @JsonPropertyDescription("Attribute name in the schema")
    @AutofillAttributeName
    private String originalAttribute;

    @JsonProperty
    @JsonSchemaTitle("Alias")
    @JsonPropertyDescription("Renamed attribute name")
    private String alias;

    // TODO: explore the reason why this JsonCreator annotation is required
    @JsonCreator
    public AttributeUnit(
        @JsonProperty("originalAttribute") String attributeName,
        @JsonProperty("alias") String alias) {
        this.originalAttribute = attributeName;
        this.alias = alias;
    }


    String getOriginalAttribute(){
        return originalAttribute;
    }


    String getAlias(){
        if(StringUtils.isBlank(alias)){
            return originalAttribute;
        }
        return alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeUnit that = (AttributeUnit) o;
        return Objects.equals(originalAttribute, that.originalAttribute) && Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalAttribute, alias);
    }
}
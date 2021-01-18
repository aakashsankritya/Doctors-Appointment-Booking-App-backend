package com.medizine.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public abstract class BaseModel implements Cloneable {

    @Id
    @ApiModelProperty(example = "5cdd81f49981f6a1b2578c7", dataType = "string")
    private ObjectId id;

    @JsonIgnore
    private StatusType status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return getId().equals(baseModel.getId()) && getStatus() == baseModel.getStatus();
    }
}

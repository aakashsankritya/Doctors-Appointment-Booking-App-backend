package com.medizine.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class BaseModel implements Cloneable {

    @Id
    @ApiModelProperty(example = "5cbdd81f49911f6a1b2578c7", dataType = "string")
    private ObjectId id;

    @JsonIgnore
    private StatusType status;

    private Date createdDate;

    @JsonIgnore
    private Date modifiedDate;

    @ApiModelProperty(hidden = true)
    public static void makeEmbeddable(BaseModel model) {
        model.setCreatedDate(null);
        model.setModifiedDate(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return getId().equals(baseModel.getId()) && getStatus() == baseModel.getStatus();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}

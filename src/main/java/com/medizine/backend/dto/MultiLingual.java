package com.medizine.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultiLingual {

    @ApiModelProperty(example = "Lorem Ipsum")
    private String en; //english

    @ApiModelProperty(example = "लोरम इप्सम")
    private String hi; //hindi

    @ApiModelProperty(hidden = true)
    private String gu; //gujarati

    @ApiModelProperty(hidden = true)
    private String mr; //marathi

    @ApiModelProperty(hidden = true)
    private String kn; //kannada
}

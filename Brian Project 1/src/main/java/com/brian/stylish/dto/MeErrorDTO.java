package com.brian.stylish.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeErrorDTO {
    private String message;
    private String type;
    private Integer code;
    @JsonProperty("fbtrace_id")
    private String fbtraceId;
}

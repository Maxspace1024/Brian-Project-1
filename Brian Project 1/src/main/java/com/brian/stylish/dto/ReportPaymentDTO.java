package com.brian.stylish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportPaymentDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("total_payment")
    private Integer totalPayment;
}

package com.forkcast.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forkcast.dao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("owner_email")
    private String ownerEmail;

    @JsonProperty("outlet_name")
    private String outletName;

    @JsonProperty("address_line_1")
    private String addressLine1;

    @JsonProperty("address_line_2")
    private String addressLine2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private Integer pinCode;
}

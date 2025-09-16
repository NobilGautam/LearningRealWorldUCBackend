package com.forkcast.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forkcast.dao.entities.User;
import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletRequest {

    @JsonProperty("outlet_id")
    private Long outletId;

    @JsonProperty("owner_email")
    @NonNull
    private String ownerEmail;

    @JsonProperty("outlet_name")
    @NonNull
    private String outletName;

    @JsonProperty("address_line_1")
    @NonNull
    private String addressLine1;

    @JsonProperty("address_line_2")
    @NonNull
    private String addressLine2;

    @JsonProperty("city")
    @NonNull
    private String city;

    @JsonProperty("state")
    @NonNull
    private String state;

    @JsonProperty("pin_code")
    @NonNull
    private Integer pinCode;
}

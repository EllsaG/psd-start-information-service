package com.project.psdstartinformationservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StartInformationRequestDTO {
    @JsonProperty("startInformationId")
    private short startInformationId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("activePower")
    private float activePower;
    @JsonProperty("amount")
    private short amount;
    @JsonProperty("ki")
    private float ki;
    @JsonProperty("cosf")
    private float cosf;
    @JsonProperty("tgf")
    private float tgf;

}

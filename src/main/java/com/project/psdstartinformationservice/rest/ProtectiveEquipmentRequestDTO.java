package com.project.psdstartinformationservice.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProtectiveEquipmentRequestDTO {

    private short startInformationId;
    private float activePower;
    private float cosf;

}

package com.project.psdstartinformationservice.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "protective-equipment-service")
public interface ProtectiveEquipmentServiceClient {
    @RequestMapping(value = "protectiveEquipment/create/selectionInformation", method = RequestMethod.PUT)
    void saveProtectiveEquipmentSelectionInformation(@RequestBody ProtectiveEquipmentRequestDTO protectiveEquipmentRequestDTO);
    @RequestMapping(value = "protectiveEquipment/delete/selectionInformation/{protectiveEquipmentSelectionId}", method = RequestMethod.DELETE)
    void deleteProtectiveEquipmentSelectionInformationById(@PathVariable("protectiveEquipmentSelectionId") short protectiveEquipmentSelectionId);
    @RequestMapping(value = "protectiveEquipment/check/{protectiveEquipmentSelectionId}", method = RequestMethod.GET)
    Boolean checkAvailability(@PathVariable("protectiveEquipmentSelectionId") short protectiveEquipmentSelectionId);

}

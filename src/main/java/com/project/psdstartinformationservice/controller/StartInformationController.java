package com.project.psdstartinformationservice.controller;

import com.project.psdstartinformationservice.controller.dto.StartInformationRequestDTO;
import com.project.psdstartinformationservice.controller.dto.StartInformationResponseDTO;
import com.project.psdstartinformationservice.service.StartInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StartInformationController {
    private final StartInformationService startInformationService;

    @Autowired
    public StartInformationController(StartInformationService startInformationService) {
        this.startInformationService = startInformationService;
    }

    @GetMapping("/getAllInformation")
    public StartInformationResponseDTO getAllStartInformation(){
        return startInformationService.getAllStartInformation();
    }

    @GetMapping("/getInformationByIdList/")
    public StartInformationResponseDTO getStartInformationByIdList(@RequestParam(value = "eq") List<Short> idList){
        return startInformationService.getStartInformationByIdList(idList);
    }

    @PutMapping("/create")
    public StartInformationResponseDTO createStartInformation(@RequestBody StartInformationRequestDTO startInformationRequestDTO) {
        return startInformationService.saveStartInformation(startInformationRequestDTO.getStartInformationId(),
                startInformationRequestDTO.getName(),startInformationRequestDTO.getActivePower(),
                startInformationRequestDTO.getAmount(), startInformationRequestDTO.getKi(),
                startInformationRequestDTO.getCosf(), startInformationRequestDTO.getTgf());
    }

    @PutMapping("/update")
    public StartInformationResponseDTO updateStartInformation(@RequestBody StartInformationRequestDTO startInformationRequestDTO){
        return startInformationService.updateStartInformation(startInformationRequestDTO.getStartInformationId(),
                startInformationRequestDTO.getName(),startInformationRequestDTO.getActivePower(),
                startInformationRequestDTO.getAmount(), startInformationRequestDTO.getKi(),
                startInformationRequestDTO.getCosf(), startInformationRequestDTO.getTgf());
    }

    @DeleteMapping("/delete/{startInformationId}")
    public StartInformationResponseDTO deleteStartInformationById(@PathVariable short startInformationId){
        startInformationService.deleteStartInformationById(startInformationId);
        return startInformationService.getAllStartInformation();
    }

    @GetMapping("/check/{startInformationId}")
    public Boolean checkAvailability(@PathVariable short startInformationId){
        return startInformationService.isAvailable(startInformationId);
    }


}

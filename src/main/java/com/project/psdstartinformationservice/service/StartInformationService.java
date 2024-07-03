package com.project.psdstartinformationservice.service;


import com.project.psdstartinformationservice.calculation.StartInformationCalculation;
import com.project.psdstartinformationservice.controller.dto.StartInformationResponseDTO;
import com.project.psdstartinformationservice.entity.StartInformation;
import com.project.psdstartinformationservice.exceptions.InformationNotFoundException;
import com.project.psdstartinformationservice.repository.StartInformationRepository;
import com.project.psdstartinformationservice.rest.ProtectiveEquipmentRequestDTO;
import com.project.psdstartinformationservice.rest.ProtectiveEquipmentServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartInformationService {

    private final StartInformationRepository startInformationRepository;
    private final ProtectiveEquipmentServiceClient protectiveEquipmentServiceClient;

    @Autowired
    public StartInformationService(StartInformationRepository startInformationRepository,
                                   ProtectiveEquipmentServiceClient protectiveEquipmentServiceClient) {
        this.startInformationRepository = startInformationRepository;
        this.protectiveEquipmentServiceClient = protectiveEquipmentServiceClient;
    }


    public StartInformationResponseDTO saveStartInformation(short startInformationId, String name, float power, short amount,
                                                            float ki, float cosf, float tgf) {
        StartInformationCalculation startInformationCalculation = new StartInformationCalculation();
        StartInformation startInformation = startInformationCalculation
                .createIfDontExist(startInformationRepository, startInformationId, name, power, amount, ki, cosf, tgf);

        protectiveEquipmentServiceClient.saveProtectiveEquipmentSelectionInformation(new ProtectiveEquipmentRequestDTO(
                startInformation.getStartInformationId(), startInformation.getActivePower(), startInformation.getCosf()));

        startInformationRepository.save(startInformation);

        return getAllStartInformation();
    }

    public StartInformationResponseDTO updateStartInformation(short startInformationId, String name, float power, short amount,
                                                              float ki, float cosf, float tgf) {

        deleteStartInformationById(startInformationId);

        return saveStartInformation( startInformationId,  name,  power,  amount, ki,  cosf,  tgf);
    }

    public void deleteStartInformationById(short startInformationId) {

        if (startInformationRepository.existsById(startInformationId)){
            startInformationRepository.deleteById(startInformationId);
        }

        if (protectiveEquipmentServiceClient.checkAvailability(startInformationId)){
            protectiveEquipmentServiceClient.deleteProtectiveEquipmentSelectionInformationById(startInformationId);
        }
    }

    public StartInformationResponseDTO getStartInformationByIdList(List<Short> idList) {

        List<StartInformation> allById = startInformationRepository.findAllById(idList);
        List<Short> startInformationIdList = allById.stream()
                .map(StartInformation::getStartInformationId)
                .collect(Collectors.toList());

        List<Short> equipmentsIdsNotFound  = new ArrayList<>();
            for(Short sh:idList){
                if (!startInformationIdList.contains(sh)){
                    equipmentsIdsNotFound.add(sh);
                }
            }
        if (startInformationIdList.size() == idList.size()){
            return new StartInformationResponseDTO(allById);
        }else {
            throw new InformationNotFoundException("Information about equipments with id's № "+ equipmentsIdsNotFound + " not founded");
        }
    }

    public Boolean isAvailable(short startInformationId) {
        return startInformationRepository.existsById(startInformationId);
    }


    public StartInformationResponseDTO getAllStartInformation() {
        return new StartInformationResponseDTO(startInformationRepository.findAll());
    }


}

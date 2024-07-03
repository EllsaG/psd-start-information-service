package com.project.psdstartinformationservice.calculation;


import com.project.psdstartinformationservice.entity.StartInformation;
import com.project.psdstartinformationservice.exceptions.InformationAlreadyExistsException;
import com.project.psdstartinformationservice.repository.StartInformationRepository;

import java.util.List;

public class StartInformationCalculation {

    public StartInformation createIfDontExist(StartInformationRepository startInformationRepository, short startInformId,
                                              String name, float activePower, short amount, float ki, float cosf, float tgf) {


        if (startInformationRepository.existsById(startInformId)) {
            throw new InformationAlreadyExistsException("Information about equipment with id № " + startInformId + " is already exists");
        }

        List<StartInformation> all = startInformationRepository.findAll();

        for (StartInformation startInformation : all) {
            if (startInformation.getName().equals(name) && startInformation.getActivePower() == activePower) {
                throw new InformationAlreadyExistsException("Information about equipment with name: " + name + " and power: " + activePower + " is already exists");
            }
        }

        float avgDailyActivePower = Math.round(activePower * ki * 100.0) / 100.0F; // average daily active power of one equipment
        float avgDailyReactivePower = Math.round(avgDailyActivePower * tgf * 100.0) / 100.0F; // average daily reactive power of one equipment


        return new StartInformation(startInformId, name, activePower, amount, ki, cosf, tgf, avgDailyActivePower, avgDailyReactivePower);

    }
}

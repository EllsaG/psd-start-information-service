package com.project.psdstartinformationservice.service;

import com.project.psdstartinformationservice.calculation.StartInformationCalculation;
import com.project.psdstartinformationservice.entity.StartInformation;
import com.project.psdstartinformationservice.exceptions.InformationNotFoundException;
import com.project.psdstartinformationservice.repository.StartInformationRepository;


import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StartInformationServiceTest {
    @Mock
    StartInformationRepository startInformationRepository;
    @InjectMocks
    StartInformationService startInformationService;


    @Test
    public void startInformationServiceTest_isAvailable() {
        Mockito.lenient().when(startInformationService.isAvailable((short)2)).thenReturn(true);
    }

    @Test
    public void startInformationServiceTest_saveStartInformation() {

        StartInformationCalculation startInformationCalculation = new StartInformationCalculation();
        StartInformation startInformation = startInformationCalculation
                .createIfDontExist(startInformationRepository, (short) 1,
                        "Станок вертикально сверлильный", 17.5F, (short) 5, 0.16F, 0.5F, 1.73F);

        Assertions.assertEquals(2.8F,  startInformation.getAvgDailyActivePower());
        Assertions.assertEquals(4.84F,  startInformation.getAvgDailyReactivePower());
    }


    @Test
    public void startInformationServiceTest_getStartInformationByIdList_exception() {
        List<Short> shortList = new ArrayList<>();
        shortList.add((short) 1);
        shortList.add((short) 2);
        assertThrows(InformationNotFoundException.class,
                ()->startInformationService.getStartInformationByIdList(shortList));

    }






}




package com.project.psdstartinformationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.psdstartinformationservice.config.SpringH2DatabaseConfig;
import com.project.psdstartinformationservice.PsdStartInformationServiceApplication;
import com.project.psdstartinformationservice.controller.dto.StartInformationRequestDTO;
import com.project.psdstartinformationservice.controller.dto.StartInformationResponseDTO;
import com.project.psdstartinformationservice.entity.StartInformation;
import com.project.psdstartinformationservice.repository.StartInformationRepository;
import com.project.psdstartinformationservice.rest.ProtectiveEquipmentServiceClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {PsdStartInformationServiceApplication.class, SpringH2DatabaseConfig.class})
public class StartInformationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StartInformationRepository startInformationRepository;

    @MockBean
    private ProtectiveEquipmentServiceClient protectiveEquipmentServiceClient;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql", "/sql/addStartInformation.sql"})
    public void getStartInformationByIdList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/getInformationByIdList/?eq=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        StartInformationResponseDTO startInformationResponseList = objectMapper.readValue(body, StartInformationResponseDTO.class);
        StartInformation startInformationResponse = startInformationResponseList.getStartInformationList().get(0);


        StartInformation startInformationRepositoryById = startInformationRepository.findById((short) 2)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        Assertions.assertEquals(startInformationRepositoryById.getStartInformationId(), startInformationResponse.getStartInformationId());
        Assertions.assertEquals(startInformationRepositoryById.getName(), startInformationResponse.getName());
        Assertions.assertEquals(startInformationRepositoryById.getActivePower(), startInformationResponse.getActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAmount(), startInformationResponse.getAmount());
        Assertions.assertEquals(startInformationRepositoryById.getKi(), startInformationResponse.getKi());
        Assertions.assertEquals(startInformationRepositoryById.getCosf(), startInformationResponse.getCosf());
        Assertions.assertEquals(startInformationRepositoryById.getTgf(), startInformationResponse.getTgf());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyActivePower(), startInformationResponse.getAvgDailyActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyReactivePower(), startInformationResponse.getAvgDailyReactivePower());

    }


    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql", "/sql/addStartInformation.sql"})
    public void getAllStartInformation() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get("/getAllInformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        StartInformationResponseDTO startInformationResponseList = objectMapper.readValue(body, StartInformationResponseDTO.class);
        StartInformation startInformationResponse1 = startInformationResponseList.getStartInformationList().get(0);
        StartInformation startInformationResponse2 = startInformationResponseList.getStartInformationList().get(1);

        List<StartInformation> startInformationRepositoryAll = startInformationRepository.findAll();
        StartInformation startInformation1 = startInformationRepositoryAll.get(0);
        StartInformation startInformation2 = startInformationRepositoryAll.get(1);

        Assertions.assertEquals(startInformation1.getName(), startInformationResponse1.getName());
        Assertions.assertEquals(startInformation1.getActivePower(), startInformationResponse1.getActivePower());
        Assertions.assertEquals(startInformation1.getAmount(), startInformationResponse1.getAmount());
        Assertions.assertEquals(startInformation1.getKi(), startInformationResponse1.getKi());
        Assertions.assertEquals(startInformation1.getCosf(), startInformationResponse1.getCosf());
        Assertions.assertEquals(startInformation1.getTgf(), startInformationResponse1.getTgf());
        Assertions.assertEquals(startInformation1.getAvgDailyActivePower(), startInformationResponse1.getAvgDailyActivePower());
        Assertions.assertEquals(startInformation1.getAvgDailyReactivePower(), startInformationResponse1.getAvgDailyReactivePower());

        Assertions.assertEquals(startInformation2.getName(), startInformationResponse2.getName());
        Assertions.assertEquals(startInformation2.getActivePower(), startInformationResponse2.getActivePower());
        Assertions.assertEquals(startInformation2.getAmount(), startInformationResponse2.getAmount());
        Assertions.assertEquals(startInformation2.getKi(), startInformationResponse2.getKi());
        Assertions.assertEquals(startInformation2.getCosf(), startInformationResponse2.getCosf());
        Assertions.assertEquals(startInformation2.getTgf(), startInformationResponse2.getTgf());
        Assertions.assertEquals(startInformation2.getAvgDailyActivePower(), startInformationResponse2.getAvgDailyActivePower());
        Assertions.assertEquals(startInformation2.getAvgDailyReactivePower(), startInformationResponse2.getAvgDailyReactivePower());
    }


    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql",})
    public void createStartInformation() throws Exception {

        String REQUEST = createRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/create")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        StartInformationResponseDTO startInformationResponseDTO = objectMapper.readValue(body, StartInformationResponseDTO.class);
        StartInformation startInformationResponse = startInformationResponseDTO.getStartInformationList().get(0);

        StartInformation startInformationRepositoryById = startInformationRepository.findById((short) 1)
                .orElseThrow(() -> new NoSuchElementException("No value present"));


        Assertions.assertEquals(startInformationRepositoryById.getName(), startInformationResponse.getName());
        Assertions.assertEquals(startInformationRepositoryById.getActivePower(), startInformationResponse.getActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAmount(), startInformationResponse.getAmount());
        Assertions.assertEquals(startInformationRepositoryById.getKi(), startInformationResponse.getKi());
        Assertions.assertEquals(startInformationRepositoryById.getCosf(), startInformationResponse.getCosf());
        Assertions.assertEquals(startInformationRepositoryById.getTgf(), startInformationResponse.getTgf());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyActivePower(), startInformationResponse.getAvgDailyActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyReactivePower(), startInformationResponse.getAvgDailyReactivePower());
    }

    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql", "/sql/addStartInformation.sql"})
    public void updateStartInformation() throws Exception {

        String REQUEST = updateRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/update")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        StartInformationResponseDTO startInformationResponseDTO = objectMapper.readValue(body, StartInformationResponseDTO.class);
        StartInformation startInformationResponse = startInformationResponseDTO.getStartInformationList().get(0);

        StartInformation startInformationRepositoryById = startInformationRepository.findById((short) 1)
                .orElseThrow(() -> new NoSuchElementException("No value present"));


        Assertions.assertEquals(startInformationRepositoryById.getName(), startInformationResponse.getName());
        Assertions.assertEquals(startInformationRepositoryById.getActivePower(), startInformationResponse.getActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAmount(), startInformationResponse.getAmount());
        Assertions.assertEquals(startInformationRepositoryById.getKi(), startInformationResponse.getKi());
        Assertions.assertEquals(startInformationRepositoryById.getCosf(), startInformationResponse.getCosf());
        Assertions.assertEquals(startInformationRepositoryById.getTgf(), startInformationResponse.getTgf());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyActivePower(), startInformationResponse.getAvgDailyActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyReactivePower(), startInformationResponse.getAvgDailyReactivePower());
    }

    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql", "/sql/addStartInformation.sql"})
    public void deleteStartInformationById() throws Exception {


        MvcResult mvcResult = mockMvc.perform(delete("/delete/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        StartInformationResponseDTO startInformationResponseDTO = objectMapper.readValue(body, StartInformationResponseDTO.class);
        StartInformation startInformationResponse = startInformationResponseDTO.getStartInformationList().get(0);

        StartInformation startInformationRepositoryById = startInformationRepository.findById((short) 1)
                .orElseThrow(() -> new NoSuchElementException("No value present"));


        Assertions.assertEquals(startInformationRepositoryById.getName(), startInformationResponse.getName());
        Assertions.assertEquals(startInformationRepositoryById.getActivePower(), startInformationResponse.getActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAmount(), startInformationResponse.getAmount());
        Assertions.assertEquals(startInformationRepositoryById.getKi(), startInformationResponse.getKi());
        Assertions.assertEquals(startInformationRepositoryById.getCosf(), startInformationResponse.getCosf());
        Assertions.assertEquals(startInformationRepositoryById.getTgf(), startInformationResponse.getTgf());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyActivePower(), startInformationResponse.getAvgDailyActivePower());
        Assertions.assertEquals(startInformationRepositoryById.getAvgDailyReactivePower(), startInformationResponse.getAvgDailyReactivePower());
    }

    @Test
    @Sql(scripts = {"/sql/clearStartInformationDB.sql", "/sql/addStartInformation.sql"})
    public void checkAvailability() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/check/2"))
                .andExpect(status()
                        .isOk())
                .andReturn();

        boolean fromRepository = startInformationRepository.existsById((short) 2);
        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        boolean fromResponse = objectMapper.readValue(body, Boolean.class);

        Assertions.assertEquals(fromRepository, fromResponse);
    }


    private String createRequestAsString() throws JsonProcessingException {
        StartInformationRequestDTO startInformationRequestDTO = new StartInformationRequestDTO((short) 1,
                "Станок вертикально-сверлильный", 17.5F, (short) 5, 0.16F, 0.5F, 1.73F);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(startInformationRequestDTO);

    }

    private String updateRequestAsString() throws JsonProcessingException {
        StartInformationRequestDTO startInformationRequestDTO = new StartInformationRequestDTO((short) 1,
                "Станок сверлильный", 12.5F, (short) 3, 0.26F, 0.5F, 1.73F);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(startInformationRequestDTO);

    }


}

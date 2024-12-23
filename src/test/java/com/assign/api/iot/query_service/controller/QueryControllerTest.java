package com.assign.api.iot.query_service.controller;

import com.assign.api.iot.query_service.dto.SensorDataQueryRequest;
import com.assign.api.iot.query_service.service.IoTDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QueryControllerTest {
    @Mock
    private IoTDataService ioTDataService;
    @InjectMocks
    private QueryController queryController;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public QueryControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(queryController).build();
    }

    @Test
    void testGetSensorDeviceDataMinValue_Success() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        Double minValue = 10.5;
        when(ioTDataService.getSensorDeviceDataMinValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.of(minValue));

        mockMvc.perform(get("http://localhost:8083/api/query/readings/min-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(minValue.toString()));
    }

    @Test
    void testGetSensorDeviceDataMinValue_NotFound() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        when(ioTDataService.getSensorDeviceDataMinValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:8083/api/query/readings/min-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSensorDeviceDataMedianValue_Success() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        Double medianValue = 15.5;
        when(ioTDataService.getSensorDeviceDataMedianValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.of(medianValue));

        mockMvc.perform(get("http://localhost:8083/api/query/readings/median-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(medianValue.toString()));
    }

    @Test
    void testGetSensorDeviceDataMedianValue_NotFound() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        when(ioTDataService.getSensorDeviceDataMedianValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:8083/api/query/readings/median-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSensorDeviceDataMaxValue_Success() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        Double maxValue = 10.5;
        when(ioTDataService.getSensorDeviceDataMaxValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.of(maxValue));

        mockMvc.perform(get("http://localhost:8083/api/query/readings/max-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(maxValue.toString()));
    }

    @Test
    void testGetSensorDeviceDataMaxValue_NotFound() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        when(ioTDataService.getSensorDeviceDataMaxValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:8083/api/query/readings/max-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSensorDeviceDataAverageValue_Success() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        Double averageValue = 15.5;
        when(ioTDataService.getSensorDeviceDataAverageValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.of(averageValue));

        mockMvc.perform(get("http://localhost:8083/api/query/readings/average-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(averageValue.toString()));
    }

    @Test
    void testGetSensorDeviceDataAverageValue_NotFound() throws Exception {

        SensorDataQueryRequest request = new SensorDataQueryRequest();
        request.setSensorDeviceType("THERMOSTAT");
        request.setStart(1734422578553L);
        request.setEnd(1734422585556L);
        request.setSensorDeviceTypeList(List.of("THERMOSTAT","HEART_RATE_METER"));

        when(ioTDataService.getSensorDeviceDataAverageValue(
                request.getSensorDeviceType(),
                request.getStart(),
                request.getEnd())
        ).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:8083/api/query/readings/average-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}

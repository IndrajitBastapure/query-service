package com.assign.api.iot.query_service.service;

import com.assign.api.iot.query_service.dto.IoTDataDTO;
import com.assign.api.iot.query_service.mapper.IoTDataToIoTDataDTOConverter;
import com.assign.api.iot.query_service.model.IoTData;
import com.assign.api.iot.query_service.repository.IoTDataCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IoTDataServiceTest  {
    @Mock
    private IoTDataCustomRepository ioTDataCustomRepository;
    @Mock
    private IoTDataToIoTDataDTOConverter converter;
    @InjectMocks
    private IoTDataService ioTDataService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSensorDeviceDataMinValue_Success() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Optional<Double> minValue = ioTDataService.getSensorDeviceDataMinValue(sensorDeviceType, startDate, endDate);

        assertNotNull(minValue);
        assertEquals(25.5, minValue.get());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, times(2)).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataMinValue_NoDataReturned() {

        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Optional<Double> minValue = ioTDataService.getSensorDeviceDataMinValue(sensorDeviceType, startDate, endDate);

        assertNotNull(minValue);
        assertEquals(Optional.empty(), minValue);
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, never()).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataMaxValue_Success() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Optional<Double> maxValue = ioTDataService.getSensorDeviceDataMaxValue(sensorDeviceType, startDate, endDate);

        assertNotNull(maxValue.get());
        assertEquals(27.0, maxValue.get());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, times(2)).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataMaxValue_NoDataReturned() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Optional<Double> maxValue = ioTDataService.getSensorDeviceDataMaxValue(sensorDeviceType, startDate, endDate);

        assertNotNull(maxValue);
        assertEquals(Optional.empty(), maxValue);
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, never()).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataMedianValue_Success() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Optional<Double> medianValue = ioTDataService.getSensorDeviceDataMedianValue(sensorDeviceType, startDate, endDate);

        assertNotNull(medianValue);
        assertEquals(26.25, medianValue.get());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, times(2)).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataMedianValue_NoDataReturned() {

        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Optional<Double> medianValue = ioTDataService.getSensorDeviceDataMedianValue(sensorDeviceType, startDate, endDate);

        assertNotNull(medianValue);
        assertEquals(Optional.empty(), medianValue);
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, never()).convert(any(IoTData.class));    }

    @Test
    void testGetSensorDeviceDataAverageValue_Success() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Optional<Double> averageValue = ioTDataService.getSensorDeviceDataAverageValue(sensorDeviceType, startDate, endDate);

        assertNotNull(averageValue);
        assertEquals(26.25, averageValue.get());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, times(2)).convert(any(IoTData.class));
    }

    @Test
    void testGetSensorDeviceDataAverageValue_NoDataReturned() {
        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Optional<Double> averageValue = ioTDataService.getSensorDeviceDataAverageValue(sensorDeviceType, startDate, endDate);

        assertNotNull(averageValue);
        assertEquals(Optional.empty(), averageValue);
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        verify(converter, never()).convert(any(IoTData.class));
    }
}

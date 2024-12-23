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
import java.util.Map;
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
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 24.0),
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

    @Test
    void testGetSensorDeviceDataMinValueForGroup_Success() {
        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734952665649L;
        Long endDate = 1734952672646L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Map<String, Optional<Double>> minValueForGroup = ioTDataService.getSensorDeviceDataMinValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertEquals(2, minValueForGroup.size());
        assertEquals(Optional.of(25.5), minValueForGroup.get("THERMOSTAT"));
        assertEquals(Optional.of(27.0), minValueForGroup.get("HEART_RATE_METER"));
    }

    @Test
    void testGetSensorDeviceDataMinValueForGroup_NoDataReturned() {

        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Map<String, Optional<Double>> minValueForGroup = ioTDataService.getSensorDeviceDataMinValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertTrue(minValueForGroup.isEmpty());
    }
    @Test
    void testGetSensorDeviceDataMaxValueForGroup_Success() {
        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734952665649L;
        Long endDate = 1734952672646L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 25.5);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);

        Map<String, Optional<Double>> maxValueForGroup = ioTDataService.getSensorDeviceDataMaxValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertEquals(2, maxValueForGroup.size());
        assertEquals(Optional.of(25.5), maxValueForGroup.get("THERMOSTAT"));
        assertEquals(Optional.of(27.0), maxValueForGroup.get("HEART_RATE_METER"));
    }
    @Test
    void testGetSensorDeviceDataMaxValueForGroup_NoDataReturned() {

        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Map<String, Optional<Double>> maxValueForGroup = ioTDataService.getSensorDeviceDataMaxValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertTrue(maxValueForGroup.isEmpty());
    }
    @Test
    void testGetSensorDeviceDataMedianValueForGroup_Success() {
        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734952665649L;
        Long endDate = 1734952672646L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 24.00),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0),
                new IoTData(3L, "THERMOSTAT", "device_03", 1734422578553L, 26.0),
                new IoTData(4L, "HEART_RATE_METER", "device_04", 1734422585556L, 29.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 24.0);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);
        IoTDataDTO dtoObject3 = new IoTDataDTO(3L, "THERMOSTAT", "device_03", 1734422578553L, 26.0);
        IoTDataDTO dtoObject4 = new IoTDataDTO(4L, "HEART_RATE_METER", "device_04", 1734422585556L, 29.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);
        when(converter.convert(mockDataList.get(2))).thenReturn(dtoObject3);
        when(converter.convert(mockDataList.get(3))).thenReturn(dtoObject4);

        Map<String, Optional<Double>> medianValueForGroup = ioTDataService.getSensorDeviceDataMedianValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertEquals(2, medianValueForGroup.size());
        assertEquals(Optional.of(25.0), medianValueForGroup.get("THERMOSTAT"));
        assertEquals(Optional.of(28.0), medianValueForGroup.get("HEART_RATE_METER"));
    }
    @Test
    void testGetSensorDeviceDataMedianValueForGroup_NoDataReturned() {

        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Map<String, Optional<Double>> medianValueForGroup = ioTDataService.getSensorDeviceDataMedianValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertTrue(medianValueForGroup.isEmpty());
    }

    @Test
    void testGetSensorDeviceDataAverageValueForGroup_Success() {
        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734952665649L;
        Long endDate = 1734952672646L;

        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 24.00),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0),
                new IoTData(3L, "THERMOSTAT", "device_03", 1734422578553L, 26.0),
                new IoTData(4L, "HEART_RATE_METER", "device_04", 1734422585556L, 29.0)
        );

        IoTDataDTO dtoObject1 = new IoTDataDTO(1L,"THERMOSTAT", "device_01", 1609462800000L, 24.0);
        IoTDataDTO dtoObject2 = new IoTDataDTO(2L,"HEART_RATE_METER", "device_02", 1609470000000L, 27.0);
        IoTDataDTO dtoObject3 = new IoTDataDTO(3L, "THERMOSTAT", "device_03", 1734422578553L, 26.0);
        IoTDataDTO dtoObject4 = new IoTDataDTO(4L, "HEART_RATE_METER", "device_04", 1734422585556L, 29.0);

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(mockDataList);
        when(converter.convert(mockDataList.get(0))).thenReturn(dtoObject1);
        when(converter.convert(mockDataList.get(1))).thenReturn(dtoObject2);
        when(converter.convert(mockDataList.get(2))).thenReturn(dtoObject3);
        when(converter.convert(mockDataList.get(3))).thenReturn(dtoObject4);

        Map<String, Optional<Double>> averageValueForGroup = ioTDataService.getSensorDeviceDataAverageValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertEquals(2, averageValueForGroup.size());
        assertEquals(Optional.of(25.0), averageValueForGroup.get("THERMOSTAT"));
        assertEquals(Optional.of(28.0), averageValueForGroup.get("HEART_RATE_METER"));
    }
    @Test
    void testGetSensorDeviceDataAverageValueForGroup_NoDataReturned() {

        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Map<String, Optional<Double>> averageValueForGroup = ioTDataService.getSensorDeviceDataAverageValueForGroup(sensorDeviceTypeList, startDate, endDate);

        assertTrue(averageValueForGroup.isEmpty());
    }
}

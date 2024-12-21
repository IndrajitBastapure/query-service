package com.assign.api.iot.query_service.repository;

import com.assign.api.iot.query_service.model.IoTData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IoTDataCustomRepositoryTest {
    @Mock
    private IoTDataCustomRepository ioTDataCustomRepository;

    @Test
    void testFindBySensorDeviceTypeAndTimestampBetween_Success() {

        String sensorDeviceType = "THERMOSTAT";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;
        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );
        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType,startDate,endDate)).thenReturn(mockDataList);

        List<IoTData> result = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType,startDate,endDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("device_01", result.get(0).getDeviceId());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
    }

    @Test
    void testFindBySensorDeviceTypeAndTimestampBetween_InvalidSensorDeviceType() {
        String sensorDeviceType = "INVALID";
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType,startDate,endDate)).thenReturn(Collections.emptyList());

        List<IoTData> result = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType,startDate,endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
    }

    @Test
    void testFindBySensorDeviceTypeListAndTimestampBetween_Success() {

        List<String> sensorDeviceTypeList = List.of("THERMOSTAT","HEART_RATE_METER");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;
        List<IoTData> mockDataList = List.of(
                new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5),
                new IoTData(2L, "HEART_RATE_METER", "device_02", 1734422585556L, 27.0)
        );
        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList,startDate,endDate)).thenReturn(mockDataList);

        List<IoTData> result = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList,startDate,endDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("device_01", result.get(0).getDeviceId());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);

    }

    @Test
    void testFindBySensorDeviceTypeListAndTimestampBetween_InvalidSensorDeviceTypeList() {
        List<String> sensorDeviceTypeList = List.of("INVALID","UNKNOWN");
        Long startDate = 1734422578553L;
        Long endDate = 1734422585556L;

        when(ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList,startDate,endDate)).thenReturn(Collections.emptyList());

        List<IoTData> result = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList,startDate,endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ioTDataCustomRepository, times(1))
                .findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
    }
}

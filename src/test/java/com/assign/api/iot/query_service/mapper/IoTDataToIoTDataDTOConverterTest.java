package com.assign.api.iot.query_service.mapper;

import com.assign.api.iot.query_service.dto.IoTDataDTO;
import com.assign.api.iot.query_service.model.IoTData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IoTDataToIoTDataDTOConverterTest {
    private final IoTDataToIoTDataDTOConverter converter = new IoTDataToIoTDataDTOConverter();

    @Test
    void testConvert_Success() {
        IoTData entity = new IoTData(1L, "THERMOSTAT", "device_01", 1734422578553L, 25.5);

        IoTDataDTO dto = converter.convert(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getSensorDeviceType(), dto.getSensorDeviceType());
        assertEquals(entity.getDeviceId(), dto.getDeviceId());
        assertEquals(entity.getTimestamp(), dto.getTimestamp());
        assertEquals(entity.getValue(), dto.getValue());
    }

    @Test
    void testConvert_NullEntity() {
        IoTData entity = null;

        assertThrows(NullPointerException.class, () -> converter.convert(entity));
    }

    @Test
    void testConvert_IncompleteData() {
        IoTData entity = new IoTData(null,null,"device_01",1734422578553L,23.5
        );

        IoTDataDTO dto = converter.convert(entity);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getSensorDeviceType());
        assertEquals(entity.getDeviceId(),dto.getDeviceId());
        assertEquals(entity.getTimestamp(), dto.getTimestamp());
        assertEquals(entity.getValue(), dto.getValue());
    }
}

package com.assign.api.iot.query_service.mapper;
import org.springframework.core.convert.converter.Converter;
import com.assign.api.iot.query_service.model.IoTData;
import com.assign.api.iot.query_service.dto.IoTDataDTO;
import org.springframework.stereotype.Component;
@Component
public class IoTDataToIoTDataDTOConverter implements Converter<IoTData, IoTDataDTO> {
    @Override public IoTDataDTO convert(IoTData source) {
        return new IoTDataDTO(
                source.getId(),
                source.getSensorDeviceType(),
                source.getDeviceId(),
                source.getTimestamp(),
                source.getValue()
        );
    }
}
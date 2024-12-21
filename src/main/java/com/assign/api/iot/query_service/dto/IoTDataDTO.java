package com.assign.api.iot.query_service.dto;

public class IoTDataDTO {
    private Long id;
    private String sensorDeviceType;
    private String deviceId;
    private Long timestamp;
    private Double value;

    public IoTDataDTO(Long id, String sensorDeviceType, String deviceId, Long timestamp, Double value) {
        this.id = id;
        this.sensorDeviceType = sensorDeviceType;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getSensorDeviceType() {
        return sensorDeviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IoTDataDTO{" +
                "id=" + id +
                ", sensorDeviceType='" + sensorDeviceType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}
package com.assign.api.iot.query_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "iot_data")
public class IoTData{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Column(name = "sensor_device_type", nullable = false)
        String sensorDeviceType;
        @Column(name = "device_id", nullable = false)
        String deviceId;
        @Column(name = "timestamp", nullable = false)
        Long timestamp;
        @Column(name = "value", nullable = false)
        Double value;

        public IoTData() {
        }
        public IoTData(Long id, String sensorDeviceType, String deviceId, Long timestamp, Double value) {
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
}


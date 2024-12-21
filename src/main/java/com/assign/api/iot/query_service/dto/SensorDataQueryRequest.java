package com.assign.api.iot.query_service.dto;
import java.util.List;
public class SensorDataQueryRequest {
    private List<String> sensorDeviceTypeList;
    private String sensorDeviceType;
    private Long start;
    private Long end;

    public List<String> getSensorDeviceTypeList() {
        return sensorDeviceTypeList;
    }
    public void setSensorDeviceTypeList(List<String> sensorDeviceTypeList) {
        this.sensorDeviceTypeList = sensorDeviceTypeList;
    }
    public String getSensorDeviceType() {
        return sensorDeviceType;
    }
    public void setSensorDeviceType(String sensorDeviceType) {
        this.sensorDeviceType = sensorDeviceType;
    }
    public Long getStart() {
        return start;
    }
    public void setStart(Long start) {
        this.start = start;
    }
    public Long getEnd() {
        return end;
    }
    public void setEnd(Long end) {
        this.end = end;
    }
}

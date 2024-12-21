package com.assign.api.iot.query_service.controller;

import com.assign.api.iot.query_service.dto.SensorDataQueryRequest;
import com.assign.api.iot.query_service.service.IoTDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/query/readings")
public class QueryController {
    private final IoTDataService ioTDataService;

    public QueryController(IoTDataService ioTDataService) {
        this.ioTDataService = ioTDataService;
    }
    @GetMapping("/min-value")
    public ResponseEntity<Double> getSensorDeviceDataMinValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMinValue(queryRequest.getSensorDeviceType(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/median-value")
    public ResponseEntity<Double> getSensorDeviceDataMedianValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMedianValue(queryRequest.getSensorDeviceType(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/max-value")
    public ResponseEntity<Double> getSensorDeviceDataMaxValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMaxValue(queryRequest.getSensorDeviceType(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/average-value")
    public ResponseEntity<Double> getSensorDeviceDataMaxAverageValue(
            @RequestBody SensorDataQueryRequest queryRequest) {
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataAverageValue(queryRequest.getSensorDeviceType(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/min-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMinValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMinValueForGroup(queryRequest.getSensorDeviceTypeList(),queryRequest.getStart(),queryRequest.getEnd()))
                .filter(result -> !result.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/median-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMedianValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMedianValueForGroup(queryRequest.getSensorDeviceTypeList(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/max-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMaxValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataMaxValueForGroup(queryRequest.getSensorDeviceTypeList(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/average-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMaxAverageValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest) {
        return Optional.ofNullable(ioTDataService.getSensorDeviceDataAverageValueForGroup(queryRequest.getSensorDeviceTypeList(),queryRequest.getStart(),queryRequest.getEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

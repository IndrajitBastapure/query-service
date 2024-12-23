package com.assign.api.iot.query_service.controller;

import com.assign.api.iot.query_service.dto.SensorDataQueryRequest;
import com.assign.api.iot.query_service.service.IoTDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/query/readings")
public class QueryController {
    private final IoTDataService ioTDataService;

    public QueryController(IoTDataService ioTDataService) {
        this.ioTDataService = ioTDataService;
    }
    @Operation(summary = "Get the minimum value of a specific sensor", description = "Fetches the minimum sensor reading for a specific sensor device type within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/min-value")
    public ResponseEntity<Double> getSensorDeviceDataMinValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        Optional<Double> minValue = ioTDataService.getSensorDeviceDataMinValue(
                queryRequest.getSensorDeviceType(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return minValue.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Get the median value of a specific sensor", description = "Fetches the median sensor reading for a specific sensor device type within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/median-value")
    public ResponseEntity<Double> getSensorDeviceDataMedianValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        Optional<Double> medianValue = ioTDataService.getSensorDeviceDataMedianValue(
                queryRequest.getSensorDeviceType(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return medianValue.map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Get the maximum value of a specific sensor", description = "Fetches the maximum sensor reading for a specific sensor device type within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/max-value")
    public ResponseEntity<Double> getSensorDeviceDataMaxValue(
            @RequestBody SensorDataQueryRequest queryRequest){
        Optional<Double> maxValue = ioTDataService.getSensorDeviceDataMaxValue(
                queryRequest.getSensorDeviceType(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return maxValue.map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Get the average value of a specific sensor", description = "Fetches the average sensor reading for a specific sensor device type within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/average-value")
    public ResponseEntity<Double> getSensorDeviceDataMaxAverageValue(
            @RequestBody SensorDataQueryRequest queryRequest) {
        Optional<Double> averageValue = ioTDataService.getSensorDeviceDataAverageValue(
                queryRequest.getSensorDeviceType(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return averageValue.map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Get the minimum value of a group of sensors", description = "Fetches the minimum sensor reading for a provided group of sensor device types within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/min-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMinValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        Map<String, Optional<Double>> minValueGroup = ioTDataService.getSensorDeviceDataMinValueForGroup(
                queryRequest.getSensorDeviceTypeList(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return getMapResponseEntity(minValueGroup);
    }
    @Operation(summary = "Get the median value of a group of sensors", description = "Fetches the median sensor reading for a provided group of sensor device types within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/median-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMedianValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        Map<String, Optional<Double>> medianValueGroupResult = ioTDataService.getSensorDeviceDataMedianValueForGroup(
                queryRequest.getSensorDeviceTypeList(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return getMapResponseEntity(medianValueGroupResult);
    }
    @Operation(summary = "Get the maximum value of a group of sensors", description = "Fetches the maximum sensor reading for a provided group of sensor device types within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/max-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMaxValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest){
        Map<String, Optional<Double>> maxValueGroup = ioTDataService.getSensorDeviceDataMaxValueForGroup(
                queryRequest.getSensorDeviceTypeList(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return getMapResponseEntity(maxValueGroup);
    }
    @Operation(summary = "Get the average value of a group of sensors", description = "Fetches the average sensor reading for a provided group of sensor device types within a specified timeframe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/average-value/group")
    public ResponseEntity<Map<String, Double>> getSensorDeviceDataMaxAverageValueGroup(
            @RequestBody SensorDataQueryRequest queryRequest) {
        Map<String, Optional<Double>> averageValueGroup = ioTDataService.getSensorDeviceDataAverageValueForGroup(
                queryRequest.getSensorDeviceTypeList(),
                queryRequest.getStart(),
                queryRequest.getEnd());
        return getMapResponseEntity(averageValueGroup);
    }
    private ResponseEntity<Map<String, Double>> getMapResponseEntity(Map<String, Optional<Double>> valueGroup) {
        Map<String, Double> maxValueGroupResult = valueGroup.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap( Map.Entry::getKey, entry -> entry.getValue().get()
                ));
        return maxValueGroupResult.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(maxValueGroupResult);
    }
}

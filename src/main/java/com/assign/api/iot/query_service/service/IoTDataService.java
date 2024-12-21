package com.assign.api.iot.query_service.service;

import com.assign.api.iot.query_service.dto.IoTDataDTO;
import com.assign.api.iot.query_service.mapper.IoTDataToIoTDataDTOConverter;
import com.assign.api.iot.query_service.model.IoTData;
import com.assign.api.iot.query_service.repository.IoTDataCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IoTDataService {
    private static final Logger logger = LoggerFactory.getLogger(IoTDataService.class);
    private final IoTDataCustomRepository ioTDataCustomRepository;
    private final IoTDataToIoTDataDTOConverter converter;

    @Autowired
    public IoTDataService(IoTDataCustomRepository ioTDataCustomRepository, IoTDataToIoTDataDTOConverter converter){
        this.ioTDataCustomRepository = ioTDataCustomRepository;
        this.converter = converter;
    }

    public Double getSensorDeviceDataMinValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MinValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .min()
                .orElse(0.0);
    }

    public Double getSensorDeviceDataMaxValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MaxValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .max()
                .orElse(0.0);
    }

    public Double getSensorDeviceDataMedianValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MediaValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return getMedianValue(sensorDeviceData);
    }

    public Double getSensorDeviceDataAverageValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching AverageValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .average().orElse(0.0);
    }

    public Map<String, Double> getSensorDeviceDataMinValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching MinValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return sensorDeviceDataList.stream()
                .map(converter::convert)
                .collect(Collectors.groupingBy(
                        IoTDataDTO::getSensorDeviceType,
                        Collectors.mapping(
                                IoTDataDTO::getValue,
                                Collectors.minBy(Double::compareTo)
                        )
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get()
                ));
    }

    public Map<String, Double> getSensorDeviceDataMaxValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching MaxValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return sensorDeviceDataList.stream()
                .map(converter::convert)
                .collect(Collectors.groupingBy(
                        IoTDataDTO::getSensorDeviceType,
                        Collectors.mapping(
                                IoTDataDTO::getValue,
                                Collectors.maxBy(Double::compareTo)
                        )
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get()
                ));
    }

    public Map<String, Double> getSensorDeviceDataMedianValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching MedianValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return getMedianValueForGroup(sensorDeviceDataList);
    }

    public Map<String, Double> getSensorDeviceDataAverageValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching AverageValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return sensorDeviceDataList.stream()
                .map(converter::convert)
                .collect(Collectors.groupingBy(
                        IoTDataDTO::getSensorDeviceType,
                        Collectors.averagingDouble(IoTDataDTO::getValue)
                ));
    }

    private Double getMedianValue(List<IoTData> sensorDeviceDataList) {
        List<Double> sortedValues = sensorDeviceDataList.stream()
                .map(converter::convert)
                .map(data-> data.getValue())
                .sorted()
                .toList();
        double medianValue;
        int size = sortedValues.size();
        if (sortedValues == null || sortedValues.isEmpty()) {
            throw new IllegalArgumentException("The list of values cannot be null or empty.");
        }
        if (size % 2 == 0) {
            medianValue = (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            medianValue = sortedValues.get(size / 2);
        }
        return medianValue;
    }

    private Map<String, Double> getMedianValueForGroup(List<IoTData> sensorDeviceDataList) {
        return sensorDeviceDataList.stream()
                .map(converter::convert)
                .collect(Collectors.groupingBy(
                        IoTDataDTO::getSensorDeviceType,
                        Collectors.collectingAndThen(
                                Collectors.mapping(IoTDataDTO::getValue, Collectors.toList()),
                                this::calculateMedian
                        )
                ));
    }
    private Double calculateMedian(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        List<Double> sortedValues = values.stream()
                .sorted()
                .toList();

        int size = sortedValues.size();
        if (size % 2 == 0) {
            return (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            return sortedValues.get(size / 2);
        }
    }
}

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
import java.util.Optional;
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

    public Optional<Double> getSensorDeviceDataMinValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MinValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .min()
                .stream()
                .boxed()
                .findFirst();
    }

    public Optional<Double> getSensorDeviceDataMaxValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MaxValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .max()
                .stream()
                .boxed()
                .findFirst();
    }

    public Optional<Double> getSensorDeviceDataMedianValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching MediaValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return getMedianValue(sensorDeviceData);
    }

    public Optional<Double> getSensorDeviceDataAverageValue(String sensorDeviceType, Long startDate, Long endDate) {
        logger.debug("Fetching AverageValue for : {}",sensorDeviceType);
        List<IoTData> sensorDeviceData = ioTDataCustomRepository.findBySensorDeviceTypeAndTimestampBetween(sensorDeviceType, startDate, endDate);
        return sensorDeviceData.stream()
                .map(converter::convert)
                .mapToDouble(data-> data.getValue())
                .average()
                .stream()
                .boxed()
                .findFirst();
    }

    public Map<String, Optional<Double>> getSensorDeviceDataMinValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
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
                ));
    }

    public Map<String, Optional<Double>> getSensorDeviceDataMaxValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
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
                ));
    }

    public Map<String, Optional<Double>> getSensorDeviceDataMedianValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching MedianValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return getMedianValueForGroup(sensorDeviceDataList);
    }

    public Map<String, Optional<Double>> getSensorDeviceDataAverageValueForGroup(List<String> sensorDeviceTypeList, Long startDate, Long endDate) {
        logger.debug("Fetching AverageValue for group: {}",sensorDeviceTypeList);
        List<IoTData> sensorDeviceDataList = ioTDataCustomRepository.findBySensorDeviceTypesAndTimestampBetween(sensorDeviceTypeList, startDate, endDate);
        return sensorDeviceDataList.stream()
                .map(converter::convert)
                .collect(Collectors.groupingBy(
                        IoTDataDTO::getSensorDeviceType,
                        Collectors.collectingAndThen( Collectors.mapping(IoTDataDTO::getValue, Collectors.toList()), this::calculateAverage )
                ));
    }

    private Map<String, Optional<Double>> getMedianValueForGroup(List<IoTData> sensorDeviceDataList) {
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
    private Optional<Double> calculateMedian(List<Double> values) {
        if (values == null || values.isEmpty() || values.size() == 0) {
            return Optional.empty();
        }
        List<Double> sortedValues = values.stream()
                .sorted()
                .toList();
        int size = sortedValues.size();
        double medianValue;
        if (size % 2 == 0) {
            medianValue = (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            medianValue = sortedValues.get(size / 2);
        }
        return Optional.of(medianValue);
    }
    private Optional<Double> getMedianValue(List<IoTData> sensorDeviceDataList) {
        List<Double> sortedValues = sensorDeviceDataList.stream()
                .map(converter::convert)
                .map(data-> data.getValue())
                .sorted()
                .toList();
        double medianValue;
        int size = sortedValues.size();
        if (sortedValues == null || sortedValues.isEmpty() || size == 0) {
            return Optional.empty();
        }
        if (size % 2 == 0) {
            medianValue = (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            medianValue = sortedValues.get(size / 2);
        }
        return Optional.of(medianValue);
    }
    private Optional<Double> calculateAverage(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        double sum = values.stream().mapToDouble(Double::doubleValue).sum();
        double average = sum / values.size();
        return Optional.of(average);
    }
}

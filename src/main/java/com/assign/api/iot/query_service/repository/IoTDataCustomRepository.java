package com.assign.api.iot.query_service.repository;

import com.assign.api.iot.query_service.model.IoTData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IoTDataCustomRepository extends JpaRepository<IoTData, Long> {
    @Query("SELECT d FROM IoTData d WHERE d.sensorDeviceType = :sensorDeviceType AND d.timestamp BETWEEN :startDate AND :endDate")
    List<IoTData> findBySensorDeviceTypeAndTimestampBetween(
            @Param("sensorDeviceType") String sensorDeviceType,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate
    );
    @Query("SELECT d FROM IoTData d WHERE d.sensorDeviceType IN :sensorDeviceTypes AND d.timestamp BETWEEN :startDate AND :endDate")
    List<IoTData> findBySensorDeviceTypesAndTimestampBetween(
            @Param("sensorDeviceTypes") List<String> sensorDeviceTypes,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate
    );
}

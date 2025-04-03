package com.gozde.service;

import com.gozde.model.WaterConsumption;

import java.util.List;


public interface WaterConsumptionService {

    List<WaterConsumption> findAll();
    List<WaterConsumption> findTopTenConsumers();

}

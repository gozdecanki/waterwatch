package com.gozde.controller;

import com.gozde.model.WaterConsumption;
import com.gozde.service.WaterConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/waterconsumption")
public class WaterConsumptionController {

    @Autowired
    private WaterConsumptionService waterConsumptionService;

    @PostConstruct
    public void init() {
        System.out.println("WaterConsumptionController initialized!");
    }


    @GetMapping("/findall")
    public List<WaterConsumption> findAll() {
        return waterConsumptionService.findAll();
    }

    @GetMapping(path = "/topten")
    public List<WaterConsumption> findTopTenConsumers() {
        return waterConsumptionService.findTopTenConsumers();
    }
}

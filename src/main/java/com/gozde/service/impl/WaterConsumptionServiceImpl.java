package com.gozde.service.impl;

import com.gozde.model.WaterConsumption;
import com.gozde.repository.WaterConsumptionRepository;
import com.gozde.service.WaterConsumptionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class WaterConsumptionServiceImpl implements WaterConsumptionService {

    @Autowired
    private WaterConsumptionRepository waterConsumptionRepository;

    public List<WaterConsumption> findAll() {
        save_csv();
        return waterConsumptionRepository.findAll();
    }

    public List<WaterConsumption> findTopTenConsumers() {
        return waterConsumptionRepository.findTopTenConsumers();
    }

    public void save_csv() {
        //read all the date from our table and store it in the response object
        List<WaterConsumption> res = waterConsumptionRepository.findAll();
        if (res.isEmpty()) {
            System.out.println("There is no data");

            String[] HEADERS = {"Suburb", "AverageMonthlyKL", "Latitude", "Longitude"}; //csv dosyasındaki sütun başlıkları
            String fileLocation = "C:\\Users\\Gözde\\Documents\\waterwatch\\src\\main\\resources\\waterwatch_data.csv";

            try {
                Reader in = new FileReader(fileLocation);

                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withHeader(HEADERS)
                        .withFirstRecordAsHeader()
                        .parse(in);

                for (CSVRecord record : records) {
                    String suburb = record.get("Suburb");
                    String averageMonthlyKL = record.get("AverageMonthlyKL");
                    String latitude = record.get("Latitude");
                    String longitude = record.get("Longitude");

                    // Convert to proper data types
                    Integer avgMonthlyKL = Integer.valueOf(averageMonthlyKL);
                    Double dLatitude = Double.parseDouble(latitude);
                    Double dLongitude = Double.parseDouble(longitude);
                    Point geom = new GeometryFactory().createPoint(new Coordinate(dLongitude, dLatitude));

                    //Load data into our WaterConsumption Table
                    WaterConsumption wc = new WaterConsumption();
                    wc.setSuburb(suburb);
                    wc.setGeom(geom);
                    wc.setAvgMonthlyKL(avgMonthlyKL);

                    waterConsumptionRepository.save(wc);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Data loaded.");
        }

    }
}

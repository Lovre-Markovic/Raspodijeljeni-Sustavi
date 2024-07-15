package com.example.HumidityMicroService.Readings;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadingService {
    public static List<Reading> loadReadings() {




        List<Reading> readings = new ArrayList<>();
        String file = "C:\\RaspSus\\HumidityMicroService\\readings.txt";

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {


            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {

                if (firstLine) {

                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                Reading reading = new Reading();

                reading.setTemperature(Integer.parseInt(parts[0]));

                reading.setHumidity(Integer.parseInt(parts[2]));



                readings.add(reading);

            }

        } catch(IOException e){
            e.printStackTrace();

        }

        //System.out.println(readings);
        return readings;

    }

}

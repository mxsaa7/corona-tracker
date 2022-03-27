package com.example.coronatracker.services;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;


import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.coronatracker.models.LocationStats;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaDataService {
    
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private static String US_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    private List<LocationStats> allStatsUS = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public List<LocationStats> getAllStatsUS(){
        return allStatsUS;
    }
    @PostConstruct
    @Scheduled(cron = "* * 1 * * * ")
    public void fetchVirusData() throws IOException, InterruptedException{
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();  
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); 
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records){
            LocationStats obj = new LocationStats();
            obj.setLatestCases(Integer.parseInt(record.get(record.size() - 1)));
            obj.setCountry(record.get("Country/Region"));
            obj.setProvince(record.get("Province/State"));
            if(obj.getProvince() == null){
                System.out.println(obj.province);
            } 
            newStats.add(obj);
            
        }

       this.allStats = newStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchUSVirusData() throws IOException, InterruptedException{
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(US_VIRUS_DATA_URL)).build();  
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); 
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for(CSVRecord record : records){
            LocationStats obj = new LocationStats();
            obj.setLatestCases(Integer.parseInt(record.get(record.size() - 1)));
            obj.setCountry(record.get("Country_Region"));
            obj.setCity(record.get("Admin2"));
            obj.setProvince(record.get("Province_State"));
            newStats.add(obj);
        }
        this.allStatsUS = newStats;
    }



}

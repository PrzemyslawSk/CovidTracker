package io.java.coronavirustracker.services;

import io.java.coronavirustracker.models.LocationDeathStats;
import io.java.coronavirustracker.models.LocationInfectedStats;
import io.java.coronavirustracker.models.LocationRecoveredStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {

    private static String VIRUS_INFECTED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static String VIRUS_RECOVERED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private List<LocationInfectedStats> infectedStats = new ArrayList<>();
    private List<LocationDeathStats> deathStats = new ArrayList<>();
    private List<LocationRecoveredStats> recoveredStats = new ArrayList<>();


    public List<LocationInfectedStats> getInfectedStats() {
        return infectedStats;
    }
    public List<LocationDeathStats> getDeathStats() {
        return deathStats;
    }
    public List<LocationRecoveredStats> getRecoveredStats() {
        return recoveredStats;
    }


    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCovidData() throws IOException, InterruptedException {
        List<LocationInfectedStats> newInfectedStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_INFECTED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader stringReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            LocationInfectedStats locationStat = new LocationInfectedStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int lastCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            locationStat.setLastTotalCases(lastCases);
            locationStat.setNewCases(lastCases - prevDayCases);
            newInfectedStats.add(locationStat);
        }
        this.infectedStats = newInfectedStats;

        List<LocationDeathStats> newDeathStats = new ArrayList<>();
        request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DEATHS_DATA_URL))
                .build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        stringReader = new StringReader(httpResponse.body());
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            LocationDeathStats locationDeathStat = new LocationDeathStats();
            locationDeathStat.setState(record.get("Province/State"));
            locationDeathStat.setCountry(record.get("Country/Region"));
            int lastCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            locationDeathStat.setLastTotalCases(lastCases);
            locationDeathStat.setNewCases(lastCases - prevDayCases);
            newDeathStats.add(locationDeathStat);
        }
        this.deathStats = newDeathStats;

        List<LocationRecoveredStats> newRecoveredStats = new ArrayList<>();
        request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_RECOVERED_DATA_URL))
                .build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        stringReader = new StringReader(httpResponse.body());
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            LocationRecoveredStats locationRecoveredStat = new LocationRecoveredStats();
            locationRecoveredStat.setState(record.get("Province/State"));
            locationRecoveredStat.setCountry(record.get("Country/Region"));
            int lastCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            locationRecoveredStat.setLastTotalCases(lastCases);
            locationRecoveredStat.setNewCases(lastCases - prevDayCases);
            newRecoveredStats.add(locationRecoveredStat);
        }
        this.recoveredStats = newRecoveredStats;
    }

}

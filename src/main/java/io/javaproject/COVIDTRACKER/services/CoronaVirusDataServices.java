package io.javaproject.COVIDTRACKER.services;

import io.javaproject.COVIDTRACKER.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// creating a service to call call the method of this class
// when a programm start
@Service
public class CoronaVirusDataServices {
    // to crete a http call to virus data url
    private static String virus_data_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    // ----------------------------------------------------------

    // creating instace of location stats in the form of array list
    private List<LocationStats> allStats = new ArrayList<>();


    public List<LocationStats> getAllStats() {
        return allStats;
    }

    //-------------------------------------------
    // now we will tell spring to execute
    // what it does it, when insrtance of this is creted
    // it automatically calls the method of the class
    @PostConstruct
    //-----------------------------------------------

    // to runs this on daily basis
    // we need to schedule this on  daily basis automatically
    @Scheduled(cron = "* * 1 * * *")
    //------------------------------------------------------

    // crete a method to fetch the data
    public void fetchVirusData() throws IOException, InterruptedException {
        // creting new status of array list
        // while we are creating new status for our website if some one trying to
        // access the website it will not show error
        // it will show old data util newStatus is not fetched
        List<LocationStats> newStats = new ArrayList<>();

    // calling for java http clinet - way to call http
        HttpClient client  = HttpClient.newHttpClient();

        // creating a  http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(virus_data_url))
                .build();


        // get a response to from client
        // get a synchronous
        HttpResponse<String> httpresponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpresponse.body());

        // creting a reader to read data from csv file
        // and convert it to string
        StringReader CSVbodyReader = new StringReader(httpresponse.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(CSVbodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setStates(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCase(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
    this.allStats = newStats;
    }

}

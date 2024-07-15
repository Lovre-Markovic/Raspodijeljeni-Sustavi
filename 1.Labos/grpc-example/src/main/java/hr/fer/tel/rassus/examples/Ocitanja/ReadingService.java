package hr.fer.tel.rassus.examples.Ocitanja;

import hr.fer.tel.rassus.examples.Ocitanje;
import hr.fer.tel.rassus.examples.Reading;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ReadingService {

    @POST("/RaspSus/Ocitanje/{sensorId}/ocitanja")
    Call<Void> saveReading(@Path("sensorId") String sensorId, @Body Ocitanje reading);

    @GET("/RaspSus/Ocitanje/{sensorId}/svaOcitanja")
    Call<List<Ocitanje>> getAllReadings(@Path("sensorId") String sensorId);
}

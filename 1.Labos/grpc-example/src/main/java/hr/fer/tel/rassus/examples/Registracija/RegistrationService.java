package hr.fer.tel.rassus.examples.Registracija;


import hr.fer.tel.rassus.examples.Registration;
import hr.fer.tel.rassus.examples.Sensor;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface RegistrationService {

    @POST("/RaspSus/registracijaSenzora/")
    Call<Void> registerSensor(@Body Registration registrationData);


    @POST("/RaspSus/NajbliziSusjed/")
    Call<Registration> nearestNeighbour(@Body Registration registrationData);


    @GET("/RaspSus/SviSenzori")
    Call<List<Registration>> getSensors();


}

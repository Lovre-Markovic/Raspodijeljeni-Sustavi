package hr.fer.tel.rassus.examples;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import hr.fer.tel.rassus.examples.Registracija.RegistrationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Random;
import java.util.logging.Logger;


//Ova klasa interpretira Senzor

public class Registration {


    private static final Logger logger = Logger.getLogger(Registration.class.getName());

    @SerializedName("port")
    static int port = (int) (Math.random() * (4000 - 3000 + 1) + 3000);

    @SerializedName("id")
    private long id;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("ip")
    private String ip;


    public Registration(long id, double longitude, double latitude, String ip, int port) {
        this.id = 0;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ip = ip;
        this.port = port;
    }

    public Registration() {

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public static Registration initializeClient(String host) {
        double longitude = 15.87 + (Math.random() * 0.13);
        double latitude = 45.75 + (Math.random() * 0.1);

        Random random = new Random();
// Generate a random integer between 1 and 1000
        int randomNumber = random.nextInt(4000-3000) + 1+3000;


        Registration registrationData = new Registration(0, longitude,
                latitude, host, randomNumber);

        registerClient(registrationData);

        return registrationData;

    }

    public static void registerClient(Registration registrationData) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistrationService registrationService = retrofit.create(RegistrationService.class);

        double roundedLongitude = Math.round(registrationData.getLongitude() * 100.0) / 100.0;
        double roundedLatitude = Math.round(registrationData.getLatitude() * 100.0) / 100.0;

        registrationData.setLongitude(roundedLongitude);
        registrationData.setLatitude(roundedLatitude);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Call<Void> call = registrationService.registerSensor(registrationData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Uspješno registriran senzor

                    logger.info("Senzor je uspješno registriran. Poruka: " + response.code());
                } else {
                    // Neuspješan odgovor od poslužitelja
                    logger.severe("Registracija senzora nije uspjela. Kod odgovora: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Greška prilikom izvođenja zahtjeva
                logger.severe("Greška prilikom slanja zahtjeva za registraciju: " + t.getMessage());
            }
        });

    }
}

package hr.fer.tel.rassus.examples;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hr.fer.tel.rassus.examples.Ocitanja.ReadingService;
import hr.fer.tel.rassus.examples.Registracija.RegistrationService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;



/**
 * The type Simple unary rpc client.
 */
public class SimpleUnaryRPCClient {

  private static final Logger logger = Logger.getLogger(SimpleUnaryRPCClient.class.getName());

  private String host;
  private int port;


  private final ManagedChannel channel;
  private final UppercaseGrpc.UppercaseBlockingStub uppercaseBlockingStub;

  /**
   * Instantiates a new Simple unary rpc client.
   *
   * @param host the host
   * @param port the port
   */
  public SimpleUnaryRPCClient(String host, int port) {
    this.host = host;
    this.port = port;
    this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

    uppercaseBlockingStub = UppercaseGrpc.newBlockingStub(channel);

  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }


  private static Registration nearestNeighbour(Registration registrationData) {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8090/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RegistrationService registrationService = retrofit.create(RegistrationService.class);

    double roundedLongitude = Math.round(registrationData.getLongitude() * 100.0) / 100.0;
    double roundedLatitude = Math.round(registrationData.getLatitude() * 100.0) / 100.0;
    registrationData.setLongitude(roundedLongitude);
    registrationData.setLatitude(roundedLatitude);


    Call<Registration> call = registrationService.nearestNeighbour(registrationData);

    Gson gson = new Gson();

    CompletableFuture<Registration> future = new CompletableFuture<>();

    call.enqueue(new Callback<Registration>() {
      @Override
      public void onResponse(Call<Registration> call, Response<Registration> response) {
        if (response.isSuccessful()) {
          // Uspješno registriran senzor

          Registration najbliziSusjed = response.body();
          future.complete(najbliziSusjed);


          logger.info("Poslužitelj je uspješno vratio najbližeg susjeda. Poruka: " + response.code());

        } else {
          // Neuspješan odgovor od poslužitelja
          future.completeExceptionally(new RuntimeException("Poslužitelj nije usio vratiti najbližeg susjeda. Kod odgovora: " + response.code()));
        }
      }


      @Override
      public void onFailure(Call<Registration> call, Throwable t) {
        // Greška prilikom izvođenja zahtjeva
        future.completeExceptionally(t);
        logger.severe("Greška prilikom slanja zahtjeva: " + t.getMessage());
      }
    });

    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Greška prilikom dohvaćanja najbližeg susjeda", e);
    }


  }

  /**
   * Stop the client.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void stop() throws InterruptedException {
//    Initiates an orderly shutdown in which preexisting calls continue but new calls are
//    immediately cancelled. Waits for the channel to become terminated, giving up if the timeout
//    is reached.
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws InterruptedException the interrupted exception
   */
  public static void main(String[] args) throws InterruptedException {

    Registration registrationData1 = new Registration();

    registrationData1 = Registration.initializeClient("localhost");

    SimpleUnaryRPCServer server = new SimpleUnaryRPCServer(new UppercaseService(), registrationData1.getPort());

    List<Ocitanje> readings = loadReadings();
    System.out.println(readings.size());

    long startTimeMillis = System.currentTimeMillis();


    String sensorId1 = UUID.randomUUID().toString();



    Sensor sensor1 = new Sensor(sensorId1, registrationData1);



    Thread.sleep(1000);


    while (true) { // Beskonačna petlja

      // Generiranje očitanja
      long currentTimeMillis1 = System.currentTimeMillis();
      Ocitanje reading1 = new Ocitanje().getReading(readings, startTimeMillis, currentTimeMillis1);
      sensor1.setReadingOfSensor(reading1);
      System.out.print(reading1);

      // Dohvaćanje najbližeg susjeda
      Registration najbliziSusjed1 = nearestNeighbour(registrationData1);

      SimpleUnaryRPCClient SusjedClient = new SimpleUnaryRPCClient(najbliziSusjed1.getIp(), najbliziSusjed1.getPort());




      Ocitanje kalibracija = new Ocitanje();
      if (najbliziSusjed1 != null) {
        kalibracija = SusjedClient.RequestReadingFromNeighbour(sensor1, najbliziSusjed1);
      } else {
        kalibracija = sensor1.getReadingOfSensor();
        logger.info("Susjeda ne postoji");
      }
      SendCalibratedReading(sensor1, kalibracija);

      Thread.sleep(2000);

      Thread.sleep(5000); // Primjer: 10 sekundi
    }


  }

  private Ocitanje RequestReadingFromNeighbour(Sensor trenutniSenzor, Registration najbliziSusjed1) {

    ManagedChannel channel = ManagedChannelBuilder.forAddress(najbliziSusjed1.getIp(), najbliziSusjed1.getPort()).usePlaintext().build();
    UppercaseGrpc.UppercaseBlockingStub stub = UppercaseGrpc.newBlockingStub(channel);





    Reading trenutni = Reading.newBuilder()
            .setTemperature(trenutniSenzor.getReadingOfSensor().getTemperature())
            .setHumidity(trenutniSenzor.getReadingOfSensor().getHumidity())
            .setPressure(trenutniSenzor.getReadingOfSensor().getPressure())
            .setCo(trenutniSenzor.getReadingOfSensor().getCoConcentration())
            .setNo2(trenutniSenzor.getReadingOfSensor().getNo2Concentration())
            .setSo2(trenutniSenzor.getReadingOfSensor().getSo2Concentration())
            .build();

    Reading susjed = stub.sendReading(Reading.newBuilder().build());

    System.out.println("Trenutni  " + trenutni);

    System.out.println("Susjed  "+  susjed);



    Ocitanje kalibracija = new Ocitanje();

    if(susjed != null) {

      kalibracija.setTemperature((trenutni.getTemperature() + susjed.getTemperature()) / 2);
      kalibracija.setHumidity((trenutni.getHumidity()+ susjed.getHumidity())/2);
      kalibracija.setPressure((trenutni.getPressure()+ susjed.getPressure())/2);
      kalibracija.setCoConcentration((trenutni.getCo()+ susjed.getCo())/2);
      if(trenutni.getNo2()==0 && susjed.getNo2()==0) {
        kalibracija.setNo2Concentration(0);
      } else {
        kalibracija.setNo2Concentration((trenutni.getNo2()+ susjed.getNo2())/2);
      }
      if(trenutni.getSo2()==0 && susjed.getSo2()==0) {
        kalibracija.setSo2Concentration(0);
      } else {
        kalibracija.setSo2Concentration((trenutni.getSo2()+ susjed.getSo2())/2);
      }


    } else {
      logger.info("Susjed nije dostupan");
      kalibracija = trenutniSenzor.getReadingOfSensor();
    }

    channel.shutdown();

    return kalibracija;



  }

  private static void SendCalibratedReading(Sensor sender, Ocitanje kalibracija) {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8090")  // Postavite URL svog servera
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ReadingService readingService = retrofit.create(ReadingService.class);

    Call<Void> call = readingService.saveReading(sender.getSensorId(), kalibracija);  // Postavite odgovarajući sensorId


    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          // Uspješno registriran senzor

          logger.info("Kalibrirano očitanje uspješno spremljeno na poslužitelj. Poruka: " + response.code());

          logger.info("-----");
        } else {
          // Neuspješan odgovor od poslužitelja
          logger.severe("Očitanje nije spremljeno, dogodila se greška. Kod odgovora: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        // Greška prilikom izvođenja zahtjeva
        logger.severe("Greška prilikom slanja zahtjeva za spremanje očitanja: " + t.getMessage());
      }
    });


  }




  public void SeeListOfAllRegisteredSensors() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8090")  // Postavite URL svog servera
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RegistrationService registrationService = retrofit.create(RegistrationService.class);

    Call<List<Registration>> call = registrationService.getSensors();



    call.enqueue(new Callback<List<Registration>>() {
      @Override
      public void onResponse(Call<List<Registration>> call, Response<List<Registration>> response) {
        if (response.isSuccessful()) {
          // Uspješno registriran senzor
          List<Registration> listOfSensors = response.body();

          logger.info("Lista Senzora učitana sa poslužitelja. Poruka: " + response.code());
          logger.info("Lista Svih registriranih senzora :");
          for(Registration reg : listOfSensors) {
            logger.info(reg.toString());
          }
          logger.info("-------");
          //logger.info()

        } else {
          // Neuspješan odgovor od poslužitelja
          logger.severe("Dogodila se greška. Kod odgovora: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Registration>> call, Throwable t) {
        // Greška prilikom izvođenja zahtjeva
        logger.severe("Greška prilikom slanja zahtjeva za listu senzora: " + t.getMessage());
      }
    });


  }

  public void SeeListOfAllReadingsForSensor(Sensor sensor) {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8090")  // Postavite URL svog servera
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ReadingService registrationService = retrofit.create(ReadingService.class);

    Call<List<Ocitanje>> call = registrationService.getAllReadings(sensor.getSensorId());


    call.enqueue(new Callback<List<Ocitanje>>() {
      @Override
      public void onResponse(Call<List<Ocitanje>> call, Response<List<Ocitanje>> response) {
        if (response.isSuccessful()) {
          // Uspješno registriran senzor
          List<Ocitanje> listOfReadings = response.body();

          logger.info("Lista očitanja učitana sa poslužitelja. Poruka: " + response.code());
          logger.info("Lista svih očitanja :");
          for (Ocitanje read : listOfReadings) {
            logger.info(read.toString());
          }
          logger.info("----");
          //logger.info()

        } else {
          // Neuspješan odgovor od poslužitelja
          logger.severe("Dogodila se greška. Kod odgovora: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Ocitanje>> call, Throwable t) {
        // Greška prilikom izvođenja zahtjeva
        logger.severe("Greška prilikom slanja zahtjeva za listu očitanja: " + t.getMessage());
      }
    });

  }

  public static List<Ocitanje> loadReadings() {

    List<Ocitanje> readings = new ArrayList<>();
    String file = "readings.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {


      String line;
      boolean firstLine = true;
      while ((line = br.readLine()) != null) {

        if (firstLine) {

          firstLine = false;
          continue;
        }

        String[] parts = line.split(",");

          Ocitanje reading = new Ocitanje();

          reading.setTemperature(Double.parseDouble(parts[0]));
          reading.setPressure(Double.parseDouble(parts[1]));
          reading.setHumidity(Double.parseDouble(parts[2]));
          reading.setCoConcentration(Double.parseDouble(parts[3]));
          if(parts[4] != null) {
            reading.setNo2Concentration(parseDoubleWithDefault(parts[4], 0.0));
          } else {
            reading.setSo2Concentration(parseDoubleWithDefault(parts[5], 0.0));
          }

          readings.add(reading);

      }

      } catch(IOException e){
        e.printStackTrace();

      }

    //System.out.println(readings);
      return readings;

    }


  private static double parseDoubleWithDefault(String value, double defaultValue) {
    if (value.isEmpty() || value.equals(",")) {
      return defaultValue;
    }
    return Double.parseDouble(value);
  }
}


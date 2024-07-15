package hr.fer.tel.rassus.examples;

import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * The type Uppercase service.
 */
public class UppercaseService extends UppercaseGrpc.UppercaseImplBase {
  private static final Logger logger = Logger.getLogger(UppercaseService.class.getName());


  @Override
  public void requestUppercase(
      Message request, StreamObserver<Message> responseObserver
  ) {
    //logger.info("Got a new message: " + request.getPayload());


    // Create response
   // Message response = Message.newBuilder().setPayload(request.getPayload().toUpperCase()).build();
    // Send response
   /* responseObserver.onNext(
        response
    );

    logger.info("Responding with: " + response.getPayload());
    // Send a notification of successful stream completion.
    responseObserver.onCompleted();*/
  }





  @Override
  public void sendReading(Reading reading, StreamObserver<Reading> messageStreamObserver) {
    long startTimeMillis = System.currentTimeMillis();




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

        Ocitanje ocitanje = new Ocitanje();

        ocitanje.setTemperature(Double.parseDouble(parts[0]));
        ocitanje.setPressure(Double.parseDouble(parts[1]));
        ocitanje.setHumidity(Double.parseDouble(parts[2]));
        ocitanje.setCoConcentration(Double.parseDouble(parts[3]));
        if(parts[4] != null) {
          ocitanje.setNo2Concentration(parseDoubleWithDefault(parts[4], 0.0));
        } else {
          ocitanje.setSo2Concentration(parseDoubleWithDefault(parts[5], 0.0));
        }

        readings.add(ocitanje);

      }

    } catch(IOException e){
      e.printStackTrace();

    }


    long currentTimeMillis1 = System.currentTimeMillis();



    int randomNumber = (int) (Math.random()*1000+ 1);;

    int line = (int) ((randomNumber % 100) +1);
    System.out.println(line);
    Ocitanje reading1 = readings.get(line);





    Reading myReading = Reading.newBuilder().setTemperature(reading1.getTemperature())
            .setHumidity(reading1.getHumidity()).setCo(reading1.getCoConcentration()).setPressure(reading1.getPressure())
            .setNo2(reading1.getNo2Concentration()).setSo2(reading1.getSo2Concentration()).build();

    messageStreamObserver.onNext(myReading);
    messageStreamObserver.onCompleted();



  }

  private static double parseDoubleWithDefault(String value, double defaultValue) {
    if (value.isEmpty() || value.equals(",")) {
      return defaultValue;
    }
    return Double.parseDouble(value);
  }




}

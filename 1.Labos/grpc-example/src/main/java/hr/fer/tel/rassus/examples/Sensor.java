package hr.fer.tel.rassus.examples;

public class Sensor {

    private String sensorId;

    private Registration registrationData;

    private Ocitanje readingOfSensor;

    public Sensor(String sensorId, Registration registrationData) {
        this.sensorId = sensorId;
        this.registrationData = registrationData;
    }

    public Sensor(String sensorId, Registration registrationData, Ocitanje readingOfSensor) {
        this.sensorId = sensorId;
        this.registrationData = registrationData;
        this.readingOfSensor = readingOfSensor;
    }

    public Registration getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(Registration registrationData) {
        this.registrationData = registrationData;
    }

    public Ocitanje getReadingOfSensor() {
        return readingOfSensor;
    }

    public void setReadingOfSensor(Ocitanje readingOfSensor) {
        this.readingOfSensor = readingOfSensor;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}

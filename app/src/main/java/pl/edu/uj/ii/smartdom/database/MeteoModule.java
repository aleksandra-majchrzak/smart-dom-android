package pl.edu.uj.ii.smartdom.database;

/**
 * Created by Mohru on 22.07.2017.
 */

public class MeteoModule extends Module {
    public double temperature;
    public double humidity;
    public double co;
    public double co2;
    public double gas;

    public MeteoModule(String serverId, String name, String roomServerId) {
        super(serverId, name, roomServerId);
    }

    public MeteoModule(String serverId, String name, String roomServerId, double temperature, double humidity, double co, double co2, double gas) {
        super(serverId, name, roomServerId);
        this.temperature = temperature;
        this.humidity = humidity;
        this.co = co;
        this.co2 = co2;
        this.gas = gas;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCo() {
        return co;
    }

    public double getCo2() {
        return co2;
    }

    public double getGas() {
        return gas;
    }
}

package pl.edu.uj.ii.smartdom.database;

import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 22.07.2017.
 */

public class MeteoModule extends Module {
    public double temperature;
    public double humidity;
    public double co;
    public double co2;
    public double gas;

    public MeteoModule() {
    }

    public MeteoModule(String serverId, String name, Room room) {
        super(serverId, name, room);
    }

    public MeteoModule(String serverId, String name, Room room, double temperature, double humidity, double co, double co2, double gas) {
        super(serverId, name, room);
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

    @Override
    public ModuleType getType() {
        return ModuleType.METEO_MODULE;
    }
}

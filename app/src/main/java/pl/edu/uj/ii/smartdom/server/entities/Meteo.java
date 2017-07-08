package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 08.07.2017.
 */

public class Meteo {

    public double temperature;
    public double humidity;
    public double co;
    public double co2;
    public double gas;

    public Meteo() {
    }

    public Meteo(double temperature, double humidity, double co, double co2, double gas) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co = co;
        this.co2 = co2;
        this.gas = gas;
    }


}

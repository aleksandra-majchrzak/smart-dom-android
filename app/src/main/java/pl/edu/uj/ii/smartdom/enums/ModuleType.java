package pl.edu.uj.ii.smartdom.enums;

/**
 * Created by Mohru on 22.07.2017.
 */

public enum ModuleType {
    LIGHT_MODULE("LIGHT_MODULE"), METEO_MODULE("METEO_MODULE"), BLIND_MOTOR_MODULE("BLIND_MOTOR_MODULE");

    private String type;

    private ModuleType(String type) {
        this.type = type;
    }

    public final String getValue() {
        return type;
    }
}

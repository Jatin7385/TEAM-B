package sample;

import javafx.beans.property.SimpleStringProperty;

public class SPDATAMODEL {
    String SP_ALTITUDE;
    String TEMPERATURE;
    String AIR_SPEED;

    public SPDATAMODEL(String SP_ALTITUDE, String TEMPERATURE, String AIR_SPEED) {
        this.SP_ALTITUDE = SP_ALTITUDE;
        this.TEMPERATURE = TEMPERATURE;
        this.AIR_SPEED = AIR_SPEED;
    }

    public String getSP_ALTITUDE() {
        return SP_ALTITUDE;
    }

    public void setSP_ALTITUDE(String ALTITUDE) {
        this.SP_ALTITUDE = ALTITUDE;
    }

    public String getTEMPERATURE() {
        return TEMPERATURE;
    }

    public void setTEMPERATURE(String TEMPERATURE) {
        this.TEMPERATURE = TEMPERATURE;
    }

    public String getAIR_SPEED() {
        return AIR_SPEED;
    }

    public void setAIR_SPEED(String AIR_SPEED) {
        this.AIR_SPEED = AIR_SPEED;
    }
}

package sample;

import javafx.beans.property.SimpleStringProperty;

public class CarrierDataModel {
    String ALTITUDE;
    String  LATITUDE;
    String LONGITUDE;
    String PITCH;
    String ROLL;
    String YAW;

    public CarrierDataModel(String ALTITUDE, String LATITUDE, String LONGITUDE, String PITCH, String ROLL, String YAW) {
        this.ALTITUDE = ALTITUDE;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.PITCH = PITCH;
        this.ROLL = ROLL;
        this.YAW = YAW;
    }
    public String getALTITUDE() {
        return ALTITUDE;
    }

    public void setALTITUDE(String ALTITUDE) {
        this.ALTITUDE = ALTITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getPITCH() {
        return PITCH;
    }

    public void setPITCH(String PITCH) {
        this.PITCH = PITCH;
    }

    public String getROLL() {
        return ROLL;
    }

    public void setROLL(String ROLL) {
        this.ROLL = ROLL;
    }

    public String getYAW() {
        return YAW;
    }

    public void setYAW(String YAW) {
        this.YAW = YAW;
    }
}

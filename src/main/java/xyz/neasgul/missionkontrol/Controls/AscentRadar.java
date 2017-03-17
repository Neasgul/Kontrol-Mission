package xyz.neasgul.missionkontrol.Controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class AscentRadar extends Control  {
    private DoubleProperty altitude;
    private DoubleProperty startAltitude;
    private DoubleProperty endAltitude;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AscentRadarSkin(this);
    }

    private void init() {
    }

    public double getAltitude() {
        return altitude.get();
    }

    public DoubleProperty altitudeProperty() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude.set(altitude);
    }

    public double getStartAltitude() {
        return startAltitude.get();
    }

    public DoubleProperty startAltitudeProperty() {
        return startAltitude;
    }

    public void setStartAltitude(double startAltitude) {
        if(startAltitude<0){
            this.startAltitude.set(0);
        }else {
            this.startAltitude.set(startAltitude);
        }

    }

    public double getEndAltitude() {
        return endAltitude.get();
    }

    public DoubleProperty endAltitudeProperty() {
        return endAltitude;
    }

    public void setEndAltitude(double endAltitude) {
        if(endAltitude<=getStartAltitude()){
            this.endAltitude.set(getStartAltitude()+10000);
        }else {
            this.endAltitude.set(endAltitude);
        }

    }
}

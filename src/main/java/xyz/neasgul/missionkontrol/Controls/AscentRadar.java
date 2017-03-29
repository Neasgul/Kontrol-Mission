package xyz.neasgul.missionkontrol.Controls;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.stream.Stream;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class AscentRadar extends Control  {
    private Double currentAltitude;
    private Double startAltitude;
    private Double endAltitude;
    private Stream<Double> surfaceAltitudeStream;
    /*
    Round down altitude
    b = a - a % 1000;
    */

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AscentRadarSkin(this);
    }

    public AscentRadar(Double startAltitude, Double endAltitude, Stream<Double> surfaceAltitudeStream) {
        this.surfaceAltitudeStream = surfaceAltitudeStream;

        if(startAltitude<0){
            this.startAltitude = Double.valueOf(0);
        }
        else {
            this.startAltitude = startAltitude - startAltitude % 1000;
        }
        if(endAltitude <= startAltitude){
            this.endAltitude = startAltitude + 10000;
        }
        else {
            this.endAltitude = endAltitude - endAltitude % 1000;
        }
    }

    public Double getCurrentAltitude() {
        return currentAltitude;
    }

    public Double getStartAltitude() {
        return startAltitude;
    }

    public Double getEndAltitude() {
        return endAltitude;
    }
}


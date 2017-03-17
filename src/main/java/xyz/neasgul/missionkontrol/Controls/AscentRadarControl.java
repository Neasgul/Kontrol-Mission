package xyz.neasgul.missionkontrol.Controls;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class AscentRadarControl extends Control {
    @Override
    protected Skin<?> createDefaultSkin() {
        return new AscentRadarSkin(this);
    }
}

package xyz.neasgul.missionkontrol.Controls;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.shape.Line;

import java.util.Random;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class AscentRadarSkin extends SkinBase<AscentRadar> implements Skin<AscentRadar> {
    private static int LINES_COUNT;
    private static final double LEFT_MARGIN = 20;
    private static final double RIGHT_MARGIN = 20;
    private static final double MARGINS = LEFT_MARGIN + RIGHT_MARGIN;
    private static final double MIN_LINE_WIDTH = 1.0;
    private double MIN_MAIN_WIDTH;
    private Line[] lines;

    AscentRadar control;


    public AscentRadarSkin(AscentRadar control) {
        super(control);
        initLinesAndValues(control);
        initGraphics(control);
        this.control = control;
        MIN_MAIN_WIDTH = MIN_LINE_WIDTH * LINES_COUNT;
    }

    private void initLinesAndValues(AscentRadar control) {
        generateLineCount(control);
        lines = new Line[LINES_COUNT];
        for (int i = 0; i < LINES_COUNT; ++i) {
            lines[i] = new Line();
        }
    }

    private void initGraphics(AscentRadar control) {
        getChildren().addAll(lines);
    }

    private void generateLineCount(AscentRadar control) {
        int line = 0;
        double altitude = control.getStartAltitude();
        while(true){
            if(altitude>control.getEndAltitude()){
                break;
            }
            line++;
            altitude+=1000;
        }
        LINES_COUNT = line;
    }

    @Override
    protected double computeMinWidth(double height,
                                     double topInset, double rightInset,
                                     double bottomInset, double leftInset) {
        return leftInset + MIN_MAIN_WIDTH + MARGINS + rightInset;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY,
                                  double contentWidth, double contentHeight) {

        final double mainWidth = contentHeight - MARGINS,
                lineWidth = mainWidth / LINES_COUNT;
        for (int i = 0; i < LINES_COUNT; ++i) {
            final Line line = lines[i];
            final double lineY = contentY - i * lineWidth;

            if(i%5==0){
                line.setStartX(0 + LEFT_MARGIN);
                line.setStartY(contentHeight);
                line.setEndX(contentWidth-RIGHT_MARGIN);
                line.setEndY(contentHeight - 0 * contentHeight);
                line.setStrokeWidth(3.0);
                layoutInArea(line, contentX, lineY-5, contentWidth, contentHeight, -2, HPos.CENTER, VPos.BOTTOM);
            }else {
                line.setStartX(5 + LEFT_MARGIN);
                line.setStartY(contentHeight);
                line.setEndX(contentWidth - 5 - RIGHT_MARGIN);
                line.setEndY(contentHeight - 0 * contentHeight);
                line.setStrokeWidth(2.0);
                layoutInArea(line, contentX, lineY-5, contentWidth, contentHeight, -2, HPos.CENTER, VPos.BOTTOM);
            }





        }
    }

    protected void redraw() {}


}

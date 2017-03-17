package xyz.neasgul.missionkontrol.Controls;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class AscentRadarSkin extends SkinBase<AscentRadarControl> {
    private static final int LINES_COUNT =75+1;
    private static final double LEFT_MARGIN = 2.0;
    private static final double RIGHT_MARGIN = 1.0;
    private static final double MARGINS = LEFT_MARGIN + RIGHT_MARGIN;
    private static final double MIN_LINE_WIDTH = 1.0;
    private static final double MIN_MAIN_WIDTH = MIN_LINE_WIDTH * LINES_COUNT;
    private final Line[] lines = new Line[LINES_COUNT];
    //private final double[] values = new double[LINES_COUNT];


    public AscentRadarSkin(AscentRadarControl control) {
        super(control);
        initLinesAndValues();
        initGraphics(control);
    }

    private void initLinesAndValues() {
        final Random random = new Random();
        for (int i = 0; i < LINES_COUNT; ++i) {
            lines[i] = new Line();
            //values[i] = random.nextDouble();
        }
    }

    private void initGraphics(AscentRadarControl control) {
        getChildren().addAll(lines);
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
        final double mainY = contentX - LEFT_MARGIN;
        for (int i = 0; i < LINES_COUNT; ++i) {
            final Line line = lines[i];
            //final double value = values[i];
            line.setStartX(0);
            line.setStartY(contentHeight);
            line.setEndX(contentWidth);
            line.setEndY(contentHeight - 0 * contentHeight);
            final double lineY = contentY - i * lineWidth;
            if(i%5==0 && i!=0){
                line.setStrokeWidth(3.0);
                //line.setStroke(Color.DARKSEAGREEN);
                layoutInArea(line, contentX, lineY, contentWidth, contentHeight, -1, HPos.CENTER, VPos.BOTTOM);
            }else {
                line.setStrokeWidth(2.0);
                layoutInArea(line, contentX, lineY, contentWidth, contentHeight, -1, HPos.CENTER, VPos.BOTTOM);
            }





        }
    }

}

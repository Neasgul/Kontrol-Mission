package xyz.neasgul.missionkontrol.Utils;

import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static krpc.client.services.SpaceCenter.*;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class VesselUtils {
    public static double calculateTWR(Vessel vessel, CelestialBody body) throws IOException, RPCException {
        float Ft = vessel.getMaxThrust();
        float m = vessel.getMass();
        float g = body.getSurfaceGravity();

        return Ft/(m*g);
    }

    static float getFirstStageThrust(Vessel vessel) throws IOException, RPCException {
        float Ft = 0;
        int maxStage = -1;
        List<Part> partList;
        List<Engine> firstStageEngine = new ArrayList<>();
        partList = vessel.getParts().getAll();
        // get the max stage
        for (Part part : partList) {
            if(part.getStage()>=maxStage) {
                maxStage = part.getStage();
            }

        }
        List<Part>stage =  vessel.getParts().inStage(maxStage);
        //check if the max stage has engine
        boolean hasEngine = false;
        for (Part part : stage) {
            if (part.getEngine()!=null) {
                hasEngine = true;
                break;
            }
        }
        if(!hasEngine) {
            maxStage--;
        }

        //addition of all first stage engine thrust
        List<Part>inStageList =  vessel.getParts().inStage(maxStage);
        for (Part part : inStageList) {
            Engine engine = part.getEngine();
            if(engine!=null) {
                Ft+=engine.getMaxThrust();
            }
        }
        return Ft;
    }
}

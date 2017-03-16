package xyz.neasgul.missionkontrol.controller;

import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;
import xyz.neasgul.missionkontrol.ConnectionManager;
import xyz.neasgul.missionkontrol.GravityTurnSettings;
import xyz.neasgul.missionkontrol.Utils.Utils;
import xyz.neasgul.missionkontrol.Utils.VesselUtils;

import java.io.IOException;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class GravityTurn_Controller {

    ConnectionManager mConnectionManager;
    static GravityTurnSettings generateGravityTurnSettings(SpaceCenter.Vessel vessel, SpaceCenter.CelestialBody body) throws IOException, RPCException {
        double TWR = VesselUtils.calculateTWR(vessel,body);
        double geeASL = body.getSurfaceGravity()/9.81;
        float baseFactor = Math.round(geeASL*100)/10;

        float turnAngle = Utils.clamp((float)(10 + TWR * 5),10,80);
        float turnSpeed = Utils.clamp((float)(baseFactor*10-TWR*baseFactor*3),baseFactor,baseFactor*10);

        return new GravityTurnSettings(turnAngle,turnSpeed);
    }

}

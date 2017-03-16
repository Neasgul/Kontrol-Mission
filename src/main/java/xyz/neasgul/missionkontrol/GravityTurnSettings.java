package xyz.neasgul.missionkontrol;

/**
 * Created by benoitchopinet on 16/03/2017.
 */
public class GravityTurnSettings {
    private float turnAngle;
    private float turnSpeed;

    public GravityTurnSettings(float turnAngle, float turnSpeed) {
        this.turnAngle = turnAngle;
        this.turnSpeed = turnSpeed;
    }

    public float getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(float turnAngle) {
        this.turnAngle = turnAngle;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }


    @Override
    public String toString() {
        return turnAngle+"°@"+turnSpeed+"m/s";
    }
}

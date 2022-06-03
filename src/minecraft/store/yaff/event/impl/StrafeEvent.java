package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;

public class StrafeEvent extends AbstractEvent {
    protected float yaw, pitch, strafe, forward, abrasion;
    protected double motionX, motionY, motionZ;

    public StrafeEvent(float yaw, float pitch, float strafe, float forward, float abrasion, double motionX, double motionY, double motionZ) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.strafe = strafe;
        this.forward = forward;
        this.abrasion = abrasion;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getStrafe() {
        return strafe;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public float getForward() {
        return forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getAbrasion() {
        return abrasion;
    }

    public void setAbrasion(float abrasion) {
        this.abrasion = abrasion;
    }

    public double getMotionX() {
        return motionX;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

}

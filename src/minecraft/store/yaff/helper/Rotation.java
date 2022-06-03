package store.yaff.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import store.yaff.event.impl.MotionEvent;

public class Rotation {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isInFov(Entity entity, float FOV) {
        return getAngleDifference(mc.player.rotationYaw, (float) (java.lang.Math.toDegrees(java.lang.Math.atan2(entity.posZ - mc.player.posZ, entity.posX - mc.player.posX)) - 90.0D)) <= FOV;
    }

    public static float getAngleDifference(float a, float b) {
        float f = java.lang.Math.abs(a - b) % 360F;
        return f > 180F ? 360F - f : f;
    }

    public static float positionToDegrees(double posX, double posZ) {
        return (float) (java.lang.Math.atan2(posZ - mc.player.posZ, posX - mc.player.posX) * 180f / java.lang.Math.PI) - 90f;
    }

    public static float[] getVec3dRotations(Vec3d vec3d) {
        double diffX = vec3d.xCoord - mc.player.posX;
        double diffZ = vec3d.zCoord - mc.player.posZ;
        double diffY = vec3d.yCoord - (mc.player.posY + mc.player.getEyeHeight());
        double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (java.lang.Math.atan2(diffZ, diffX) * 180.0D / java.lang.Math.PI) - 90.0F;
        float pitch = (float) (-(java.lang.Math.atan2(diffY, diffXZ) * 180.0D / java.lang.Math.PI));
        yaw -= yaw % GCD.getGCD();
        pitch -= pitch % GCD.getGCD();
        return new float[]{ yaw, MathHelper.clamp(pitch, -90.0F, 90.0F) };
    }

    public static float[] getRotations(Entity entityIn, float shakeDiff, boolean toggleShake, boolean smartShake, float rotationPredict) {
        double diffX = (entityIn.posX + (entityIn.posX - entityIn.prevPosX) * rotationPredict) - (mc.player.posX + (mc.player.posX - mc.player.prevPosX) * rotationPredict);
        double diffZ = (entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) * rotationPredict) - (mc.player.posZ + (mc.player.posZ - mc.player.prevPosZ) * rotationPredict);
        double diffY = (entityIn.posY + entityIn.getEyeHeight()) - (mc.player.posY + mc.player.getEyeHeight()) - 0.3;
        double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        double randomYaw = toggleShake ? Random.getRandomFloat(0 - shakeDiff, 0 + shakeDiff) : 0 + (smartShake ? (mc.player.motionX + mc.player.motionZ) * 25f : 0);
        double randomPitch = toggleShake ? Random.getRandomFloat(0 - shakeDiff, 0 + shakeDiff) : 0 + (smartShake ? mc.player.motionY * 25f : 0);
        float yaw = (float) ((java.lang.Math.atan2(diffZ, diffX) * 180 / java.lang.Math.PI) - 90.0F) + (float) randomYaw;
        float pitch = (float) -(java.lang.Math.atan2(diffY, diffXZ) * 180 / java.lang.Math.PI) + (float) randomPitch;
        yaw -= yaw % GCD.getGCD();
        pitch -= pitch % GCD.getGCD();
        return new float[]{ yaw, MathHelper.clamp(pitch, -90.0F, 90.0F) };
    }

    public static void setClientRotations(float[] currentRotations) {
        mc.player.rotationYaw = currentRotations[0];
        mc.player.rotationYawHead = currentRotations[0];
        mc.player.renderYawOffset = currentRotations[0];
        mc.player.rotationPitch = currentRotations[1];
        mc.player.rotationPitchHead = currentRotations[1];
        mc.player.renderPitchOffset = currentRotations[1];
    }

    public static void setPacketRotations(MotionEvent event, float[] currentRotations) {
        event.setYaw(currentRotations[0]);
        mc.player.rotationYawHead = currentRotations[0];
        mc.player.renderYawOffset = currentRotations[0];
        event.setPitch(currentRotations[1]);
        mc.player.rotationPitchHead = currentRotations[1];
        mc.player.renderPitchOffset = currentRotations[1];
    }

}

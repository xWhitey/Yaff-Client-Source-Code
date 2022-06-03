package store.yaff.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import store.yaff.event.impl.MotionEvent;

import java.util.Objects;

public class Movement {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static int getJumpBoostModifier() {
        PotionEffect potionEffect = mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
        return potionEffect != null ? potionEffect.getAmplifier() + 1 : 0;
    }

    public static int getSpeedModifier() {
        PotionEffect potionEffect = mc.player.getActivePotionEffect(MobEffects.SPEED);
        return potionEffect != null ? potionEffect.getAmplifier() + 1 : 0;
    }

    public static float getSpeed(double motionX, double motionZ) {
        return (float) java.lang.Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static float getEventSpeed(MotionEvent event) {
        return (float) java.lang.Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
    }

    public static double getBaseMovementSpeed() {
        if (mc.player.isSprinting()) {
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                if (Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1 == 1) {
                    return 0.18386012061481244;
                } else {
                    return 0.21450346015841276;
                }
            } else {
                return 0.15321676228437875;
            }
        } else {
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                if (Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1 == 1) {
                    return 0.14143085686761;
                } else {
                    return 0.16500264553372018;
                }
            } else {
                return 0.11785905094607611;
            }
        }
    }

    public static float getBPS() {
        double bps = (java.lang.Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ) * mc.timer.field_194150_d) * 20;
        return java.lang.Math.round(bps * 100) / 100f;
    }

    public static void setSpeed(float speed) {
        float yaw = mc.player.rotationYaw;
        float forward = mc.player.movementInput.field_192832_b;
        float strafe = mc.player.movementInput.moveStrafe;
        if (forward != 0) {
            if (strafe > 0) {
                yaw += (forward > 0 ? -45 : 45);
            } else if (strafe < 0) {
                yaw += (forward > 0 ? 45 : -45);
            }
            strafe = 0;
            if (forward > 0) {
                forward = 1;
            } else if (forward < 0) {
                forward = -1;
            }
        }
        mc.player.motionX = (forward * speed * java.lang.Math.cos(java.lang.Math.toRadians(yaw + 90)) + strafe * speed * java.lang.Math.sin(java.lang.Math.toRadians(yaw + 90)));
        mc.player.motionZ = (forward * speed * java.lang.Math.sin(java.lang.Math.toRadians(yaw + 90)) - strafe * speed * java.lang.Math.cos(java.lang.Math.toRadians(yaw + 90)));
    }

    public static void setStrafe(double strafeSpeed) {
        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            strafeSpeed *= -1;
        }
        double yaw = getDirection();
        mc.player.motionX = -MathHelper.sin((float) yaw) * strafeSpeed;
        mc.player.motionZ = MathHelper.cos((float) yaw) * strafeSpeed;
    }

    public static float getDirection() {
        float yaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0) {
            yaw += 180f;
        }
        float forward = 1f;
        if (mc.player.moveForward < 0) {
            forward = -0.5f;
        } else if (mc.player.moveForward > 0) {
            forward = 0.5f;
        }
        if (mc.player.moveStrafing > 0) {
            yaw -= 90f * forward;
        }
        if (mc.player.moveStrafing < 0) {
            yaw += 90f * forward;
        }
        yaw *= 0.017453292f;
        return yaw;
    }

    public static boolean hasMotion(boolean includeY) {
        return (mc.player.motionX != 0 && (!includeY || mc.player.motionY != 0) && mc.player.motionZ != 0);
    }

    public static boolean shouldSprint() {
        final boolean hasFood = mc.player.getFoodStats().getFoodLevel() > 6;
        final boolean isSneaking = !mc.player.isSneaking();
        return hasFood && isSneaking;
    }

    public static boolean shouldStep() {
        final boolean isCollided = mc.player.isCollidedHorizontally;
        final boolean onGround = mc.player.onGround;
        final boolean onLadder = !mc.player.isOnLadder();
        final boolean isInWater = !mc.player.isInWater();
        return isCollided && onLadder && isInWater && onGround;
    }

    public static boolean shouldClimb() {
        final boolean isCollided = mc.player.isCollidedHorizontally;
        final boolean onLadder = !mc.player.isOnLadder();
        final boolean isInWater = !mc.player.isInWater();
        return isCollided && onLadder && isInWater;
    }

}

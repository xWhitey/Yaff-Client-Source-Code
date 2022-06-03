package store.yaff.feature.impl.movement;

import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Movement;
import store.yaff.setting.impl.ListSetting;

import java.util.Objects;

public class Speed extends AbstractFeature {
    public static final ListSetting speedMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Matrix", "NCP", "NCP Y-Port", "Test");

    protected int speedTicks = 0;

    public Speed(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(speedMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            switch (speedMode.getListValue().toLowerCase()) {
                case "matrix" -> {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.4, mc.player.posZ)).getBlock() == Blocks.AIR) {
                        mc.player.setSprinting(true);
                    } else if (mc.player.fallDistance >= 0.8) {
                        //mc.player.addVelocity(-Math.sin(Movement.getDirection()) * 2 / 24.5f, 0, Math.cos(Movement.getDirection()) * 2 / 24.5f);
                        mc.player.motionX *= 2f;
                        mc.player.motionZ *= 2f;
                    }
                }
                case "ncp" -> {
                    if (Movement.hasMotion(false)) {
                        if (mc.player.onGround) {
                            mc.player.jump();
                            Movement.setSpeed((float) (Movement.getSpeed(mc.player.motionX, mc.player.motionZ) * 1 + Math.random() / 100));
                        } else {
                            Movement.setSpeed((float) (Movement.getSpeed(mc.player.motionX, mc.player.motionZ) * 1.0035));
                        }
                        mc.player.jumpMovementFactor = 0.0225F;
                    }
                    if (speedTicks < 20) {
                        mc.timer.field_194150_d = 0.98f;
                    }
                    if (speedTicks == 20)
                        speedTicks = 0;
                    if (speedTicks < 9) {
                        mc.timer.field_194150_d = (float) (1.06f + (Math.random() / 100));
                    }
                }
                case "ncp y-port" -> {
                    if (Movement.hasMotion(true)) {
                        mc.timer.field_194150_d = 1.05F;
                        if (mc.player.onGround) {
                            mc.player.motionY = 0.4000000059604645D;
                            if (!mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(1)))) {
                                Movement.setStrafe(0.34F);
                            } else {
                                if (Objects.requireNonNull(mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(1)))).getAmplifier() == 0) {
                                    Movement.setStrafe(0.49F);
                                }
                                if (Objects.requireNonNull(mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(1)))).getAmplifier() == 1) {
                                    Movement.setStrafe(0.575F);
                                }
                                if (Objects.requireNonNull(mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(1)))).getAmplifier() == 2) {
                                    Movement.setStrafe(0.73F);
                                }
                            }
                        } else {
                            mc.player.motionY = -100.0D;
                        }
                    }
                }
                case "test" -> {
                    if (!mc.player.onGround) {
                        if (mc.player.motionY > 0) {
                            mc.player.motionY -= 0.0005;
                        }
                        mc.player.motionY -= 0.0094001145141919810;
                    }
                    if (!mc.player.onGround) {
                        mc.gameSettings.keyBindJump.pressed = mc.gameSettings.keyBindJump.isKeyDown();
                        if (Movement.getSpeed(mc.player.motionX, mc.player.motionZ) < 0.2177) {
                            Movement.setStrafe(0.2177f);
                        }
                    }
                    if (Math.abs(mc.player.movementInput.moveStrafe) < 0.1) {
                        mc.player.jumpMovementFactor = 0.026f;
                    } else {
                        mc.player.jumpMovementFactor = 0.0247f;
                    }
                    if (mc.player.onGround && Movement.hasMotion(false)) {
                        mc.gameSettings.keyBindJump.pressed = false;
                        mc.player.jump();
                        mc.player.motionY = 0.41050001145141919810;
                        if (Math.abs(mc.player.movementInput.moveStrafe) < 0.1) {
                            Movement.setStrafe(Movement.getSpeed(mc.player.motionX, mc.player.motionZ));
                        }
                    }
                    if (!Movement.hasMotion(false)) {
                        mc.player.motionX = 0.0;
                        mc.player.motionZ = 0.0;
                    }
                }
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.timer.field_194150_d = 1f;
            speedTicks = 0;
        }
    }

}

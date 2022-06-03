package store.yaff.feature.impl.movement;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.MathHelper;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Movement;
import store.yaff.helper.Time;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Flight extends AbstractFeature {
    public static ListSetting flyMode = new ListSetting("Fly Mode", "None", "Matrix", () -> true, "Matrix Pearl", "Elytra Fly", "Matrix RTP");
    public static NumericSetting flyMotion = new NumericSetting("Motion-Y", "None", 0.2F, 0.01F, 0.5F, 0.01F, () -> flyMode.getListValue().equalsIgnoreCase("Elytra Fly"));
    protected final Time timeManager = new Time();
    private final int i = 0;
    private double x, y, z;
    private boolean hasClipped, doFly;
    private float stage;

    public Flight(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(flyMode, flyMotion);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            switch (flyMode.getListValue().toLowerCase()) {
                case "matrix pearl" -> {
                    if (mc.player.hurtTime > 0 && mc.player.hurtTime <= 9)
                        if (mc.player.onGround) {
                            mc.player.jump();
                        } else if (!mc.player.onGround && timeManager.hasReached(280)) {
                            mc.player.motionY = 0.4f;
                            double f = Math.toRadians(mc.player.rotationYaw);
                            if (mc.player.ticksExisted % 2 == 0) {
                                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                            }
                            mc.player.motionX -= MathHelper.sin((float) f) * 0.998f;
                            mc.player.motionZ += MathHelper.cos((float) f) * 0.998f; // 0.27
                        }
                }
                case "matrix rtp" -> {
                    if (mc.player.onGround) {
                        mc.player.jump();
                        timeManager.reset();
                    } else {
                        if (!mc.player.onGround && timeManager.hasReached(280.0F)) {
                            mc.player.motionY = -0.01D;
                            mc.player.capabilities.isFlying = true;
                            mc.player.capabilities.setFlySpeed(3 / 10.0F);
                        }
                    }
                }
                case "elytra fly" -> {
                    if (mc.player.ticksExisted % 2 == 0) {
                        mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                        mc.player.fallDistance = (float) 1.0E-12D;
                        if (mc.player.ticksExisted % 4 == 0) {
                            mc.player.motionY = flyMotion.getNumericValue();
                        }
                        mc.player.onGround = false;
                        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                            mc.player.motionY -= 0.01;
                        } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            mc.player.motionY += 0.01;
                        }
                        Movement.setSpeed(0.7F);
                        mc.player.capabilities.setFlySpeed(3.0F);
                    }
                }
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.setFlySpeed(0.05F);
            mc.timer.field_194150_d = 1;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }

}

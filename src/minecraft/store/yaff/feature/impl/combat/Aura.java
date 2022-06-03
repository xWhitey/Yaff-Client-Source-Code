package store.yaff.feature.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.misc.Friends;
import store.yaff.helper.*;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Aura extends AbstractFeature {
    public static final BooleanSetting attackPlayers = new BooleanSetting("Attack Players", "None", true, () -> true);
    public static final BooleanSetting attackMobs = new BooleanSetting("Attack Mobs", "None", false, () -> true);
    public static final BooleanSetting attackAnimals = new BooleanSetting("Attack Animals", "None", false, () -> true);
    public static final BooleanSetting attackWaterAnimals = new BooleanSetting("Attack Water Animals", "None", false, () -> true);
    public static final BooleanSetting attackDecorations = new BooleanSetting("Attack Decorations", "None", false, () -> true);
    public static final BooleanSetting attackInvisible = new BooleanSetting("Attack Invisibles", "None", false, () -> true);
    public static final BooleanSetting attackTeams = new BooleanSetting("Attack Teams", "None", false, () -> true);
    public static final BooleanSetting keepSprint = new BooleanSetting("Keep Sprint", "None", true, () -> true);
    public static final BooleanSetting dynamicSprint = new BooleanSetting("Dynamic Sprint", "None", true, () -> true);
    public static final BooleanSetting unsprintOnCrit = new BooleanSetting("Unsprint On Crit", "None", true, () -> true);
    public static final BooleanSetting inventoryCheck = new BooleanSetting("Check Inventory", "None", true, () -> true);
    public static final BooleanSetting rayTrace = new BooleanSetting("RayTrace", "None", true, () -> true);
    public static final BooleanSetting throughWalls = new BooleanSetting("Through Walls", "None", false, () -> true);
    public static final BooleanSetting swingHand = new BooleanSetting("Swing Hand", "None", true, () -> true);
    public static final BooleanSetting tpsSync = new BooleanSetting("TpsSync", "None", true, () -> true);
    public static final BooleanSetting triggerBot = new BooleanSetting("TriggerBot", "None", true, () -> true);
    public static final BooleanSetting onlyCrits = new BooleanSetting("Only Crits", "None", true, () -> true);
    public static final NumericSetting critChance = new NumericSetting("Crit Chance", "None", 50, 1, 100, 1, () -> true);
    public static final BooleanSetting disableOnDeath = new BooleanSetting("Disable on death", "None", true, () -> true);
    public static final NumericSetting attackDistance = new NumericSetting("Attack Distance", "None", 3.4f, 2, 5, 0.1f, () -> true);
    public static final NumericSetting hitChance = new NumericSetting("Hit Chance", "None", 85, 1, 100, 1, () -> true);
    public static final NumericSetting attackCooldown = new NumericSetting("Cooldown", "None", 1, 0, 1, 0.1f, () -> true);
    public static final NumericSetting attackFOV = new NumericSetting("FOV", "None", 360, 0, 360, 1, () -> true);
    public static final NumericSetting hurtTime = new NumericSetting("HurtTime", "None", 10, 0, 10, 1, () -> true);
    public static final BooleanSetting toggleShake = new BooleanSetting("Toggle Shake", "None", true, () -> true);
    public static final BooleanSetting smartShake = new BooleanSetting("Smart Shake", "None", true, () -> true);
    public static final NumericSetting shakeDiff = new NumericSetting("ShakeDiff", "None", 8, 2, 32, 1, () -> true);
    public static final NumericSetting rotationPredict = new NumericSetting("Predict", "None", 0.2f, 0, 4, 0.1f, () -> true);
    public static final NumericSetting rotationDelay = new NumericSetting("Rotation Delay", "None", 42, 1, 80, 2, () -> true);
    public static final ListSetting rotationMode = new ListSetting("Rotations", "None", "Packet", () -> true, "None", "Client", "Packet", "Matrix");
    public static final BooleanSetting rotateOnCrit = new BooleanSetting("Rotate On Crit", "None", false, () -> true);
    public static final BooleanSetting rotateOnCooldown = new BooleanSetting("Rotate On Cooldown", "None", false, () -> true);
    public static final ListSetting strafeMode = new ListSetting("Strafe", "None", "Silent", () -> true, "None", "Silent");
    public static final ListSetting sortMode = new ListSetting("Sort", "None", "Distance", () -> true, "Health", "Armor", "Distance", "HurtTime");
    public static final ListSetting targetStyle = new ListSetting("Switch Style", "None", "Switch", () -> true, "Single", "Switch");
    public static final ListSetting attackStyle = new ListSetting("Attack Style", "None", "Old", () -> true, "Old", "New");
    public static EntityLivingBase targetEntity;
    public static float[] playerRotation;
    public static boolean dynamicSprintState = false;
    private final Time timeManager = new Time();
    private float healthBarWidth;

    public Aura(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(attackPlayers, attackMobs, attackAnimals, attackWaterAnimals, attackDecorations, attackInvisible, attackTeams, keepSprint, dynamicSprint, unsprintOnCrit, inventoryCheck, rayTrace, throughWalls, swingHand, tpsSync, triggerBot, onlyCrits, critChance, disableOnDeath, attackDistance, hitChance, attackCooldown, attackFOV, hurtTime, toggleShake, smartShake, shakeDiff, rotationPredict, rotationDelay, rotationMode, rotateOnCrit, rotateOnCooldown, strafeMode, sortMode, targetStyle, attackStyle);
    }

    public boolean canAttack(Entity entity) {
        if (!(attackPlayers.getBooleanValue() && entity instanceof EntityPlayer) && !(attackMobs.getBooleanValue() && entity instanceof EntityMob) && !(attackAnimals.getBooleanValue() && (entity instanceof EntityAnimal || entity instanceof EntityVillager)) && !(attackWaterAnimals.getBooleanValue() && entity instanceof EntityWaterMob) && !(attackDecorations.getBooleanValue() && (entity instanceof EntityPainting || entity instanceof EntityArmorStand || entity instanceof EntityItemFrame))) {
            return false;
        }
        if (!attackTeams.getBooleanValue() && World.isOnSameTeam(entity)) {
            return false;
        }
        if (!attackInvisible.getBooleanValue() && entity.isInvisible()) {
            return false;
        }
        return (((Yaff.of.friendController.getFriend(entity.getUniqueID()) == null || !Friends.checkUUID.getBooleanValue()) && (Yaff.of.friendController.getFriend(entity.getName()) == null || !Friends.checkName.getBooleanValue())) || !Friends.preventAttack.getBooleanValue()) && !AntiBot.isBot(entity) && entity.isEntityAlive() && entity != mc.player && (!(mc.player.getDistanceToEntity(entity) > attackDistance.getNumericValue())) && (!(mc.player.getYDistanceToEntity(entity) > 2)) && Rotation.isInFov(entity, attackFOV.getNumericValue()) && (throughWalls.getBooleanValue() || ((EntityLivingBase) entity).canEntityBeSeen(mc.player));
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            //playerRotation = new float[]{ mc.player.rotationYaw, mc.player.rotationPitch };
            ArrayList<EntityLivingBase> entityList = new ArrayList<>();
            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (!canAttack(entity)) {
                    continue;
                }
                if (targetEntity != null && targetStyle.getListValue().equalsIgnoreCase("Single") && canAttack(targetEntity)) {
                    continue;
                }
                if (canAttack(entity)) {
                    entityList.add((EntityLivingBase) entity);
                }
            }
            switch (sortMode.getListValue().toLowerCase()) {
                case "health" -> entityList.sort(Comparator.comparing(EntityLivingBase::getHealth));
                case "armor" -> entityList.sort(Comparator.comparing(EntityLivingBase::getTotalArmorValue));
                case "distance" -> entityList.sort(Comparator.comparing(mc.player::getDistanceToEntity));
                case "hurttime" -> entityList.sort(Comparator.comparing(EntityLivingBase::getHurtTime).reversed());
            }
            if (!entityList.isEmpty()) {
                targetEntity = entityList.get(0);
            }
            if (targetEntity != null && targetEntity.isEntityAlive() && (targetEntity != mc.player) && canAttack(targetEntity) && !(inventoryCheck.getBooleanValue() && mc.player.openContainer == null)) {
                if (!keepSprint.getBooleanValue()) {
                    mc.gameSettings.keyBindSprint.pressed = false;
                    mc.player.setSprinting(false);
                }
                if (!dynamicSprint.getBooleanValue()) {
                    dynamicSprintState = false;
                }
                float TPS = 20 - Yaff.of.tpsController.getTickRate();
                if (!(rotateOnCrit.getBooleanValue() && mc.player.fallDistance > 0) && !(rotateOnCooldown.getBooleanValue() && mc.player.getCooledAttackStrength(tpsSync.getBooleanValue() ? TPS : 0) >= attackCooldown.getNumericValue())) {
                    if (!rotationMode.getListValue().equalsIgnoreCase("None")) {
                        if (java.lang.Math.abs(targetEntity.posX - targetEntity.lastTickPosX) > 0.5f || java.lang.Math.abs(targetEntity.posZ - targetEntity.lastTickPosZ) > 0.5f) {
                            targetEntity.setEntityBoundingBox(new AxisAlignedBB(targetEntity.posX, targetEntity.posY, targetEntity.posZ, targetEntity.lastTickPosX, targetEntity.lastTickPosY, targetEntity.lastTickPosZ));
                        }
                        if (timeManager.hasReached(Aura.rotationDelay.getNumericValue()) || playerRotation == null) {
                            playerRotation = Rotation.getRotations(targetEntity, shakeDiff.getNumericValue(), toggleShake.getBooleanValue(), smartShake.getBooleanValue(), rotationPredict.getNumericValue());
                            timeManager.reset();
                        }
                        switch (rotationMode.getListValue().toLowerCase()) {
                            case "client" -> Rotation.setClientRotations(playerRotation);
                            case "packet" -> Rotation.setPacketRotations(motionEvent, playerRotation);
                            case "matrix" -> Rotation.setPacketRotations(motionEvent, new float[]{ playerRotation[0] + Random.getRandomFloat(-1.98f, 1.98f), playerRotation[1] * 1.001f });
                        }
                    } else {
                        playerRotation = new float[]{ mc.player.rotationYaw, mc.player.rotationPitch };
                    }
                }
                boolean isCritical = Random.getRandomInt(1, 100) <= critChance.getNumericValue();
                if (unsprintOnCrit.getBooleanValue() && mc.player.fallDistance > 0 && isCritical) {
                    mc.player.setSprinting(false);
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (!isCritical || !(onlyCrits.getBooleanValue() && !World.isCriticalHit())) {
                    if ((rayTrace.getBooleanValue() && RayCast.isInView(playerRotation[0], playerRotation[1], targetEntity, attackDistance.getNumericValue())) || (triggerBot.getBooleanValue() && RayCast.isInView(playerRotation[0], playerRotation[1], targetEntity, attackDistance.getNumericValue()))) {
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                        targetEntity = null;
                        return;
                    }
                    if (targetEntity.hurtTime > hurtTime.getNumericValue() || (targetEntity.hurtResistantTime >= targetEntity.maxHurtResistantTime / 2f)) {
                        return;
                    }
                    mc.playerController.syncCurrentPlayItem();
                    switch (attackStyle.getListValue().toLowerCase()) {
                        case "old" -> {
                            if (Random.getRandomInt(1, 100) <= hitChance.getNumericValue()) {
                                if (dynamicSprint.getBooleanValue()) {
                                    dynamicSprintState = true;
                                    mc.player.setSprinting(false);
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                                }
                                mc.playerController.attackEntity(mc.player, rayTrace.getBooleanValue() ? Objects.requireNonNull(RayCast.rayCast(targetEntity, attackDistance.getNumericValue())) : targetEntity);
                                if (swingHand.getBooleanValue()) {
                                    mc.player.swingArm(EnumHand.MAIN_HAND);
                                } else {
                                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                                }
                                if (dynamicSprint.getBooleanValue() && keepSprint.getBooleanValue()) {
                                    dynamicSprintState = false;
                                    mc.player.setSprinting(true);
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                                }
                            }
                        }
                        case "new" -> {
                            if (mc.player.getCooledAttackStrength(tpsSync.getBooleanValue() ? TPS : 0) >= attackCooldown.getNumericValue() && Random.getRandomInt(1, 100) <= hitChance.getNumericValue()) {
                                if (dynamicSprint.getBooleanValue()) {
                                    dynamicSprintState = true;
                                    mc.player.setSprinting(false);
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                                }
                                mc.playerController.attackEntity(mc.player, rayTrace.getBooleanValue() ? Objects.requireNonNull(RayCast.rayCast(targetEntity, attackDistance.getNumericValue())) : targetEntity);
                                if (swingHand.getBooleanValue()) {
                                    mc.player.swingArm(EnumHand.MAIN_HAND);
                                } else {
                                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                                }
                                if (dynamicSprint.getBooleanValue() && keepSprint.getBooleanValue()) {
                                    dynamicSprintState = false;
                                    mc.player.setSprinting(true);
                                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                                }
                            }
                        }
                    }
                    if (keepSprint.getBooleanValue() && mc.gameSettings.keyBindSprint.pressed) {
                        mc.player.setSprinting(true);
                    }
                    if (World.isCriticalHit()) {
                        mc.player.onCriticalHit(targetEntity);
                    }
                }
            } else {
                targetEntity = null;
                entityList.clear();
            }
        }
        if (event instanceof UpdateEvent) {
            if (disableOnDeath.getBooleanValue() && !mc.player.isEntityAlive()) {
                super.setState(false);
            }
        }
        if (event instanceof StrafeEvent strafeEvent) {
            if (targetEntity != null && mc.player.getDistanceToEntity(targetEntity) > 2 && strafeMode.getListValue().equalsIgnoreCase("Silent")) {
                strafeEvent.setYaw(playerRotation[0]);
            }
        }
        if (event instanceof store.yaff.event.impl.Render.Render2D) {
            if (targetEntity != null) {
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            targetEntity = null;
        }
    }
    // primer dlya yakubenko - private java.awt.Color gradientColor1 = java.awt.Color.WHITE, gradientColor2 = java.awt.Color.WHITE, gradientColor3 = java.awt.Color.WHITE, gradientColor4 = java.awt.Color.WHITE;
}

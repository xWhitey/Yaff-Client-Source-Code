package store.yaff.feature;

import org.lwjgl.input.Keyboard;
import store.yaff.feature.impl.combat.*;
import store.yaff.feature.impl.exploit.*;
import store.yaff.feature.impl.hud.ArrayList;
import store.yaff.feature.impl.hud.HUD;
import store.yaff.feature.impl.hud.Keystrokes;
import store.yaff.feature.impl.hud.Watermark;
import store.yaff.feature.impl.misc.*;
import store.yaff.feature.impl.movement.*;
import store.yaff.feature.impl.player.*;
import store.yaff.feature.impl.render.*;
import store.yaff.feature.impl.world.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FeatureManager {
    public final CopyOnWriteArrayList<AbstractFeature> features = new CopyOnWriteArrayList<>();

    public FeatureManager() {
        // COMBAT
        features.add(new AutoClicker("AutoClicker", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new Aura("Aura", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AutoGApple("AutoGApple", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AntiBot("AntiBot", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new FastBow("FastBow", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new Criticals("Criticals", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new Regen("Regen", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AutoSword("AutoSword", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AttackParticles("AttackParticles", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new TargetStrafe("TargetStrafe", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new HitBox("HitBox", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new Reach("Reach", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AutoArmor("AutoArmor", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new AutoPotion("AutoPotion", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new WTap("WTap", "None", Category.COMBAT, Keyboard.KEY_NONE));
        features.add(new Velocity("Velocity", "None", Category.COMBAT, Keyboard.KEY_NONE));
        // MOVEMENT
        features.add(new Flight("Flight", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Jesus("Jesus", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Leave("Leave", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new NoVoid("NoVoid", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Strafe("Strafe", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Spider("Spider", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Speed("Speed", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new HighJump("HighJump", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        features.add(new Sprint("Sprint", "None", Category.MOVEMENT, Keyboard.KEY_NONE));
        // PLAYER
        features.add(new Blink("Blink", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new NoDelay("NoDelay", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new FastUse("FastUse", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new Spinner("Spinner", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new Timer("Timer", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new SafeWalk("SafeWalk", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new AntiAFK("AntiAFK", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new AutoRespawn("AutoRespawn", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new Freecam("Freecam", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new NoFall("NoFall", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new NoSlow("NoSlow", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new XCarry("XCarry", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new Zoot("Zoot", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new Murder("Murder", "None", Category.PLAYER, Keyboard.KEY_NONE));
        features.add(new SolidWeb("SolidWeb", "None", Category.PLAYER, Keyboard.KEY_NONE));
        // WORLD
        features.add(new NoPush("NoPush", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new PortalChat("PortalChat", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new FastWorldLoad("FastWorldLoad", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new ChestStealer("ChestStealer", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new Eagle("Eagle", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new Scaffold("Scaffold", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new NoInteract("NoInteract", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new Breaker("Breaker", "None", Category.WORLD, Keyboard.KEY_NONE));
        features.add(new XRay("XRay", "None", Category.WORLD, Keyboard.KEY_NONE));
        // RENDER
        features.add(new NightMode("NightMode", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new ViewModel("ViewModel", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new CustomFog("CustomFog", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new Breadcrumbs("Breadcrumbs", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new Animations("Animations", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new ChunkAnimations("ChunkAnimations", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new Scoreboard("Scoreboard", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new CameraClip("CameraClip", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new StorageESP("StorageESP", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new ESP("ESP", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new TrueSight("TrueSight", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new ItemPhysics("ItemPhysics", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new NameProtect("NameProtect", "None", Category.RENDER, Keyboard.KEY_NONE));
        features.add(new GUI("GUI", "None", Category.RENDER, Keyboard.KEY_RSHIFT));
        // EXPLOIT
        features.add(new NoPitchLimit("NoPitchLimit", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        features.add(new InstantEC("InstantEC", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        features.add(new PingSpoof("PingSpoof", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        features.add(new PearlLogger("PearlLogger", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        features.add(new AntiHunger("AntiHunger", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        features.add(new NoRotation("NoRotation", "None", Category.EXPLOIT, Keyboard.KEY_NONE));
        // MISC
        features.add(new FeatureSounds("FeatureSounds", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new PacketLimiter("PacketLimiter", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new BetterChat("BetterChat", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new DeathCoords("DeathCoords", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new Macro("Macro", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new FlagListener("FlagListener", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new FancyChat("FancyChat", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new ChatBypass("ChatBypass", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new NoSwing("NoSwing", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new MCF("MCF", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new MCP("MCP", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new CPUReducer("CPUReducer", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new Friends("Friends", "None", Category.MISC, Keyboard.KEY_NONE));
        features.add(new GuiWalk("GuiWalk", "None", Category.MISC, Keyboard.KEY_NONE));
        // HUD
        features.add(new Watermark("Watermark", "None", Category.HUD, Keyboard.KEY_NONE));
        features.add(new ArrayList("ArrayList", "None", Category.HUD, Keyboard.KEY_NONE));
        features.add(new Keystrokes("Keystrokes", "None", Category.HUD, Keyboard.KEY_NONE));
        features.add(new HUD("HUD", "None", Category.HUD, Keyboard.KEY_NONE));
    }

    @Nullable
    public CopyOnWriteArrayList<AbstractFeature> getFeatures() {
        try {
            return features;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public List<AbstractFeature> getFeatures(boolean featureState) {
        try {
            return features.stream().filter(f -> f.state == featureState).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractFeature getFeature(String featureName) {
        try {
            return features.stream().filter(f -> f.getName().equalsIgnoreCase(featureName)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public List<AbstractFeature> getFeatures(Category featureCategory) {
        try {
            return features.stream().filter(f -> f.getCategory().equals(featureCategory)).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractFeature getFeature(Class<? extends AbstractFeature> featureClass) {
        try {
            return features.stream().filter(f -> f.getClass().equals(featureClass)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

}

package store.yaff.engine.locale;

import net.minecraft.client.Minecraft;
import store.yaff.Yaff;
import store.yaff.feature.impl.render.NameProtect;

import java.util.Objects;

public class YI18n {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static String format(String translateKey) {
        return LanguageController.YI18n.translateKeyPrivate(translateKey)
                .replace("{player.name}", Objects.requireNonNull(Yaff.of.featureManager.getFeature(NameProtect.class)).getState() ? NameProtect.protectedName : mc.getSession().getUsername())
                .replace("{client.name}", Yaff.of.name)
                .replace("{client.build}", Yaff.of.build)
                .replace("{player.health}", String.valueOf(mc.player != null ? mc.player.getHealth() : 0))
                .replace("{player.armor}", String.valueOf(mc.player != null ? mc.player.getTotalArmorValue() : 0))
                .replace("{player.food.food}", String.valueOf(mc.player != null ? mc.player.getFoodStats().getFoodLevel() : 0))
                .replace("{player.food.saturation}", String.valueOf(mc.player != null ? mc.player.getFoodStats().getSaturationLevel() : 0));
    }

}

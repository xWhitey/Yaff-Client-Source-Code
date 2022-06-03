package store.yaff.feature.impl.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.render.NameProtect;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.tenacity.Rounded;

import java.util.Objects;

public class Watermark extends AbstractFeature {
    public Watermark(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof store.yaff.event.impl.Render.Render2D) {
            String name = mc.getSession().getUsername();
            if (Objects.requireNonNull(Yaff.of.featureManager.getFeature(NameProtect.class)).getState()) {
                name = "Yaff Loves You";
            }
            String text = " Yaff" + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.RESET + name + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.RESET + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + " ms" + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.RESET + Minecraft.getDebugFPS() + " fps ";
            Rounded.drawRound(4, 3, 8 + TTFFontManager.fontRenderer14SGM.getWidth(text), 6, 4, Color.rgb(133, 133, 199));
            Rounded.drawRound(4, 4, 8 + TTFFontManager.fontRenderer14SGM.getWidth(text), 16, 4, Color.rgb(21, 22, 25));
            TTFFontManager.fontRenderer14SGM.drawStringWithShadow(text, 6, 7, -1);
        }
    }

}

package store.yaff.font;

import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TTFFontManager {
    public static final TTFFontRenderer fontRenderer24MSB = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Montserrat-SemiBold.ttf"), 24, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer fontRenderer14SGM = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/SpaceGrotesk-Medium.ttf"), 14, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer fontRenderer16SGM = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/SpaceGrotesk-Medium.ttf"), 16, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer fontRenderer18SGM = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/SpaceGrotesk-Medium.ttf"), 18, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer fontRenderer20SGM = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/SpaceGrotesk-Medium.ttf"), 20, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer fontRenderer24SGM = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/SpaceGrotesk-Medium.ttf"), 24, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer faRenderer60I = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Icons.ttf"), 60, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer faRenderer33I = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Icons.ttf"), 33, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer faRenderer24I = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Icons.ttf"), 24, Font.TRUETYPE_FONT));
    public static final TTFFontRenderer faRenderer16I = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Icons.ttf"), 16, Font.TRUETYPE_FONT));

}

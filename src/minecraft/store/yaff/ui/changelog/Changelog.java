package store.yaff.ui.changelog;

import net.minecraft.util.ResourceLocation;
import store.yaff.font.TTFFontRenderer;
import store.yaff.helper.Color;

import java.awt.*;
import java.util.ArrayList;

public class Changelog {
    public final TTFFontRenderer faRenderer = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Icons.ttf"), 20, Font.TRUETYPE_FONT));
    public final TTFFontRenderer fontRenderer = new TTFFontRenderer(TTFFontRenderer.getFontFromTTF(new ResourceLocation("yaff/fonts/Montserrat-SemiBold.ttf"), 24, Font.TRUETYPE_FONT));
    private final ArrayList<Segment> changelog = new ArrayList<>();
    private int i = -5;

    public Changelog() {
        changelog.add(new Segment("New MainMenu & Loading Screen", SegmentType.NEW));
        changelog.add(new Segment("Improved Aura & Rotations", SegmentType.IMPROVED));
        changelog.add(new Segment("Recoded NameProtect", SegmentType.RECODED));
        changelog.add(new Segment("Added Matrix setting to AntiBot", SegmentType.ADDED));
        changelog.add(new Segment("Renamed Over category to Misc", SegmentType.RENAMED));
        changelog.add(new Segment("Moved FancyChat & ChatBypass to Misc category", SegmentType.MOVED));
        changelog.add(new Segment("Fixed scoreboard numbers render", SegmentType.FIXED));
        changelog.add(new Segment("Removed some fonts", SegmentType.REMOVED));
    }

    public ArrayList<Segment> getChangelog() {
        return this.changelog;
    }

    public void renderChangelog() {
        for (Segment s : changelog) {
            i += 15;
            int x = 10;
            switch (s.getType()) {
                case ADDED, NEW, REMOVED, MOVED, RECODED, FIXED, IMPROVED, RENAMED, NONE -> {
                    //faRenderer.drawString("t", x, i, Color.hex(0xFF8585c7, 242));
                    fontRenderer.drawString(s.getContent(), x + 2, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                    Color.glColor(Color.hex(0xFF3b3b40, 242));
                }
                //case REMOVED -> {
                //    //faRenderer.drawString("v", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case MOVED -> {
                //    //faRenderer.drawString("a", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case RECODED -> {
                //    //faRenderer.drawString("w", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case FIXED -> {
                //    //faRenderer.drawString("x", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case IMPROVED -> {
                //    //faRenderer.drawString("A", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case RENAMED -> {
                //    //faRenderer.drawString("H", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
                //case NONE -> {
                //    //faRenderer.drawString("K", x, i, Color.hex(0xFF8585c7, 242));
                //    fontRenderer.drawString(s.getContent(), x + faRenderer.getWidth("t") + 5, i - 0.5f, Color.hex(0xFF3b3b40, 242));
                //}
            }
        }
        i = -5;
    }

}

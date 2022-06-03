package store.yaff.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import store.yaff.helper.tenacity.GL;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public class Render {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();

    public static void drawHead(ResourceLocation skin, float x, float y) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 8.0F, 8.0F, 8, 8, 46, 46, 64.0F, 64.0F);
    }

    public static void drawItem(ItemStack itemStack, float x, float y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int) x, (int) y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, (int) x, (int) y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableDepth();
    }

    public static void renderBlockFrame(BlockPos blockPos, int color, double xzOffset, double yOffset) {
        double x = blockPos.getX() - RenderManager.renderPosX;
        double y = blockPos.getY() - RenderManager.renderPosY;
        double z = blockPos.getZ() - RenderManager.renderPosZ;
        GL.setup3DRendering(() -> {
            GlStateManager.color((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
            drawAABBFrame(new AxisAlignedBB(x + xzOffset, y, z + xzOffset, x + 1 - xzOffset, y + 1 - yOffset, z + 1 - xzOffset));
            GlStateManager.color(0, 0, 0, 0);
        });
    }

    public static void drawAABBFrame(AxisAlignedBB boundingBox) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(3, DefaultVertexFormats.POSITION);
        vb.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        ts.draw();
        vb.begin(3, DefaultVertexFormats.POSITION);
        vb.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        ts.draw();
        vb.begin(1, DefaultVertexFormats.POSITION);
        vb.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vb.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        ts.draw();
    }

    public static void renderBreadCrumbs(final List<Vec3d> vec3s) {
        GlStateManager.disableDepth();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int i = 0;
        try {
            for (final Vec3d v : vec3s) {
                i++;
                boolean draw = true;
                final double x = v.xCoord - RenderManager.renderPosX;
                final double y = v.yCoord - RenderManager.renderPosY;
                final double z = v.zCoord - RenderManager.renderPosZ;
                final double distanceFromPlayer = mc.player.getDistance(v.xCoord, v.yCoord - 1, v.zCoord);
                int quality = (int) (distanceFromPlayer * 4 + 10);
                if (quality > 350)
                    quality = 350;
                if (i % 10 != 0 && distanceFromPlayer > 25) {
                    draw = false;
                }
                if (i % 3 == 0 && distanceFromPlayer > 15) {
                    draw = false;
                }
                if (draw) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, z);
                    final float scale = 0.04f;
                    GL11.glScalef(-scale, -scale, -scale);
                    GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated((mc.getRenderManager()).playerViewX, 1.0D, 0.0D, 0.0D);
                    Render.drawFilledCircleNoGL(0, 0, 0.7, Color.rgb(100, 100, 255), quality);
                    if (distanceFromPlayer < 4)
                        Render.drawFilledCircleNoGL(0, 0, 1.4, Color.rgba(100, 100, 255, 50), quality);
                    if (distanceFromPlayer < 20)
                        Render.drawFilledCircleNoGL(0, 0, 2.3, Color.rgba(100, 100, 255, 30), quality);
                    GL11.glScalef(0.8f, 0.8f, 0.8f);
                    GL11.glPopMatrix();
                }
            }
        } catch (final ConcurrentModificationException ignored) {
        }
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableDepth();
        GL11.glColor3d(255, 255, 255);
    }

    public static void drawFilledCircleNoGL(final int x, final int y, final double r, final int c, int quality) {
        final float f = ((c >> 24) & 0xff) / 255F;
        final float f1 = ((c >> 16) & 0xff) / 255F;
        final float f2 = ((c >> 8) & 0xff) / 255F;
        final float f3 = (c & 0xff) / 255F;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = 0; i <= 360 / 20; i++) {
            final double x2 = Math.sin(((i * 20 * Math.PI) / 180)) * r;
            final double y2 = Math.cos(((i * 20 * Math.PI) / 180)) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }
        GL11.glEnd();
    }

    public static void renderBlock(BlockPos blockPos, int color, double xzOffset, double yOffset) {
        double x = blockPos.getX() - RenderManager.renderPosX;
        double y = blockPos.getY() - RenderManager.renderPosY;
        double z = blockPos.getZ() - RenderManager.renderPosZ;
        GL.setup3DRendering(() -> {
            GlStateManager.color((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
            drawAABBBox(new AxisAlignedBB(x + xzOffset, y, z + xzOffset, x + 1 - xzOffset, y + 1 - yOffset, z + 1 - xzOffset));
            GlStateManager.color(0, 0, 0, 0);
        });
    }

    public static void renderEntityBox(Entity entityIn, int color) {
        double x = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * mc.timer.field_194147_b - RenderManager.renderPosX;
        double y = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * mc.timer.field_194147_b - RenderManager.renderPosY;
        double z = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * mc.timer.field_194147_b - RenderManager.renderPosZ;
        GL.setup3DRendering(() -> {
            GlStateManager.color((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
            AxisAlignedBB aabb = entityIn.getEntityBoundingBox();
            AxisAlignedBB aabb1 = new AxisAlignedBB(aabb.minX - entityIn.posX + x, aabb.minY - entityIn.posY + y, aabb.minZ - entityIn.posZ + z, aabb.maxX - entityIn.posX + x, aabb.maxY - entityIn.posY + y, aabb.maxZ - entityIn.posZ + z);
            drawAABBBox(aabb1.expandXyz(0.1f));
            GlStateManager.color(0, 0, 0, 0);
        });
    }

    public static void renderBox(AxisAlignedBB aabb, int color, double xzOffset, double yOffset) {
        GL.setup3DRendering(() -> {
            GlStateManager.color((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
            drawAABBBox(new AxisAlignedBB(aabb.minX - RenderManager.renderPosX, aabb.minY - RenderManager.renderPosY, aabb.minZ - RenderManager.renderPosZ, aabb.maxX - RenderManager.renderPosX, aabb.maxY - RenderManager.renderPosY, aabb.maxZ - RenderManager.renderPosZ));
            GlStateManager.color(0, 0, 0, 0);
        });
    }

    public static void drawAABBBox(AxisAlignedBB axisalignedbb) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
        ts.draw();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        double finalLeft = left;
        double finalBottom = bottom;
        double finalRight = right;
        double finalTop = top;
        GL.setup2DRendering(() -> {
            GlStateManager.color((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
            bufferBuilder.pos(finalLeft, finalBottom, 0.0D).endVertex();
            bufferBuilder.pos(finalRight, finalBottom, 0.0D).endVertex();
            bufferBuilder.pos(finalRight, finalTop, 0.0D).endVertex();
            bufferBuilder.pos(finalLeft, finalTop, 0.0D).endVertex();
            tessellator.draw();
        });
    }

    public static void drawRoundedRectXY(double x, double y, double width, double height, double radius, int color) {
        double x1 = x + width;
        double y1 = y + height;
        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
        glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + +(java.lang.Math.sin((i * java.lang.Math.PI / 180)) * (radius * -1)), y + radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * (radius * -1)));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * (radius * -1)), y1 - radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * (radius * -1)));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * radius), y1 - radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * radius));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * radius), y + radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * radius));
        }
        GL11.glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_LINE_SMOOTH);
        glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2, 2, 2);
        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, double radius, int color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        left *= 2;
        top *= 2;
        right *= 2;
        bottom *= 2;
        glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
        glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(left + radius + +(java.lang.Math.sin((i * java.lang.Math.PI / 180)) * (radius * -1)), top + radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * (radius * -1)));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(left + radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * (radius * -1)), bottom - radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * (radius * -1)));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(right - radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * radius), bottom - radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * radius));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(right - radius + (java.lang.Math.sin((i * java.lang.Math.PI / 180)) * radius), top + radius + (java.lang.Math.cos((i * java.lang.Math.PI / 180)) * radius));
        }
        GL11.glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_LINE_SMOOTH);
        glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2, 2, 2);
        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawBorderedRect(double x, double y, double width, double height, double borderWidth, int insideColor, int borderColor) {
        drawRoundedRectXY(x, y, width, height, 0, insideColor);
        drawRoundedRectXY(x, y, width, borderWidth, 0, borderColor);
        drawRoundedRectXY(x, y + borderWidth, borderWidth, height - borderWidth * 2, 0, borderColor);
        drawRoundedRectXY(x, y + height - borderWidth, width, borderWidth, 0, borderColor);
        drawRoundedRectXY(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth * 2, 0, borderColor);
    }

    public static void drawOutlinedRect(double x, double y, double width, double height, double borderWidth, int insideColor, int borderColor) {
        drawRoundedRectXY(x, y, width, height, 0, borderColor);
        drawRoundedRectXY(x + borderWidth, y + borderWidth, width - borderWidth, height - borderWidth, 0, insideColor);
    }

    public static void drawBorderedRect(double x, double y, double width, double height, double borderWidth, int color) {
        drawRoundedRectXY(x, y, width / 3f, borderWidth, 0, color);
        drawRoundedRectXY(x, y, borderWidth, height / 3f, 0, color);
        drawRoundedRectXY(x, y + height - borderWidth, width / 3f, borderWidth, 0, color);
        drawRoundedRectXY(x, y + height, borderWidth, -height / 3f, 0, color);
        drawRoundedRectXY(x + width - borderWidth, y, borderWidth, height / 3f, 0, color);
        drawRoundedRectXY(x + width, y, -width / 3f, borderWidth, 0, color);
        drawRoundedRectXY(x + width - borderWidth, y + height, borderWidth, -height / 3f, 0, color);
        drawRoundedRectXY(x + width, y + height - borderWidth, -width / 3f, borderWidth, 0, color);
    }

}

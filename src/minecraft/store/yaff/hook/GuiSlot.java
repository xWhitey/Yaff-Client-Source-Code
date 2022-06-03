package store.yaff.hook;

import net.minecraft.client.Minecraft;

public class GuiSlot {
    net.minecraft.client.gui.GuiSlot guiSlot;

    public GuiSlot(int width, int height, int topIn, int bottomIn, int slotHeightIn) {
        this.guiSlot = new Slot(width, height, topIn, bottomIn, slotHeightIn);
    }

    public net.minecraft.client.gui.GuiSlot getGuiSlot() {
        return guiSlot;
    }

    static class Slot extends net.minecraft.client.gui.GuiSlot {
        public Slot(int width, int height, int topIn, int bottomIn, int slotHeightIn) {
            super(Minecraft.getMinecraft(), width, height, topIn, bottomIn, slotHeightIn);
        }

        protected int getSize() {
            return 0;
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        }

        protected boolean isSelected(int slotIndex) {
            return false;
        }

        protected void drawBackground() {
        }

        protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
        }

    }

}

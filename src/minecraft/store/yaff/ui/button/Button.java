package store.yaff.ui.button;

import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.Render;

public class Button {
    protected final int id;
    protected final double width, height;
    protected final String icon;
    protected double x, y;
    protected int color;

    public Button(int id, double x, double y, double width, double height, int color, String icon) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.icon = icon;
    }

    public void drawButton() {
        Render.drawBorderedRect(this.x, this.y, this.width, this.height, 4.5f, this.color);
        Color.glColor(this.color);
        TTFFontManager.faRenderer60I.drawString(this.icon, this.x + this.width / 2f - TTFFontManager.faRenderer60I.getWidth(this.icon) / 2f + 1, this.y + this.height / 2f - TTFFontManager.faRenderer60I.getHeight(icon) / 2f + 2, this.color);
    }

    public int getClickId(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            return this.id;
        }
        return 0;
    }

    public boolean isTriggered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setColor(int color) {
        this.color = color;
    }

}

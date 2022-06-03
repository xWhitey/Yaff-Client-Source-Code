package store.yaff.ui.menu;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import store.yaff.hook.GuiSlot;
import store.yaff.ui.altmanager.PasswordField;

import java.io.IOException;

public class GuiAltLogin extends GuiScreen {
    protected final GuiScreen previousScreen;
    protected PasswordField password;
    protected AltLoginThread thread;
    protected GuiTextField username;
    private GuiSlot guiSlot;

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1 -> this.mc.displayGuiScreen(this.previousScreen);
            case 0 -> {
                this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                this.thread.start();
            }
            case 2 -> mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        guiSlot.getGuiSlot().drawScreen(x2, y2, z2);
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Alt Login", width / 2, 7, -1);
        this.drawCenteredString(this.mc.fontRendererObj, this.thread == null ? ChatFormatting.GRAY + "Idle..." : this.thread.getStatus(), width / 2, 16, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 41, 100, 20, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height - 41, 100, 20, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 4 + 50, this.height - 41, 100, 20, "Multiplayer"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.username.setFocused(true);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        guiSlot = new GuiSlot(this.width, this.height, 32, this.height - 65 + 4, 18);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        } catch (IOException ignored) {
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }

}

package net.minecraft.client.gui;

import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import store.yaff.hook.GuiSlot;

import java.io.IOException;

public class ScreenChatOptions extends GuiScreen {
    private static final GameSettings.Options[] CHAT_OPTIONS = new GameSettings.Options[]{ GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO, GameSettings.Options.NARRATOR };
    private final GuiScreen parentScreen;
    private final GameSettings game_settings;
    private String chatTitle;
    private GuiOptionButton field_193025_i;

    private GuiSlot guiSlot;

    public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
        this.parentScreen = parentScreenIn;
        this.game_settings = gameSettingsIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.chatTitle = I18n.format("options.chat.title");
        int i = 0;
        for (GameSettings.Options gamesettings$options : CHAT_OPTIONS) {
            if (gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options));
            } else {
                GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options));
                this.buttonList.add(guioptionbutton);
                if (gamesettings$options == GameSettings.Options.NARRATOR) {
                    this.field_193025_i = guioptionbutton;
                    guioptionbutton.enabled = NarratorChatListener.field_193643_a.func_193640_a();
                }
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 41, I18n.format("gui.done")));
        guiSlot = new GuiSlot(this.width, this.height, 32, this.height - 65 + 4, 18);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.gameSettings.saveOptions();
        }
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id < 100 && button instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
                button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        guiSlot.getGuiSlot().drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.chatTitle, this.width / 2, 13, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void func_193024_a() {
        this.field_193025_i.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_193025_i.id));
    }

}

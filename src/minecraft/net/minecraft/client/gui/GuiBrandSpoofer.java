package net.minecraft.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.resources.I18n;
import store.yaff.helper.StringGenerator;
import store.yaff.hook.GuiSlot;

import java.io.IOException;

public class GuiBrandSpoofer extends GuiScreen {
    protected final GuiScreen parentScreen;
    protected final StringGenerator stringGenerator = new StringGenerator(true, false, true, false);
    protected String title = "Client Brand Spoofer";
    private GuiSlot guiSlot;

    public GuiBrandSpoofer(GuiScreen screen) {
        this.parentScreen = screen;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(121, this.width / 2 - 155, this.height / 6 - 12, 150, 20, "Vanilla"));
        this.buttonList.add(new GuiButton(122, this.width / 2 + 5, this.height / 6 - 12, 150, 20, "Forge"));
        this.buttonList.add(new GuiButton(123, this.width / 2 - 155, this.height / 6 + 24 - 12, 150, 20, "Lunar"));
        this.buttonList.add(new GuiButton(124, this.width / 2 + 5, this.height / 6 + 24 - 12, 150, 20, "Badlion"));
        this.buttonList.add(new GuiButton(125, this.width / 2 - 155, this.height / 6 + 48 - 12, 150, 20, "Tellum"));
        this.buttonList.add(new GuiButton(131, this.width / 2 - 155, this.height / 3 + 48 - 6, 150, 20, "1.8.9"));
        this.buttonList.add(new GuiButton(132, this.width / 2 + 5, this.height / 3 + 48 - 6, 150, 20, "1.10.2"));
        this.buttonList.add(new GuiButton(133, this.width / 2 - 155, this.height / 3 + 72 - 6, 150, 20, "1.12.2"));
        this.buttonList.add(new GuiButton(134, this.width / 2 + 5, this.height / 3 + 72 - 6, 150, 20, "1.14.4"));
        this.buttonList.add(new GuiButton(135, this.width / 2 - 155, this.height / 3 + 96 - 6, 150, 20, "1.15.2"));
        this.buttonList.add(new GuiButton(136, this.width / 2 + 5, this.height / 3 + 96 - 6, 150, 20, "1.16.5"));
        this.buttonList.add(new GuiButton(137, this.width / 2 + 5, this.height / 3 + 120 - 6, 150, 20, "1.17"));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 41, I18n.format("gui.done")));
        guiSlot = new GuiSlot(this.width, this.height, 32, this.height - 65 + 4, 18);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 200) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            if (button.id == 121) {
                ClientBrandRetriever.clientModName = "Vanilla";
            }
            if (button.id == 122) {
                ClientBrandRetriever.clientModName = "fml,forge";
            }
            if (button.id == 123) {
                ClientBrandRetriever.clientModName = "lunarclient:" + stringGenerator.generate(7);
            }
            if (button.id == 124) {
                ClientBrandRetriever.clientModName = "Badlion";
            }
            if (button.id == 125) {
                ClientBrandRetriever.clientModName = "Tellum";
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        guiSlot.getGuiSlot().drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Current Brand: " + ChatFormatting.GREEN + ClientBrandRetriever.clientModName, this.width / 2, this.height / 6 - 30, 16777215);
        this.drawCenteredString(this.fontRendererObj, "Current Version: " + ChatFormatting.GREEN + "[VIAVERSION]", this.width / 2, this.height / 3 + 24, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 13, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}

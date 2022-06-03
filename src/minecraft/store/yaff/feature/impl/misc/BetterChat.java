package store.yaff.feature.impl.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.Packet;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

import java.util.HashMap;

public class BetterChat extends AbstractFeature {
    protected HashMap<String, Integer> messageMap = new HashMap<>();
    protected HashMap<String, Integer> linesMap = new HashMap<>();

    public BetterChat(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Receive packetREvent) {
            if (packetREvent.getPacket() instanceof SPacketChat chatPacket) {
                ITextComponent textComponent = chatPacket.getChatComponent();
                String rawMessage = StringUtils.stripControlCodes(chatPacket.getChatComponent().getFormattedText());
                GuiNewChat chatInstance = mc.ingameGUI.getChatGUI();
                if (!messageMap.containsKey(rawMessage)) {
                    messageMap.put(rawMessage, 1);
                    linesMap.put(rawMessage, chatInstance.chatLines.size());
                } else {
                    messageMap.replace(rawMessage, messageMap.get(rawMessage) + 1);
                    textComponent.appendText(messageMap.get(rawMessage) > 1 ? ChatFormatting.GRAY + " [x" + messageMap.get(rawMessage) + "]" : "");
                    chatInstance.deleteChatLine(linesMap.get(rawMessage));
                    chatInstance.printChatMessageWithOptionalDeletion(textComponent, linesMap.get(rawMessage));
                    event.setCancelled(true);
                }
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            messageMap.clear();
            linesMap.clear();
        }
    }

}

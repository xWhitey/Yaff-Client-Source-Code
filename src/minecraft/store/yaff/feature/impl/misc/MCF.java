package store.yaff.feature.impl.misc;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MiddleClickEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.file.impl.Friend;
import store.yaff.friend.FriendController;
import store.yaff.helper.Chat;

import java.util.Objects;

public class MCF extends AbstractFeature {
    public MCF(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MiddleClickEvent) {
            if (mc.objectMouseOver.entityHit instanceof EntityOtherPlayerMP playerMP) {
                if (Yaff.of.friendController.getFriend(playerMP.getUniqueID()) != null) {
                    Objects.requireNonNull(Yaff.of.friendController.getFriend(playerMP.getUniqueID())).remove();
                    Chat.addChatMessage(playerMP.getNameClear() + " deleted from friend list");
                } else {
                    new FriendController.LocalFriend(playerMP.getNameClear(), playerMP.getUniqueID()).add();
                    Chat.addChatMessage(playerMP.getNameClear() + " added to friend list");
                }
                Friend.saveFriendList();
            }
        }
    }

}

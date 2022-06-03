package store.yaff.command.impl;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import store.yaff.Yaff;
import store.yaff.command.AbstractCommand;
import store.yaff.friend.FriendController;
import store.yaff.helper.Chat;

import java.util.Objects;

public class Friend extends AbstractCommand {
    public Friend(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            switch (args[1]) {
                case "add" -> {
                    if (args[2].equalsIgnoreCase(mc.player.getName())) {
                        Chat.addChatMessage("You can't add yourself to friend list");
                        return;
                    }
                    for (Entity entity : mc.world.getLoadedEntityList()) {
                        if (entity.getName().equalsIgnoreCase(args[2]) && entity instanceof EntityOtherPlayerMP playerMP) {
                            new FriendController.LocalFriend(playerMP.getNameClear(), playerMP.getUniqueID()).add();
                            Chat.addChatMessage(playerMP.getNameClear() + " added to friend list");
                            store.yaff.file.impl.Friend.saveFriendList();
                            return;
                        }
                    }
                    Chat.addChatMessage("Player " + args[2] + " not found");
                }
                case "remove" -> {
                    FriendController.LocalFriend friend = Yaff.of.friendController.getFriend(args[2]);
                    if (friend != null) {
                        friend.remove();
                        Chat.addChatMessage(args[2] + " deleted from friend list");
                        store.yaff.file.impl.Friend.saveFriendList();
                    } else {
                        Chat.addChatMessage("Friend with name " + args[2] + " not found");
                    }
                }
                case "list" -> {
                    if (Objects.requireNonNull(Yaff.of.friendController.getFriends()).isEmpty()) {
                        Chat.addChatMessage("Your friend list is empty");
                        return;
                    }
                    StringBuilder friends = new StringBuilder();
                    friends.append("Friends:");
                    for (FriendController.LocalFriend f : Objects.requireNonNull(Yaff.of.friendController.getFriends())) {
                        friends.append(" ").append(f.name()).append(",");
                    }
                    Chat.addChatMessage(friends.substring(0, friends.length() - 1) + ".");
                }
                case "clear" -> {
                    Objects.requireNonNull(Yaff.of.friendController.getFriends()).clear();
                    store.yaff.file.impl.Friend.saveFriendList();
                    Chat.addChatMessage("Friend list has been cleared");
                }
                default -> Chat.addChatMessage("Invalid action");
            }
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}

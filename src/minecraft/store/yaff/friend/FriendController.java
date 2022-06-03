package store.yaff.friend;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class FriendController {
    protected static final CopyOnWriteArrayList<LocalFriend> friends = new CopyOnWriteArrayList<>();

    @Nullable
    public CopyOnWriteArrayList<LocalFriend> getFriends() {
        try {
            return friends;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public LocalFriend getFriend(String friendName) {
        try {
            return friends.stream().filter(f -> f.name().equalsIgnoreCase(friendName)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public LocalFriend getFriend(UUID friendUUID) {
        try {
            return friends.stream().filter(f -> f.uuid().equals(friendUUID)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public record LocalFriend(String name, UUID uuid) {
        public void add() {
            friends.add(this);
        }

        public void remove() {
            friends.remove(this);
        }

    }

}

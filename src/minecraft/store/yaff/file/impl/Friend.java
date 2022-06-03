package store.yaff.file.impl;

import com.google.gson.*;
import store.yaff.Yaff;
import store.yaff.file.AbstractFile;
import store.yaff.friend.FriendController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Friend extends AbstractFile {
    public Friend(String name, String extension) {
        super(name, extension);
    }

    public static void saveFriendList() {
        JsonObject jsonObject = new JsonObject();
        for (FriendController.LocalFriend f : Objects.requireNonNull(Yaff.of.friendController.getFriends())) {
            JsonObject jsonFriend = new JsonObject();
            jsonFriend.addProperty("UUID", String.valueOf(f.uuid()));
            jsonObject.add(f.name(), jsonFriend);
        }
        try {
            PrintWriter sWriter = new PrintWriter(Objects.requireNonNull(Yaff.of.fileManager.getFile(Friend.class)).getPath());
            sWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
            sWriter.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public static void loadFriendList() {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new BufferedReader(new FileReader(Objects.requireNonNull(Yaff.of.fileManager.getFile(Friend.class)).getPath())));
            if (jsonElement instanceof JsonNull) {
                return;
            }
            for (Map.Entry<String, JsonElement> jsonFriends : jsonElement.getAsJsonObject().entrySet()) {
                JsonObject jsonFriend = (JsonObject) jsonFriends.getValue();
                new FriendController.LocalFriend(jsonFriends.getKey(), UUID.fromString(jsonFriend.get("UUID").getAsString())).add();
            }
        } catch (Exception ignored) {
        }
    }

}

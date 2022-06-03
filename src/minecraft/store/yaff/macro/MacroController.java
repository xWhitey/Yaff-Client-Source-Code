package store.yaff.macro;

import store.yaff.helper.Chat;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MacroController {
    protected static final CopyOnWriteArrayList<LocalMacro> macros = new CopyOnWriteArrayList<>();

    @Nullable
    public CopyOnWriteArrayList<LocalMacro> getMacros() {
        try {
            return macros;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public List<LocalMacro> getMacrosByKey(int key) {
        try {
            return macros.stream().filter(m -> m.getKey() == key).toList();
        } catch (Exception e) {
            return null;
        }
    }

    public record LocalMacro(int key, String command) {
        public int getKey() {
            return key;
        }

        public String getCommand() {
            return command;
        }

        public void onMacro(int key) {
            if (key == key()) {
                Chat.sendChatMessage(command);
            }
        }

        public void add() {
            macros.add(this);
        }

        public void remove() {
            macros.remove(this);
        }

    }

}

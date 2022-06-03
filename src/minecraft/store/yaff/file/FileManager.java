package store.yaff.file;

import store.yaff.Yaff;
import store.yaff.file.impl.Configuration;
import store.yaff.file.impl.Friend;
import store.yaff.file.impl.Locale;

import javax.annotation.Nullable;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileManager {
    public static final File fileDirectory = new File(System.getenv("APPDATA") + "\\." + Yaff.of.name.toLowerCase());

    public final CopyOnWriteArrayList<AbstractFile> files = new CopyOnWriteArrayList<>();

    public FileManager() {
        if (!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }
        files.add(new Configuration("configuration", ".yaff"));
        files.add(new Friend("friends", ".yaff"));
        files.add(new Locale("locale", ".yaff"));
    }

    @Nullable
    public CopyOnWriteArrayList<AbstractFile> getFiles() {
        try {
            return files;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractFile getFile(String fileName) {
        try {
            return files.stream().filter(f -> f.getName().equalsIgnoreCase(fileName)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractFile getFile(Class<? extends AbstractFile> fileClass) {
        try {
            return files.stream().filter(f -> f.getClass().equals(fileClass)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

}

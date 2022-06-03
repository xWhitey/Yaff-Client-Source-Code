package store.yaff.file;

import java.io.File;
import java.io.IOException;

public abstract class AbstractFile {
    protected final String name, extension, path;

    protected AbstractFile(String name, String extension) {
        this.name = name.toLowerCase();
        this.extension = extension;
        this.path = FileManager.fileDirectory + "\\" + name.toLowerCase() + extension;
        File materialFile = new File(path);
        if (!materialFile.exists()) {
            try {
                materialFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

}

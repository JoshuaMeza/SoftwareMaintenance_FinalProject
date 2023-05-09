package src.world;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.LinkOption;
import java.nio.file.Files;

import src.Room;

public class WorldConfigurationHandler implements Handler {
    private Handler next;

    public WorldConfigurationHandler() {
        setNext(null);
    }

    @Override
    public void setNext(Handler h) {
        this.next = h;
    }

    @Override
    public Room handle() {
        if (next != null) {
            return next.handle();
        } else {
            return null;
        }
    }

    public boolean verifyFileExistance(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path, new LinkOption[0]);
        } catch (Exception e) {
            return false;
        }
    }
}

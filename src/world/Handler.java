package src.world;

import src.Room;

public interface Handler {
    void setNext(Handler h);
    Room handle();
}

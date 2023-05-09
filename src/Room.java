package model;

public class Room extends StateRoom {
    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean accesible() {
        return true;
    }

    @Override
    public String moveNorth(Model model) {
        String output;

        if (canGo(getNorth())) {
            model.setCurrentRoom(getNorth());
            output = model.getRoomDescription();
        } else {
            output = getDefaultUnreachableMessage() + model.getRoomDescription();
        }

        return output;
    }

    @Override
    public String moveSouth(Model model) {
        String output;

        if (canGo(getSouth())) {
            model.setCurrentRoom(getSouth());
            output = model.getRoomDescription();
        } else {
            output = getDefaultUnreachableMessage() + model.getRoomDescription();
        }

        return output;
    }

    @Override
    public String moveEast(Model model) {
        String output;

        if (canGo(getEast())) {
            model.setCurrentRoom(getEast());
            output = model.getRoomDescription();
        } else {
            output = getDefaultUnreachableMessage() + model.getRoomDescription();
        }

        return output;
    }

    @Override
    public String moveWest(Model model) {
        String output;

        if (canGo(getWest())) {
            model.setCurrentRoom(getWest());
            output = model.getRoomDescription();
        } else {
            output = getDefaultUnreachableMessage() + model.getRoomDescription();
        }

        return output;
    }

    public void connectNorth(Room room) {
        this.setNorth(room);
        room.setSouth(this);
    }
    
    public void connectSouth(Room room) {
        this.setSouth(room);
        room.setNorth(this);
    }
    
    public void connectEast(Room room) {
        this.setEast(room);
        room.setWest(this);
    }
    
    public void connectWest(Room room) {
        this.setWest(room);
        room.setEast(this);
    }
}

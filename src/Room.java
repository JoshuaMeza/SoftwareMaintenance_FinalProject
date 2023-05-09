package src;

public class Room {
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;

    public boolean isNull() {
        return false;
    }

    public boolean accesible() {
        return true;
    }

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public void connectNorth(Room room) {
        this.northExit = room;
        room.northExit = this;
    }
    
    public void connectSouth(Room room) {
        this.southExit = room;
        room.southExit = this;
    }
    
    public void connectEast(Room room) {
        this.eastExit = room;
        room.eastExit = this;
    }
    
    public void connectWest(Room room) {
        this.westExit = room;
        room.westExit = this;
    }
}

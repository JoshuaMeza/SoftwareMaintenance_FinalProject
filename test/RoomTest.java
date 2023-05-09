package test;

import static org.junit.Assert.assertEquals;

import src.Room;
import org.junit.Before;
import org.junit.Test;

public class RoomTest {

    private Room room;
    
    @Before
    public void setUp() {
        room = new Room("O");
    }
    
    @Test
    public void testConnectNorth() {
        Room northRoom = new Room("T");
        room.connectNorth(northRoom);
        assertEquals(room.northExit, northRoom);
    }
    
    @Test
    public void testConnectSouth() {
        Room southRoom = new Room("T");
        room.connectSouth(southRoom);
        assertEquals(room.southExit, southRoom);
    }
    
    @Test
    public void testConnectEast() {
        Room eastRoom = new Room("T");
        room.connectEast(eastRoom);
        assertEquals(room.eastExit, eastRoom);
    }
    
    @Test
    public void testConnectWest() {
        Room westRoom = new Room("T");
        room.connectWest(westRoom);
        assertEquals(room.westExit, westRoom);
    }
}
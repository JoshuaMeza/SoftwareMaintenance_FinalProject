package test;

import static org.junit.Assert.assertEquals;

import model.Model;
import model.Room;
import org.junit.Before;
import org.junit.Test;

public class RoomTest {

    private Room room;
    private Model model;
    
    @Before
    public void setUp() {
        room = new Room();
        model = new Model();
    }
    
    @Test
    public void testMoveNorth() {
        Room northRoom = new Room();
        room.connectNorth(northRoom);
        String output = room.moveNorth(model);
        assertEquals(output, northRoom.getDescription());
        assertEquals(model.getCurrentRoom(), northRoom);
    }
    
    @Test
    public void testMoveSouth() {
        Room southRoom = new Room();
        room.connectSouth(southRoom);
        String output = room.moveSouth(model);
        assertEquals(output, southRoom.getDescription());
        assertEquals(model.getCurrentRoom(), southRoom);
    }
    
    @Test
    public void testMoveEast() {
        Room eastRoom = new Room();
        room.connectEast(eastRoom);
        String output = room.moveEast(model);
        assertEquals(output, eastRoom.getDescription());
        assertEquals(model.getCurrentRoom(), eastRoom);
    }
    
    @Test
    public void testMoveWest() {
        Room westRoom = new Room();
        room.connectWest(westRoom);
        String output = room.moveWest(model);
        assertEquals(output, westRoom.getDescription());
        assertEquals(model.getCurrentRoom(), westRoom);
    }
    
    @Test
    public void testConnectNorth() {
        Room northRoom = new Room();
        room.connectNorth(northRoom);
        assertEquals(room.getNorth(), northRoom);
        assertEquals(northRoom.getSouth(), room);
    }
    
    @Test
    public void testConnectSouth() {
        Room southRoom = new Room();
        room.connectSouth(southRoom);
        assertEquals(room.getSouth(), southRoom);
        assertEquals(southRoom.getNorth(), room);
    }
    
    @Test
    public void testConnectEast() {
        Room eastRoom = new Room();
        room.connectEast(eastRoom);
        assertEquals(room.getEast(), eastRoom);
        assertEquals(eastRoom.getWest(), room);
    }
    
    @Test
    public void testConnectWest() {
        Room westRoom = new Room();
        room.connectWest(westRoom);
        assertEquals(room.getWest(), westRoom);
        assertEquals(westRoom.getEast(), room);
    }

}
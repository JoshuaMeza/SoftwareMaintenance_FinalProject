package src.world;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.Room;

public class JsonConcreteHandler extends WorldConfigurationHandler {
    private final String SOURCE_PATH = "./config/settings.json";

    @Override
    public Room handle() {
        if (verifyFileExistance(SOURCE_PATH)) {
            return generateWorld();
        } else {
            return super.handle();
        }
    }

    private Room generateWorld() {
        JSONObject json = readConfig();
        HashMap<String, Room> rooms = generateRoomsMap(json);
        return rooms.get((String) json.get("Main"));
    }

    private JSONObject readConfig() {
        try {
            String jsonContent = Files.readString(Paths.get(SOURCE_PATH), StandardCharsets.UTF_8);
            return (JSONObject) new JSONParser().parse(jsonContent);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    private HashMap<String, Room> generateRoomsMap(JSONObject json) {
        try {
            HashMap<String, Room> rooms = new HashMap<>();
            JSONObject descriptions = (JSONObject) json.get("Descriptions");
            JSONObject connections = (JSONObject) json.get("Connections");

            ((JSONArray) json.get("Rooms")).forEach((Object o) -> {
                String room = (String) o;
                String description = (String) descriptions.get(room);
                rooms.put(room, new Room(description));
            });

            for (String roomName : rooms.keySet()) {
                JSONObject currentRoomConnections = (JSONObject) connections.get(roomName);
                Room currentRoom = rooms.get(roomName);
                Room north = rooms.get((String) currentRoomConnections.get("North"));
                Room south = rooms.get((String) currentRoomConnections.get("South"));
                Room east = rooms.get((String) currentRoomConnections.get("East"));
                Room west = rooms.get((String) currentRoomConnections.get("West"));
                currentRoom.setExits(north, east, south, west);
            }

            return rooms;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}

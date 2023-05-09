package src.world;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.HashMap;

import src.Room;

public class XmlConcreteHandler extends WorldConfigurationHandler {
    private final String SOURCE_PATH = "./config/settings.xml";
    private String main = "";

    @Override
    public Room handle() {
        if (verifyFileExistance(SOURCE_PATH)) {
            return generateWorld();
        } else {
            return super.handle();
        }
    }

    private Room generateWorld() {
        Document xml = (Document) readConfig();
        HashMap<String, Room> rooms = generateRoomsMap(xml);
        return rooms.get(main);
    }

    private Object readConfig() {
        try {
            File file = new File(SOURCE_PATH);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            return document;
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, Room> generateRoomsMap(Document xml) {
        HashMap<String, Room> rooms = new HashMap<>();
        try {
            main = xml.getElementsByTagName("main").item(0).getTextContent();
            NodeList nList = xml.getElementsByTagName("room");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String room = eElement.getAttribute("id");
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    rooms.put(room, new Room(description)); 
                }
            }
            for (String roomName : rooms.keySet()) {
                System.out.println("Roomname: " + roomName);
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String room = eElement.getAttribute("id");
                        if(roomName.equals(room)){
                            Room currentRoom = rooms.get(roomName);
                            String northexit = eElement.getElementsByTagName("northexit").item(0).getTextContent();
                            String southexit = eElement.getElementsByTagName("southexit").item(0).getTextContent();
                            String eastexit = eElement.getElementsByTagName("eastexit").item(0).getTextContent();
                            String westexit = eElement.getElementsByTagName("westexit").item(0).getTextContent();
                            Room north = rooms.get(northexit);
                            Room south = rooms.get(southexit);
                            Room east = rooms.get(eastexit);
                            Room west = rooms.get(westexit);
                            currentRoom.setExits(north, east, south, west);
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            return new HashMap<>();
        }
        return rooms;
    }
}

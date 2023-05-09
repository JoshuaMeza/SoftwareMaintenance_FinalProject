package src;

import src.world.Handler;
import src.world.JsonConcreteHandler;
import src.world.XmlConcreteHandler;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

import src.view.*;

public class Game 
{
    private ICLI view;
    private Handler worldLoaderHandler;
    private Parser parser;
    private Room currentRoom;
    private JSONObject jsonObject;
    private String source_languaje_path;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game(ICLI view) 
    {
        this.view = view;
        createRooms();
        parser = new Parser(view);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        this.worldLoaderHandler = new JsonConcreteHandler();
        worldLoaderHandler.setNext(new XmlConcreteHandler());
        this.currentRoom = worldLoaderHandler.handle();
        
        if (currentRoom == null) {
            this.currentRoom = new Room(jsonObject.get("Not_found").toString());
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {      
        String inputLine;
        view.clear();
        view.warningMsg(printLanguage());
        inputLine = view.read("> "); 
        while (true){
            if (inputLine.equals("1") || inputLine.equals("2")){
                if (inputLine.equals("1")){
                    source_languaje_path = "./config/locales/en.json";
                } else{
                    source_languaje_path = "./config/locales/es.json";
                }
                jsonObject = readConfig();
                break;
            }
            view.dangerMsg("Select one of the valid languajes numbers / Seleccione uno de los números de lenguaje válidos\n");
            inputLine = view.read("> "); 
        } 
        view.successMsg(printWelcome());
        printWhereAmI();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        view.print(jsonObject.get("Bye").toString());
        view.clear();
    }

    /**
     * Print out the opening message for the player.
     */
    private String printWelcome(){
        return jsonObject.get("Welcome").toString();
    }

    private String printLanguage(){
        return "Please select a language / Por favor selecciona un idioma\n" +
                "1- English / Inglés.\n" +
                "2- Spanish / Español.\n";
    }

    private void printWhereAmI()
    {
        view.print(jsonObject.get("You_are").toString() + currentRoom.getDescription()+"\n");
        view.print(jsonObject.get("Exits").toString());
        
        if(currentRoom.northExit != null) {
            view.warningMsg(jsonObject.get("North").toString());
        }
        if(currentRoom.eastExit != null) {
            view.warningMsg(jsonObject.get("East").toString());
        }
        if(currentRoom.southExit != null) {
            view.warningMsg(jsonObject.get("South").toString());
        }
        if(currentRoom.westExit != null) {
            view.warningMsg(jsonObject.get("West").toString());
        }
        view.print("\n");
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            view.dangerMsg(jsonObject.get("Unknown").toString());
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help") || commandWord.equals("ayuda"))
            printHelp();
        else if (commandWord.equals("go") || commandWord.equals("ir"))
            goRoom(command);
        else if (commandWord.equals("quit") || commandWord.equals("quitar"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        view.warningMsg(jsonObject.get("Help").toString());
        view.successMsg(jsonObject.get("Commands").toString());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()){
            System.out.println(jsonObject.get("Invalid_command").toString());
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north") || direction.equals("norte")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east") || direction.equals("este")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")|| direction.equals("sur")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west") || direction.equals("oeste")) {
            nextRoom = currentRoom.westExit;
        }

        if(nextRoom == null){
            System.out.println(jsonObject.get("Not_door").toString());
        } else {
            currentRoom = nextRoom;
            System.out.println(jsonObject.get("You_are").toString() + currentRoom.getDescription());
            System.out.print(jsonObject.get("Exits").toString());
            if(currentRoom.northExit != null) {
                System.out.print(jsonObject.get("North").toString());
            }
            if(currentRoom.eastExit != null) {
                System.out.print(jsonObject.get("East").toString());
            }
            if(currentRoom.southExit != null) {
                System.out.print(jsonObject.get("South").toString());
            }
            if(currentRoom.westExit != null) {
                System.out.print(jsonObject.get("West").toString());
            }
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            view.dangerMsg(jsonObject.get("Quit").toString());
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private JSONObject readConfig() {
        try {
            String jsonContent = Files.readString(Paths.get(source_languaje_path), StandardCharsets.UTF_8);
            return (JSONObject) new JSONParser().parse(jsonContent);
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}

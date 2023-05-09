package src;
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
    private Parser parser;
    private Room currentRoom;
        
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
        Room outside, theatre, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExits(null, theatre, lab, pub);
        theatre.setExits(null, null, null, outside);
        pub.setExits(null, outside, null, null);
        lab.setExits(outside, office, null, null);
        office.setExits(null, null, null, lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {      
        view.clear();

        view.successMsg(printWelcome());
        printWhereAmI();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        view.print("Thank you for playing. Good bye.\n");
        view.clear();
    }

    /**
     * Print out the opening message for the player.
     */
    private String printWelcome(){
        return "Welcome to the World of Zuul!\n" +
                "World of Zuul is a new, incredibly boring adventure game.\n" +
                "Type 'help' if you need help.\n";
    }

    private void printWhereAmI()
    {
        view.print("You are " + currentRoom.getDescription()+"\n");
        view.print("Exits: \n");
        
        if(currentRoom.northExit != null) {
            view.warningMsg("north ");
        }
        if(currentRoom.eastExit != null) {
            view.warningMsg("east ");
        }
        if(currentRoom.southExit != null) {
            view.warningMsg("south ");
        }
        if(currentRoom.westExit != null) {
            view.warningMsg("west ");
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
            view.dangerMsg("I don't know what you mean...\n");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
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
        view.warningMsg("You are lost. You are alone. You wander\n" + 
        "around at the university.\n"+
        "\n"+
        "Your command words are:\n");
        view.successMsg("   go quit help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()){
            System.out.println("Invalid command. You must specify where to go");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }

        currentRoom = nextRoom;
        view.print("You are " + currentRoom.getDescription());
        view.print("Exits: ");
        if(currentRoom.northExit != null) {
            view.warningMsg("north ");
        }
        if(currentRoom.eastExit != null) {
            view.warningMsg("east ");
        }
        if(currentRoom.southExit != null) {
            view.warningMsg("south ");
        }
        if(currentRoom.westExit != null) {
            view.warningMsg("west ");
        }
        view.print("\n");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            view.dangerMsg("Quit what?\n");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}

package src;

import src.view.CLIView;

public class Main {
    public static void main(String[] args) {
        new Game(new CLIView()).play();
    }
}

package engine;

import commands.LoadCommand;
import commands.LookCommand;
import model.GameState;
import view.GameUI;
import java.util.Scanner;

public class GameMenu {
    private GameUI ui;
    private Scanner scanner;
    private Parser parser;
    private GameState state;


    public GameMenu(GameUI ui, Scanner scanner, Parser parser, GameState state) {
        this.ui = ui;
        this.scanner = scanner;
        this.parser = parser;
        this.state = state;
    }


    public void show() {
        ui.print("welcome");
        ui.print("new_or_load");
        ui.printInline("prompt");

        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("load")) {
            new LoadCommand(parser).execute(state);
        } else {
            ui.print("intro_lore");
            ui.printRaw("Write /? for help menu");
            new LookCommand("").execute(state);
        }
    }
}
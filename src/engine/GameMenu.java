package engine;

import commands.Command;
import commands.LoadCommand;
import commands.LookCommand;
import model.GameState;
import view.GameUI;
import java.util.Scanner;
import java.util.Optional;

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
        ui.printRaw("Welcome to " + state.gameTitle);
        ui.print("new_or_load");
        ui.printInline("prompt");

        String choice = scanner.nextLine().trim().toLowerCase();
        Optional<Command> cmdOpt = parser.parseInput(choice);
        if (cmdOpt.isPresent() &&  cmdOpt.get() instanceof LoadCommand) {
            cmdOpt.get().execute(state);
        }
        else{
            ui.printRaw(state.introLore);
            ui.print("main_help_hint");
            new LookCommand("").execute(state);
        }


    }
}
package engine;

import commands.*;
import model.GameState;
import view.GameUI;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GameLoader loader = new GameLoader();
        Optional<GameState> OptionalState = loader.load("recourses/world.json");
        Optional<GrammarConfig> grammarOpt = loader.loadGrammar("recourses/grammar.json");

        Parser parser;

        if (OptionalState.isEmpty()) return;
        GameState state = OptionalState.get();
        state.setupGame();
        state.initializeNPCsInRooms();

        GameUI ui = new GameUI("recourses/messages.json");
        state.setUI(ui);

        if (grammarOpt.isPresent()) {
            parser = new Parser(grammarOpt.get());
            parser.registerCommand("go", arg -> new GoCommand(arg));
            parser.registerCommand("take", arg -> new TakeCommand(arg));
            parser.registerCommand("look", arg -> new LookCommand(arg));
            parser.registerCommand("inventory", arg -> new InventoryCommand());
            parser.registerCommand("open", arg -> new OpenCommand(arg));
            parser.registerCommand("save", arg -> new SaveCommand());
            parser.registerCommand("load", arg -> new LoadCommand(parser));
            parser.registerCommand("quit", arg -> new QuitCommand());
            parser.registerCommand("undo", arg -> new UndoCommand());
            parser.registerCommand("redo", arg -> new RedoCommand());
            parser.registerCommand("help", arg -> new HelpCommand());
            parser.registerCommand("talk", arg -> new TalkCommand(arg));
        }
        else{
            ui.print("no_grammar");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        ui.printRaw("Welcome to " + state.gameTitle + "!");
        ui.printRaw(state.introLore);
        ui.print("main_help_hint");

        new LookCommand("").execute(state);

        while (true) {
            ui.printInline("prompt");
            String input = scanner.nextLine().toLowerCase();
            input = input.replaceAll("\\b(the|a|an|into)\\b", "");
            input = input.replaceAll("\\s+", " ").trim();

            Optional<Command> cmdOpt = parser.parseInput(input);

            if (cmdOpt.isPresent()) {
                Command command = cmdOpt.get();

                if (!(command instanceof SaveCommand) &&
                        !(command instanceof LoadCommand) &&
                        !(command instanceof HelpCommand) &&
                        !(command instanceof QuitCommand) &&
                        !(command instanceof UndoCommand) &&
                        !(command instanceof RedoCommand)) {

                    state.addHistory(input);
                    state.saveShot();
                }
                command.execute(state);
            } else {
                ui.print("unknown_command", input);
            }
        }
    }
}

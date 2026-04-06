import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Φόρτωση του κόσμου
        GameLoader loader = new GameLoader();
        Optional<GameState> OptionalState = loader.load("world.json");
        Optional<GrammarConfig> grammarOpt = loader.loadGrammar("grammar.json");

        Parser parser;

        if (OptionalState.isEmpty()) return;
        GameState state = OptionalState.get();

        if (grammarOpt.isPresent()) {
            parser = new Parser(grammarOpt.get());
        }
        else{
            System.out.println("No grammar found");
            return;
        }
        Scanner scanner = new Scanner(System.in);


        System.out.println("Καλώς ήρθες, " + state.player.name + "!");
        System.out.println("[" + state.getCurrentRoom().name + "]");
        System.out.println(state.getCurrentRoom().description);


        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();

            Optional<Command> cmdOpt = parser.parseInput(input);

            if (cmdOpt.isPresent()) {
                cmdOpt.get().execute(state);
            } else {
                System.out.println("Δεν καταλαβαίνω. Δοκίμασε 'go north', 'go south' ή 'quit'.");
            }
        }
    }
}
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GameLoader loader = new GameLoader();
        Optional<GameState> OptionalState = loader.load("resourses/world.json");
        Optional<GrammarConfig> grammarOpt = loader.loadGrammar("resourses/grammar.json");

        Parser parser;

        if (OptionalState.isEmpty()) return;
        GameState state = OptionalState.get();
        state.setupGame();

        if (grammarOpt.isPresent()) {
            parser = new Parser(grammarOpt.get());
        }
        else{
            System.out.println("No grammar found");
            return;
        }
        Scanner scanner = new Scanner(System.in);


        System.out.println("Welcome! Whats your name?" );
        String name = scanner.nextLine();
        state.getPlayer().setName(name);

        System.out.println("Welcome " + name + "!");
        System.out.println("\n--- ΕΝΑΡΞΗ ΠΕΡΙΠΕΤΕΙΑΣ ---"); //todo beginning message..!
        new LookCommand("").execute(state);

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();

            Optional<Command> cmdOpt = parser.parseInput(input);

            if (cmdOpt.isPresent()) {
                cmdOpt.get().execute(state);
            } else {
                System.out.println("Δεν καταλαβαίνω. Δοκίμασε 'go north', 'go south'.");
            }
        }
    }
}
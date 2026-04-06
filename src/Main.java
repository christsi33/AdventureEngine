import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Φόρτωση του κόσμου
        GameLoader loader = new GameLoader();
        Optional<GameState> OptionalState = loader.load("world.json");

        if (OptionalState.isEmpty()) return;
        GameState state = OptionalState.get();

        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);


        System.out.println("Καλώς ήρθες, " + state.player.name + "!");
        System.out.println("[" + state.getCurrentRoom().name + "]");
        System.out.println(state.getCurrentRoom().description);


        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();

            if (input.equals("quit")) {
                System.out.println("Αντίο!");
                break;
            }

            Optional<Command> cmd = parser.parseInput(input);

            if (cmd.isPresent()) {
                cmd.get().execute(state);
            } else {
                System.out.println("Δεν καταλαβαίνω. Δοκίμασε 'go north', 'go south' ή 'quit'.");
            }
        }
        scanner.close();
    }
}
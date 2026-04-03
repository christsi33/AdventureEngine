import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Φόρτωση του κόσμου
        GameLoader loader = new GameLoader();
        GameState state = loader.load("world.json");

        if (state == null) return;


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

            Command cmd = parser.parseInput(input);

            if (cmd != null) {
                cmd.execute(state);
            } else {
                System.out.println("Δεν καταλαβαίνω. Δοκίμασε 'go north', 'go south' ή 'quit'.");
            }
        }
        scanner.close();
    }
}
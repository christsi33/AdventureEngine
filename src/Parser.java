import java.util.Optional;

public class Parser {
    public Optional<Command> parseInput(String input) {
        input = input.trim().toLowerCase();

        // Αν η πρόταση ξεκινάει με "go "
        if (input.startsWith("go ")) {
            String direction = input.substring(3); // Παίρνει τη λέξη μετά το "go "
            return Optional.of(new GoCommand(direction));
        }

        return Optional.empty();
    }
}
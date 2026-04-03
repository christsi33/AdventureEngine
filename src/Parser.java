public class Parser {
    public Command parseInput(String input) {
        input = input.trim().toLowerCase();

        // Αν η πρόταση ξεκινάει με "go "
        if (input.startsWith("go ")) {
            String direction = input.substring(3); // Παίρνει τη λέξη μετά το "go "
            return new GoCommand(direction);
        }

        return null; // Αν γράψει κάτι άλλο, δεν το καταλαβαίνει
    }
}
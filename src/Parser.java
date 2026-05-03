import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Parser {
    private Map<String, String> replacements;

    private Map<String, Function<String, Command>> dictionary;

    public Parser(GrammarConfig config){
        this.replacements = config.replacements;
        this.dictionary = new HashMap<>();

        if(config.grammar != null){
            config.grammar.forEach((mainVerb, synonyms) -> {
                dictionary.put(mainVerb, arg -> createCommand(mainVerb, arg));

                if(synonyms != null){
                    for(String s : synonyms){
                        dictionary.put(s, arg -> createCommand(mainVerb, arg));
                    }
                }
            });
        }
    }

    public Optional<Command> parseInput(String input) {
        if(input == null || input.trim().isEmpty()){
            return Optional.empty();
        }
        input = input.trim().toLowerCase();

        if (replacements != null) {
            for(Map.Entry<String, String> entry: replacements.entrySet()){
                input = input.replace(entry.getKey(), entry.getValue());
            }
        }

        String[] parts = input.split("\\s+", 2);
        String verb = parts[0];
        String argument = (parts.length > 1) ? parts[1] : "";

        if (dictionary.containsKey(verb)) {
            Command cmd = dictionary.get(verb).apply(argument);
            if (cmd != null) {
                return Optional.of(cmd);
            }
        }
        return Optional.empty();
    }

    private Command createCommand(String action, String arg) {
        switch (action) {
            case "go":
                return new GoCommand(arg);
            case "take":
                return new TakeCommand(arg);
            case "inventory":
                return new InventoryCommand();
            case "look":
                return new LookCommand(arg);
            default:
                return null;
        }
    }
}
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Parser {
    private Map<String, String> replacements;

    private Map<String, Function<String, Command>> dictionary;

    private GrammarConfig config;

    public Parser(GrammarConfig config){
       this.replacements = config.replacements;
       this.dictionary = new HashMap<>();
       this.config = config;
    }

    public void registerCommand(String mainVerb, Function<String, Command> creator){
        dictionary.put(mainVerb, creator);

        if(config.grammar != null && config.grammar.containsKey(mainVerb)){
            List<String> synonyms = config.grammar.get(mainVerb);
            if(synonyms != null){
                for (String s : synonyms) {
                    dictionary.put(s, creator);
                }
            }
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
}
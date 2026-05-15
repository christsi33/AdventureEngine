import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.Writer;

public class SaveCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();

        try(Writer writer = new FileWriter("recourses/savegame.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(state, writer);

            ui.printRaw("Game Saved Successfully!");
        }
        catch (Exception e){
            ui.printRaw("Failed to save game: " + e.getMessage());
        }
    }
}

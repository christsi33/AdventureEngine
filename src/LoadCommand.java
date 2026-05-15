import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;

public class LoadCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();

        try (Reader reader = new FileReader("recourses/savegame.json")) {
            Gson gson = new Gson();
            GameState loadedState = gson.fromJson(reader, GameState.class);

            state.restoreFrom(loadedState);

            ui.printRaw("Game loaded successfully! Resuming...");
            new LookCommand("").execute(state);

        } catch (Exception e) {
            ui.printRaw("No save file found. Type 'save' to create one first.");
        }
    }
}
import com.google.gson.Gson;
import java.io.FileReader;
import java.util.Optional;


public class GameLoader {
    public Optional<GameState> load(String filename) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filename);
            GameState state = gson.fromJson(reader, GameState.class);
            state.currentRoomId = state.initialRoomId;
            return Optional.of(state);
        } catch (Exception e) {
            System.out.println("Σφάλμα: Δεν βρέθηκε το αρχείο " + filename);
            return Optional.empty();
        }
    }
}

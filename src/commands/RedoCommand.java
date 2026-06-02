package commands;
import view.GameUI;
import model.GameState;

public class RedoCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();
        if (state.redo()) {
            ui.print("redo_success");
            new LookCommand("").execute(state);
        } else {
            ui.print("redo_fail");
        }
    }
}
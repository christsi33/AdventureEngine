package commands;
import view.GameUI;
import model.GameState;

public class UndoCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();
        if (state.undo()) {
            ui.print("undo_success");
            new LookCommand("").execute(state); 
        } else {
            ui.print("undo_fail");
        }
    }
}
public class UndoCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();
        if (state.undo()) {
            ui.printRaw("Time rewinds... (Undo successful)");
            new LookCommand("").execute(state); 
        } else {
            ui.printRaw("There is nothing to undo.");
        }
    }
}
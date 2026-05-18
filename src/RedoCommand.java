public class RedoCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();
        if (state.redo()) {
            ui.printRaw("Time moves forward... (Redo successful)");
            new LookCommand("").execute(state);
        } else {
            ui.printRaw("There is nothing to redo.");
        }
    }
}
package commands;

import model.GameState;

public class HelpCommand implements Command{
    @Override
    public void execute(GameState state) {
        state.getUI().print("help_menu");
    }
}

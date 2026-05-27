package commands;

import model.GameState;

public class QuitCommand implements Command {
    @Override
    public void execute(GameState state){
        state.getUI().printRaw("Thanks for playing! Goodbye.");
        System.exit(0);
    }
}

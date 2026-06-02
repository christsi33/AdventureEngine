package commands;

import model.GameState;

public class QuitCommand implements Command {
    @Override
    public void execute(GameState state){
        state.getUI().print("quit_message");
        System.exit(0);
    }
}

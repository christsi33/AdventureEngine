package commands;

import model.GameState;

public interface Command {
    void execute(GameState state);
}

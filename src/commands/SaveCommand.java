package commands;
import view.GameUI;
import model.GameState;

import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveCommand implements Command {
    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();

        try(PrintWriter writer = new PrintWriter(new FileWriter("recourses/savegame.txt"))){
            for(String cmd : state.getHistory()){
                writer.println(cmd);
            }
            ui.printRaw("Game Saved Successfully!");
        }
        catch (Exception e){
            ui.printRaw("Failed to save game: " + e.getMessage());
        }
    }
}

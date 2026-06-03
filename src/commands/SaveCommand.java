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
            ui.print("save_success");
        }
        catch (Exception e){
            ui.print("save_fail", e.getMessage());
        }
    }
}

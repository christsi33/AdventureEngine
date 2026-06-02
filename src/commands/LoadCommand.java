package commands;
import view.GameUI;
import engine.GameLoader;
import engine.Parser;
import model.GameState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoadCommand implements Command {
   private Parser parser;
   public LoadCommand(Parser parser) {
      this.parser = parser;
   }

   @Override
    public void execute(GameState state){
       GameUI ui = state.getUI();
       List<String> savedCommands = new ArrayList<>();

       try(BufferedReader reader = new BufferedReader(new FileReader("recourses/savegame.txt"))){
           String line;
           while ((line = reader.readLine()) != null){
               if(!line.trim().isEmpty()){
                   savedCommands.add(line);
               }
           }
       }
       catch (Exception e){
           ui.print("load_no_file");
           return;
       }
       try {
            GameLoader loader = new GameLoader();
            Optional<GameState> optionalState = loader.load("recourses/world.json");

            if(optionalState.isPresent()){
                GameState initialState = optionalState.get();
                initialState.setUI(ui);

                state.restoreFrom(initialState);
                state.getHistory().clear();
            }
           else{
                ui.print("load_fail");
               return;
            }
       }
       catch (Exception e){
           ui.print("load_reset_fail", e.getMessage());
           return;
       }

       ui.setMuted(true);
       for(String cmdStr: savedCommands){
           Optional<Command> cmdOpt = parser.parseInput(cmdStr);
           if(cmdOpt.isPresent()){
               Command command = cmdOpt.get();

               state.addHistory(cmdStr);
               command.execute(state);
           }
       }
       ui.setMuted(false);
       ui.print("load_success");

       new LookCommand("").execute(state);
   }
}
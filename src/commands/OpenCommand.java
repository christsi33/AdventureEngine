package commands;
import view.GameUI;
import model.GameState;
import model.Player;
import model.Room;

public class OpenCommand implements Command {
    private String target;
    private String tool;

    public OpenCommand(String argument) {
        if(argument == null) {
            argument = "";
        }
        else {
            argument = argument.toLowerCase().trim();
        }

        if (argument.contains("on")){
            String[] parts=argument.split(" on ", 2);
            this.target = parts[0].trim();
            this.tool = parts[1].trim();
        }
        else if (argument.contains(" with ")){
            String[] parts=argument.split(" with ", 2);
            this.target = parts[0].trim();
            this.tool = parts[1].trim();
        }
        else {
            this.target = argument;
            this.tool = "";
        }
    }

    @Override
    public void execute(GameState state){
        GameUI ui = state.getUI();

        if(target.isEmpty()){
            ui.print("open_what");
            return;
        }

        Room currentRoom = state.getCurrentRoom();
        Player player = state.getPlayer();

        if (currentRoom.locations != null) {
            for (Room.Location loc : currentRoom.locations) {
                String cleanTarget = target.replace("the ", "");

                if (loc.id.equalsIgnoreCase(cleanTarget) || loc.name.toLowerCase().contains(cleanTarget)) {

                    if (loc.locked_with != null && !loc.locked_with.isEmpty()) {

                        if (!tool.isEmpty() && !player.hasItem(tool)) {
                            ui.print("open_missing_tool", tool);
                            return;
                        }

                        boolean hasAllKeys = true;
                        for (String requiredKeyId : loc.locked_with) {
                            if (!player.hasItem(requiredKeyId)) {
                                hasAllKeys = false;
                                break;
                            }
                        }

                        if (hasAllKeys) {
                            if (loc.unlock_message != null) {
                                ui.printRaw(loc.unlock_message);
                            } else {
                                ui.print("open_success_generic", loc.name);
                            }

                            if (loc.is_win_condition) {
                                ui.print("open_gate_win");
                                System.exit(0);
                            }
                        } else {
                            ui.print("open_gate_fail");
                        }
                    } else {
                        ui.print("open_not_locked", loc.name);
                    }
                    return;
                }
            }
        }
        ui.print("open_cant", target);
    }
}

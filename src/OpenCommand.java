import java.util.List;

public class OpenCommand implements Command {
    private String target;
    private String tool;

    public OpenCommand(String argument) {
        this.target = (target == null) ? "" : target.toLowerCase().replace("_","").trim();

        if(argument.contains("on")){
            String[] parts = argument.split("on", 2);
            this.tool = parts[0].trim();
            this.target = parts[1].trim();
        }
        else if(argument.contains("with")){
            String[] parts = argument.split("with", 2);
            this.tool = parts[0].trim();
            this.target = parts[1].trim();
        }
        else{
            this.tool = argument;
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
                            ui.printRaw("You don't have the '" + tool + "' in your inventory.");
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
                            ui.printRaw(loc.unlock_message != null ? loc.unlock_message : "You successfully opened the " + loc.name + "!");

                            if (loc.is_win_condition) {
                                ui.print("open_gate_win");
                                System.exit(0);
                            }
                        } else {
                            ui.print("open_gate_fail");
                        }
                    } else {
                        ui.printRaw("The " + loc.name + " doesn't seem to be locked.");
                    }
                    return;
                }
            }
        }
        ui.print("open_cant", target);
    }
}

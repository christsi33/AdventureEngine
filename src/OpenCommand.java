public class OpenCommand implements Command {
    private String target;

    public OpenCommand(String target) {
        this.target = (target == null) ? "" : target.toLowerCase().replace("_","").trim();
    }

    @Override
    public void execute(GameState state){
        GameUI ui = state.getUI();

        if(target.isEmpty()){
            ui.print("open_what");
            return;
        }

        Room currentRoom = state.getCurrentRoom();

        if (target.contains("gate") || target.contains("door")) {

            if (currentRoom.name.equalsIgnoreCase("Emergency Exit")) {

                Player player = state.getPlayer();

                boolean hasRedKey = player.hasItem("red_key");
                boolean hasBlueCard = player.hasItem("blue_keycard");

                if (hasRedKey && hasBlueCard) {
                    ui.print("open_gate_success_1");
                    ui.print("open_gate_success_2");
                    ui.print("open_gate_success_3");
                    ui.print("open_gate_win");

                    System.exit(0);
                } else {
                    ui.print("open_gate_fail");
                }
            } else {
                ui.print("open_no_gate");
            }
            return;
        }
        ui.print("open_cant", target);
    }
}

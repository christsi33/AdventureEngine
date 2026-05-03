public class GoCommand implements Command {
    private final String direction;

    public GoCommand(String direction) {
        if (direction == null) {
            this.direction = "";
        } else {
            this.direction = direction.trim();
        }
    }

    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();

        if (direction.isEmpty()) {
            ui.print("go_where");
            return;
        }

        Room currentRoom = state.getCurrentRoom();

        if (currentRoom.exits != null && currentRoom.exits.containsKey(direction)) {
            String nextRoomId = currentRoom.exits.get(direction);

            Room nextRoom = state.rooms.get(nextRoomId);

            if(nextRoom != null && nextRoom.locations != null) {
                for(Room.Location loc : nextRoom.locations) {
                    if(loc.trap != null && "enter".equalsIgnoreCase(loc.trap.trigger)){
                        if (!state.getPlayer().hasItem(loc.trap.required_item)) {
                            ui.print("trap_activated");
                            ui.printRaw(loc.trap.game_over_message); // Αυτό έρχεται από το world.json
                            ui.print("game_over");
                            System.exit(0);
                        } else {
                            ui.print("trap_safe_enter", loc.trap.required_item);
                        }
                    }
                }
            }
            state.currentRoomId = nextRoomId;
            new LookCommand("").execute(state);
        } else {
            ui.print("go_no_path");
        }
    }
}
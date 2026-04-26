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
        if (direction.isEmpty()) {
            System.out.println("Where do you want to go?");
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
                            System.out.println("\n!!! TRAP ACTIVATED !!!");
                            System.out.println(loc.trap.game_over_message);
                            System.out.println("\n--- GAME OVER ---");
                            System.exit(0);
                        } else {
                            System.out.println("\n[Safe: Your " + loc.trap.required_item + " prevented a trap!]");
                        }
                    }
                }
            }
            state.currentRoomId = nextRoomId;
            new LookCommand("").execute(state);
        } else {
            System.out.println("There is no path in that direction.");
        }
    }
}
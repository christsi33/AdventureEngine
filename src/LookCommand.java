public class LookCommand  implements Command {

    private String target;

    public LookCommand(String target) {
        if (target != null && target.startsWith("at ")) {
            this.target = target.substring(3).trim();
        } else {
            this.target = (target == null) ? "" : target.trim();
        }
    }

    @Override
    public void execute(GameState state){
        Room currentRoom = state.getCurrentRoom();

        if (target.isEmpty()) {
            System.out.println("\n=== [" + currentRoom.name + "] ===");
            System.out.println(currentRoom.description);

            if (currentRoom.locations != null && !currentRoom.locations.isEmpty()) {
                System.out.println("\nIn the space you can distinguish the following points: ");
                for (Room.Location loc : currentRoom.locations) {
                    System.out.println("- " + loc.name + " (id: " + loc.id + ")");
                }
            }

            if (currentRoom.exits != null && !currentRoom.exits.isEmpty()) {
                System.out.print("\nExits: ");
                for (String direction : currentRoom.exits.keySet()) {
                    System.out.print("[" + direction + "] ");
                }
                System.out.println();
            }
            return;
        }

        if (currentRoom.locations != null) {
            for (Room.Location loc : currentRoom.locations) {
                if (loc.id.equalsIgnoreCase(target) || loc.name.equalsIgnoreCase(target)) {

                    System.out.println("\nLooking for: " + loc.name + " ---");
                    System.out.println(loc.description);

                    if (loc.items != null && !loc.items.isEmpty()) {
                        System.out.println("You found:");
                        for (Item item : loc.items) {
                            System.out.println("- " + item.getName());
                        }
                    } else {
                        System.out.println("There is nothing useful here.");
                    }
                    return;
                }
            }
        }

        System.out.println("There is nothing " + target + "around there.");
    }
}


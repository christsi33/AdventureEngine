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
        GameUI ui = state.getUI();

        if (target.isEmpty()) {
           ui.print("look_room_title", currentRoom.name);
           ui.printRaw(currentRoom.description);

            if (currentRoom.locations != null && !currentRoom.locations.isEmpty()) {
                ui.print("look_points_of_interest");
                for (Room.Location loc : currentRoom.locations) {
                    ui.print("look_point_item", loc.name, loc.id);
                }
            }

            if (currentRoom.exits != null && !currentRoom.exits.isEmpty()) {
                ui.print("look_exits");
                for (String direction : currentRoom.exits.keySet()) {
                    ui.print("look_exit_item", direction);
                }
                ui.printRaw("");
            }
            return;
        }

        if (currentRoom.locations != null) {
            for (Room.Location loc : currentRoom.locations) {
                String cleanTarget = target.toLowerCase().replace("_", " ");
                String cleanId = loc.id.toLowerCase().replace("_", " ");
                String cleanName = loc.name.toLowerCase();

                if (cleanId.contains(cleanTarget) || cleanName.contains(cleanTarget)){

                    ui.print("look_examining", loc.name);

                    if (loc.trap != null && "interact".equalsIgnoreCase(loc.trap.trigger)) {
                        if (!state.getPlayer().hasItem(loc.trap.required_item)) {
                            ui.print("trap_activated");
                            ui.printRaw(loc.trap.game_over_message);
                            ui.print("game_over");
                            System.exit(0);
                        } else {
                            ui.print("trap_safe_interact", loc.trap.required_item);
                        }
                    }

                    ui.printRaw(loc.description);

                    if (loc.items != null && !loc.items.isEmpty()) {
                        ui.print("look_items_discovered");
                        for (Item item : loc.items) {
                            ui.print("look_item_detail", item.getName(), item.getId());
                        }
                    } else {
                        ui.print("look_nothing_interest");
                    }
                    return;
                }
            }
        }

        ui.print("look_not_found", target);
    }
}


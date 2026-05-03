public class TakeCommand implements Command {
    private String targetItem;

    public TakeCommand(String targetItem) {
        this.targetItem = targetItem;
    }

    @Override
    public void execute(GameState state){
        GameUI ui = state.getUI();

        if(targetItem == null || targetItem.isEmpty()){
            ui.print("take_what");
            return;
        }

        Room currentRoom = state.getCurrentRoom();
        Player player = state.getPlayer();

        if(currentRoom.locations != null){
            for(Room.Location loc : currentRoom.locations){
                if(loc.items != null){
                    for(int i = 0; i < loc.items.size(); i++){
                        Item item = loc.items.get(i);
                        String cleanTarget = targetItem.toLowerCase().replace("_", " ");
                        String cleanId = item.getId().toLowerCase().replace("_", " ");
                        String cleanName = item.getName().toLowerCase();
                        if (cleanId.contains(cleanTarget) || cleanName.contains(cleanTarget)) {
                            loc.items.remove(item);
                            player.addItem(item);
                            ui.print("take_success", item.getName());
                            return;
                        }
                    }
                }
            }
        }
        ui.print("take_not_found", targetItem);
    }
}

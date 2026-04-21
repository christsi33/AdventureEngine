public class TakeCommand implements Command {
    private String targetItem;

    public TakeCommand(String targetItem) {
        this.targetItem = targetItem;
    }

    @Override
    public void execute(GameState state){
        if(targetItem == null || targetItem.isEmpty()){
            System.out.println("You need to enter a target item");
            return;
        }

        Room currentRoom = state.getCurrentRoom();
        Player player = state.getPlayer();

        if(currentRoom.locations != null){
            for(Room.Location loc : currentRoom.locations){
                if(loc.items != null){
                    for(int i = 0; i < loc.items.size(); i++){
                        Item item = loc.items.get(i);
                        if(item.getId().equalsIgnoreCase(targetItem) || item.getName().equalsIgnoreCase(targetItem)){
                            loc.items.remove(item);
                            player.addItem(item);
                            System.out.println("You got [" + item.getName() + "] at: " + loc.name);
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("I can't see any "+ targetItem + " around you");
    }
}

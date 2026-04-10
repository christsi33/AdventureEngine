public class InventoryCommand implements Command {
    @Override
    public void execute(GameState state) {
        Player player = state.getPlayer();

        if (player != null) {
            player.showInventory();
        }
        else{
            System.out.println("You are not a player");
        }
    }
}

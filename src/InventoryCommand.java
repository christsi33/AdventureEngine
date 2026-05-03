import java.util.List;

public class InventoryCommand implements Command {
    @Override
    public void execute(GameState state) {
        Player player = state.getPlayer();
        GameUI ui = state.getUI();

        if (player != null) {
            List<Item> inv = player.getInventory();

            if(inv.isEmpty()){
                ui.print("inventory_empty");
            }
            else{
                ui.print("inventory_title");
                for(Item item : inv){
                    ui.print("inventory_item", item.getName(), item.getDescription());
                }
            }
        }
        else{
            ui.print("inventory_not_player");
        }
    }
}

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Item> inventory;

    public Player(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing on you.");
        }
        else {
            System.out.println("Yoy have :");
            for(Item item : inventory) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        }
    }

    public boolean hasItem(String itemId){
        for (Item item : inventory) {
            if (item.getId().equalsIgnoreCase(itemId)) {
                return true;
            }
        }
        return false;
    }

}
import java.util.ArrayList;
import java.util.List;

public class Player {
    public String name = "Hero";
    public int maxInventorySize = 10;

    // Το σακίδιο τώρα δέχεται αντικείμενα (Items)
    public List<Item> inventory = new ArrayList<>();
}
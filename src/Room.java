import java.util.List;
import java.util.Map;

public class Room {
    public String name;
    public String description;

    public Map<String, String> exits;

    public List<Location> locations;

    public static class Location {
        public String id;
        public String name;
        public String description;
        public Trap trap;
        public List<Item> items;
    }

    public static class Trap {
        public String trigger;
        public String required_item;
        public String game_over_message;
    }
}

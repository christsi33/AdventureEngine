package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Room {
    public String name;
    public String description;

    private transient List<NPC> npcs = new java.util.ArrayList<>();

    public Map<String, String> exits;

    public List<Location> locations;

    public static class Location {
        public String id;
        public String name;
        public String description;
        public List<String> locked_with;
        public boolean is_win_condition;
        public String unlock_message;
        public Trap trap;
        public List<Item> items;
    }

    public static class Trap {
        public String trigger;
        public String required_item;
        public String game_over_message;
    }

    public List<NPC> getNpcs() {
        if (npcs == null) npcs = new ArrayList<>();
        return npcs;
    }

    public void addNPC(NPC npc) {
        getNpcs().add(npc);
    }

    public void removeNPC(NPC npc) {
        getNpcs().remove(npc);
    }
}

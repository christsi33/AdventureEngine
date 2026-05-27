package model;

import java.util.Map;

public class NPC {
    private String id;
    private String name;
    private String currentRoom;
    private String currentState;
    private Map<String, NPCState> states;

    public String getId() { return id; }
    public String getName() { return name; }

    public String getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(String currentRoom) { this.currentRoom = currentRoom; }

    public String getCurrentState() { return currentState; }
    public void setCurrentState(String currentState) { this.currentState = currentState; }

    public NPCState getActiveState() {
        if (states != null && states.containsKey(currentState)) {
            return states.get(currentState);
        }
        return null;
    }
}
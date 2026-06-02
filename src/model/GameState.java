package model;
import view.GameUI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import com.google.gson.Gson;

public class GameState {
    public String gameTitle;
    public String introLore;
    public String initialRoomId;
    public Map<String, Room> rooms;

    public String currentRoomId;
    public Player player = new Player();

    private transient GameUI ui;
    private List<NPC> npcs;

    private transient Stack<String> undoStack = new Stack<>();
    private transient Stack<String> redoStack = new Stack<>();

    private List<String> commandHistory = new ArrayList<>();

    public void addHistory(String cmd){
        this.commandHistory.add(cmd);
    }

    public List<String> getHistory(){
        return this.commandHistory;
    }

    public void setupGame() {
        this.currentRoomId = this.initialRoomId;
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomId);
    }

    public Player getPlayer() {
        return player;
    }

    public GameUI getUI() {
        return ui;
    }

    public void setUI(GameUI ui) {
        this.ui = ui;
    }

    public void restoreFrom(GameState savedState) {
        this.currentRoomId = savedState.currentRoomId;
        this.rooms = savedState.rooms;
        this.player = savedState.player;
    }

    public void saveShot(){
        if(undoStack == null){undoStack = new Stack<>();}
        if(redoStack == null){redoStack = new Stack<>();}

        Gson gson = new Gson();
        String snapshot = gson.toJson(this);
        this.undoStack.push(snapshot);
        this.redoStack.clear();
    }

    public boolean undo(){
        if(undoStack == null || undoStack.isEmpty()){return false;}

        Gson gson = new Gson();
        redoStack.push(gson.toJson(this));

        String previousStateStr = undoStack.pop();
        GameState previousState = gson.fromJson(previousStateStr, GameState.class);
        this.restoreFrom(previousState);
        return true;
    }

    public boolean redo(){
        if(redoStack == null || redoStack.isEmpty()){return false;}

        Gson gson = new Gson();
        undoStack.push(gson.toJson(this));

        String nextStateStr = redoStack.pop();
        GameState nextState = gson.fromJson(nextStateStr, GameState.class);
        this.restoreFrom(nextState);
        return true;
    }

    public void initializeNPCsInRooms() {
        if (npcs != null && rooms != null) {
            for (NPC npc : npcs) {
                Room room = rooms.get(npc.getCurrentRoom());
                if (room != null) {
                    room.addNPC(npc);
                }
            }
        }
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }
}

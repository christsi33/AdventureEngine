import java.util.Map;
import java.util.Stack;

import com.google.gson.Gson;

public class GameState {
    public String initialRoomId;
    public Map<String, Room> rooms;

    public String currentRoomId;
    public Player player = new Player();

    private transient GameUI ui;

    private transient Stack<String> undoStack = new Stack<>();
    private transient Stack<String> redoStack = new Stack<>();

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
        String json = gson.toJson(this);
        undoStack.push(snapshot);
        redoStack.clear();
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
}

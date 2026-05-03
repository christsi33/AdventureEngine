import java.util.Map;

public class GameState {
    public String initialRoomId;
    public Map<String, Room> rooms;

    public String currentRoomId;
    public Player player = new Player();

    private transient GameUI ui;

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
}

import java.util.Map;

public class GameState {
    public String initialRoomId;
    public Map<String, Room> rooms;

    public String currentRoomId;
    public Player player = new Player(); // Ο παίκτης μας

    public Room getCurrentRoom() {
        return rooms.get(currentRoomId);
    }
}

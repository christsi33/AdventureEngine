public class GoCommand implements Command {
    private final String direction;

    public GoCommand(String direction) {
        this.direction = direction;
    }

    @Override
    public void execute(GameState state) {
        if (direction == null || direction.isEmpty()) {
            System.out.println("Προς τα πού θέλεις να πας; (π.χ. 'go north')");
            return;
        }

        Room currentRoom = state.getCurrentRoom();

        if (currentRoom.exits != null && currentRoom.exits.containsKey(direction)) {
            state.currentRoomId = currentRoom.exits.get(direction);
            Room newRoom = state.getCurrentRoom();
            System.out.println("\n[" + newRoom.name + "]");
            System.out.println(newRoom.description);
        } else {
            System.out.println("Δεν υπάρχει δρόμος προς τα εκεί.");
        }
    }
}
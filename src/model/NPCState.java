package model;

public class NPCState {
    private String dialogue;
    private String requiredItem;
    private String givenItem;
    private String nextState;
    private String moveToRoom;

    public String getDialogue() { return dialogue; }
    public String getRequiredItem() { return requiredItem; }
    public String getGivenItem() { return givenItem; }
    public String getNextState() { return nextState; }
    public String getMoveToRoom() { return moveToRoom; }
}
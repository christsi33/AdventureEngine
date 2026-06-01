package commands;
import model.*;
import view.GameUI;

public class TalkCommand implements Command {
    private String target;

    public TalkCommand(String target) {
        if (target != null && target.startsWith("to ")) {
            this.target = target.substring(3).trim();
        } else {
            this.target = (target == null) ? "" : target.trim();
        }
    }

    @Override
    public void execute(GameState state) {
        GameUI ui = state.getUI();

        if (target == null || target.trim().isEmpty()) {
            ui.print("talk_to_whom");
            return;
        }

        Room currentRoom = state.getCurrentRoom();
        NPC targetNPC = null;

        for (NPC npc : currentRoom.getNpcs()) {
            if (npc.getId().equals(target) || npc.getName().toLowerCase().contains(target.toLowerCase())) {
                targetNPC = npc;
                break;
            }
        }

        if (targetNPC == null) {
            ui.print("no_one_here");
            return;
        }

        NPCState activeState = targetNPC.getActiveState();
        if (activeState != null && activeState.getDialogue() != null) {
            ui.printRaw("[" + targetNPC.getName() + "]: \"" + activeState.getDialogue() + "\"");
            boolean conditionMet = false;
            boolean autoContinue = false;

            if (activeState.getRequiredItem() == null) {
                conditionMet = true;

            }
            else{
                if(state.getPlayer().hasItem(activeState.getRequiredItem())) {
                    ui.printRaw("\n[System]: You showed the " + activeState.getRequiredItem() + " to the mechanic.");
                    conditionMet = true;
                    autoContinue = true;
                }
            }

            if (conditionMet && activeState.getNextState() != null) {
                targetNPC.setCurrentState(activeState.getNextState());

                if (activeState.getGivenItem() != null) {
                    String itemId = activeState.getGivenItem();

                    String itemName = itemId.replace("_", " ");

                    Item newItem = new Item(itemId, itemName, "Acquired from the Vault Mechanic.");

                    state.getPlayer().addItem(newItem);

                    ui.printRaw("\n[System]: You received the " + newItem.getName() + "!");
                }

                if(activeState.getMoveToRoom() != null) {
                    currentRoom.removeNPC(targetNPC);
                    targetNPC.setCurrentRoom(activeState.getMoveToRoom());

                    Room nextRoom = state.getRooms().get(activeState.getMoveToRoom());
                    if(nextRoom != null) {
                        nextRoom.addNPC(targetNPC);
                    }
                    ui.printRaw("\n" + targetNPC.getName() + " walks away towards the " + activeState.getMoveToRoom() + "...");
                }

                if(autoContinue) {
                    NPCState newState = targetNPC.getActiveState();
                    if(newState != null && newState.getDialogue() != null) {
                        ui.printRaw("[" + targetNPC.getName() + "]: \"" + newState.getDialogue() + "\"");

                        if(newState.getGivenItem() != null) {
                            String itemId = newState.getGivenItem();
                            String itemName = itemId.replace("_", " ");
                            Item newItem = new Item(itemId, itemName, "Acquired from the Mechanic.");
                            state.getPlayer().addItem(newItem);
                            ui.printRaw("\n[System]: You received the " + newItem.getName() + "!");
                        }

                        if(newState.getNextState() != null) {
                            targetNPC.setCurrentState(newState.getNextState());
                        }

                        if(newState.getMoveToRoom() != null) {
                            currentRoom.removeNPC(targetNPC);
                            targetNPC.setCurrentRoom(activeState.getMoveToRoom());
                            Room nextRoom = state.getRooms().get(activeState.getMoveToRoom());
                            if(nextRoom != null) {
                                nextRoom.addNPC(targetNPC);
                            }
                            ui.printRaw("\n" + targetNPC.getName() + " walks away towards the " + newState.getMoveToRoom() + "...");
                        }
                    }
                }
            }
        }
        else{
            ui.printRaw(targetNPC.getName() + " has nothing to say right now.");
        }
    }
}
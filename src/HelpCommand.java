public class HelpCommand implements Command{
    @Override
    public void execute(GameState state) {
        state.getUI().printRaw(state.getUI().getMessage("help_menu"));
    }
}

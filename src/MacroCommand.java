import java.util.List;
import java.util.ArrayList;

public class MacroCommand implements ICommand {
    private final List<ICommand> commands = new ArrayList<>();

    public void addCommand(ICommand c) {
        commands.add(c);
    }
    
    @Override
    public void execute() {
        for (ICommand c : commands) {
            c.execute();
        }
    }

    @Override
    public void undo() {
        // 실행의 역순으로 되돌림
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    @Override
    public String name() {
        return "Macro(" + commands.size() + " cmds)";
    }
}

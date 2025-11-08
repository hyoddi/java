
import java.awt.Color;

public class Attack implements ICommand{
    private Player player;
    // private int prevCooldown;
    private boolean execute;

    public Attack(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.attack(); // 빨간 오라
        execute = true;
    }

    @Override
    public void undo() {
        if (!execute) return;

        // 빨간 오라를 파란 오라로 변경
        player.auraColor = Color.BLUE;
        player.auraTicks = 60;

        // 말풍선 표시 (선택)
        player.bubbleText = "Undo Attack";
        player.bubbleTicks = 60;
    }

    @Override
    public String name(){
        return "Attack!";
    }
    

    
}

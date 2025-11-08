public class Jump implements ICommand {
    private final Player player;
    private final int power;


    public Jump(Player player, int power) {
        this.player = player;
        this.power = power;
    }

    @Override
    public void execute() {
        player.jump(power);
    }

    @Override
    public void undo() {

        // 말풍선 표시
        player.bubbleText = "Undo Jump!";
        player.bubbleTicks = 80;
    }

    @Override
    public String name() {
        return "Jump";
    }
}

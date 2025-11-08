class Move implements ICommand { // Concrete Command : 실제 명령(행동) 구현
    private final Player player;
    private final int dx;

    public Move(Player player, int dx) {
        this.player = player;
        this.dx = dx;
    }

    @Override
    public void execute(){
        player.move(dx);
    }

    @Override
    public void undo(){
        player.move(-1*dx);
    }

    @Override
    public String name(){
        return "Move(" + dx + ")";
    }
}
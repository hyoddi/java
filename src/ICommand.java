public interface ICommand {
    public void execute();
    public void undo();
    
    public default String name(){
        return getClass().getSimpleName();
    };
}

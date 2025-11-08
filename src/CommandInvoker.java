
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class CommandInvoker{
    // 초기화
    private final Deque<ICommand> undoStack = new ArrayDeque<>();
    private final Deque<ICommand> redoStack = new ArrayDeque<>();

    
    public void execute(ICommand c) {

        if (!redoStack.isEmpty()){ // redoStack이 차있는데 새로운 걸 실행한다면
            redoStack.clear(); // 초기화
        }
        c.execute();
        undoStack.push(c); // 내가 한 행동 undoStack에 추가
    }

    public void undo(){ // 되돌리기 (Ctrl Z)
        if(!undoStack.isEmpty()){ // 전에 뭔가 행동이 있었다면
            ICommand undoCommand = undoStack.pop();
            undoCommand.undo(); // 그 행동 실행
            redoStack.push(undoCommand); // redoStack에 해당 행동 추가
        }
    }

    public void redo(){ // 되돌린거 되돌리기 (Ctrl Y)
        if (!redoStack.isEmpty()){
            ICommand redoCommand = redoStack.pop();
            redoCommand.execute();
            undoStack.push(redoCommand); // 돌아가기 취소한것도 다시 undoStack에 넣어주기
        }
    }


    // your code : 화면에서 직접 스택 상황을 확인해보자
    public Deque<ICommand> getUndoStackNames() {
        return undoStack;
    }

    public Deque<ICommand> getRedoStackNames() {
        return redoStack;
    }
   
}
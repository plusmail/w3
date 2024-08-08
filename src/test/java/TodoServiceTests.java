import kroryi.w3.todo.TodoService;
import kroryi.w3.todo.dto.TodoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TodoServiceTests {

    private TodoService todoService;

    @BeforeEach
    public void ready(){
        todoService = TodoService.INSTANCE;
    }

    @Test
    public void testRegister() throws Exception {
        TodoDTO todoDTO = TodoDTO.builder()
                .title("JDBC 테스트 Title")
                .dueDate(LocalDate.now())
                .build();
        todoService.register(todoDTO);

    }


}

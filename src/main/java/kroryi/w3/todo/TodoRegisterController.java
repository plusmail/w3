package kroryi.w3.todo;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kroryi.w3.todo.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@WebServlet(name="todoRegisterController", urlPatterns = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("할일 입력 화면 실행");

        HttpSession session = req.getSession();
        if(session.isNew()){
            log.info("세션을 발급 받아야 한다.");
            res.sendRedirect("/login");
            return;
        }

        req.getRequestDispatcher("/todo/register.jsp").forward(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("파라메터 Title 값은: {}", req.getParameter("title"));
        log.info("파라메터 dueDate값은: {}", req.getParameter("dueDate"));

        TodoDTO todoDTO = TodoDTO.builder()
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(req.getParameter("dueDate"), DATEFORMATTER))
                .build();
        try{
            todoService.register(todoDTO);

        }catch (Exception e) {
            e.printStackTrace();
        }

        //브라우즈(클라이언트)에 전달되는 http header 중에 Location 값을 /todo/list변경
        //브라우즈가 /todo/list로 다시 서버로 요청 하고 todoListController이 /todo/list.jsp를
        // 호출해서 사용자 화면에 목록을 보여준다.
        res.sendRedirect("/todo/list");
    }

}

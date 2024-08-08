package kroryi.w3.todo;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet(name="todoRemoveController", urlPatterns = "/todo/remove")
public class TodoRemoveController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Long tno = Long.parseLong(req.getParameter("tno"));
        log.info("삭제할 번호 tno-> {}", tno);
        try{
            todoService.remove(tno);

        }catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("삭제할 번호 읽기 실패");
        }

        res.sendRedirect("/todo/list");
    }


}

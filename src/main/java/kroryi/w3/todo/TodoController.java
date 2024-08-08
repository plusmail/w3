package kroryi.w3.todo;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kroryi.w3.todo.dto.TodoDTO;

import java.io.IOException;
import java.util.List;

@WebServlet("*.do")
public class TodoController  extends HttpServlet {

    private  TodoService todoService = TodoService.INSTANCE;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    public void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = requestURI.substring(contextPath.length());
        System.out.println("command = " + command);


        //응답 페이지
        String page = "";
        // 맨 밑 페이지 이동시 쓰는거.
        boolean isRedirect = false;

        if(command.equals("/todo/list.do"))
        {
            try{
                List<TodoDTO> dtoList = todoService.listAll();
                req.setAttribute("dtoList", dtoList);
                page = "list.jsp";

            }catch (Exception e){
                e.printStackTrace();
                throw new ServletException(e);
            }

        }

        if(command.equals("/todo/read.do"))
        {
            try{
                Long tno = Long.parseLong(req.getParameter("tno"));
                TodoDTO dto = todoService.get(tno);
                req.setAttribute("dto", dto);
                page = "read.jsp";

            }catch (Exception e){
                e.printStackTrace();
                throw new ServletException(e);
            }

        }

        // 페이지 이동.
        if(isRedirect) {
            res.sendRedirect(page);
        }
        else {
            req.getRequestDispatcher("/todo/"+page).forward(req, res);
        }


    }

}

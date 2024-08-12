package kroryi.w3.todo;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kroryi.w3.todo.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
//@WebServlet(name="todoReadController", urlPatterns = "/todo/read")
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("/todo/read 실행");
        try{
            Long tno = Long.parseLong(req.getParameter("tno"));
            TodoDTO dto = todoService.get(tno);
            req.setAttribute("dto", dto);

            Cookie viewTodoCookie = findCooke(req.getCookies(), "viewTodos");

            String todoListStr = viewTodoCookie.getValue();

            boolean exist = false;
            if(todoListStr != null && todoListStr.indexOf(tno+"-") >=0 ) {
                exist = true;
            }
            if(!exist){
                todoListStr += tno+"-";
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60);
                viewTodoCookie.setPath("/");
                //쿠키를 res에 추가해서
                res.addCookie(viewTodoCookie);
            }
            req.getRequestDispatcher("/todo/read.jsp").forward(req, res);

        }catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("읽기 에러 발생");
        }
    }

    private Cookie findCooke(Cookie[] cookies, String cookieName){
        Cookie targetCookie = null;
        if(cookies != null && cookies.length > 0 ){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    targetCookie = cookie;
                    break;
                }
            }
        }

        //위 코드에서 쿠키를 못찾으면 쿠키 생성
        if(targetCookie == null){
            // new Cookie가 쿠치 객체 생성
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60);

        }
        return targetCookie;
    }


}

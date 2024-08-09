package kroryi.w3.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet(name="logoutController", urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("로그아웃 실행");

        HttpSession session = request.getSession();
        session.removeAttribute("loginInfo");
        session.invalidate();
        response.sendRedirect("/");
    }
}

package kroryi.w3.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kroryi.w3.member.MemberService;
import kroryi.w3.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginController  extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        log.info("로그인 페이지 .. 열기");
        req.getRequestDispatcher("/login/login.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        // DB에서 회원정보 확인 절차 추가로 들어감
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(id, password);
            HttpSession session = req.getSession();
            if(id.equals(memberDTO.getMid()) && password.equals(memberDTO.getMpw())){
                session.setAttribute("loginInfo", memberDTO);
                res.sendRedirect("/todo/list");
            }else{
                throw new ServletException("아이디, 비번 확인 하세요.");
            }

        }catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("/login?result=error");
        }

    }

}

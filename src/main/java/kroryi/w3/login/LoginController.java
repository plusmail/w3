package kroryi.w3.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import kroryi.w3.member.MemberService;
import kroryi.w3.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.UUID;

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
        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");

        // DB에서 회원정보 확인 절차 추가로 들어감
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(id, password);
            if(rememberMe){
                String uuid = UUID.randomUUID().toString();
                MemberService.INSTANCE.updateUuid(id,uuid);
                memberDTO.setUuid(uuid);

                Cookie memberCookie = new Cookie("remember-me", uuid);
                memberCookie.setPath("/");
                memberCookie.setHttpOnly(true);
                memberCookie.setMaxAge(3600);
                res.addCookie(memberCookie);

            }

            HttpSession session = req.getSession();
            if(id.equals(memberDTO.getMid()) && password.equals(memberDTO.getMpw())){
                //세션
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

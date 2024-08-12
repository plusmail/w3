package kroryi.w3.login;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kroryi.w3.member.MemberService;
import kroryi.w3.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@Log4j2
@WebFilter(urlPatterns = "/todo/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        log.info("필터 실행...");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if(session.getAttribute("loginInfo") != null){
            chain.doFilter(req, res);
            return;
        }

        Cookie cookie = findCookie(request.getCookies(), "remember-me");
        if(cookie == null){
            response.sendRedirect("/login");
            return;
        }
        // 현재 여기는 remember_me ="xxxxxxx"
        String uuid = cookie.getValue();
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);
            log.info("쿠키값이 조회된 사용자 정보 : {}", memberDTO);
            if(memberDTO == null){
                throw new Exception("쿠키 값이 존재 하지 않습니다.");
            }

            session.setAttribute("loginInfo", memberDTO);
            chain.doFilter(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name){
        if(cookies == null || cookies.length == 0) return null;

        //람다식
        //Optinal은 NullPoint exception 값이 존재하지 않는 경우(Null)를 다루는 데 사용
        Optional<Cookie> result= Arrays.stream(cookies)
                .filter( ck-> ck.getName().equals(name))
                .findFirst();

        return result.isPresent() ? result.get() : null;
    }


}

package kroryi.w3.login;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
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
        if(session.getAttribute("loginInfo") == null){
            response.sendRedirect("/login");
            return;
        }
        chain.doFilter(request,response);
    }
}

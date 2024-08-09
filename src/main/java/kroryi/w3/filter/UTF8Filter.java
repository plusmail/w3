package kroryi.w3.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Log4j2
public class UTF8Filter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        log.info("UTF8 필터 실행");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(servletRequest, servletResponse);
    }
}

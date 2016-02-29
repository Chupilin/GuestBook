package by.chupilin.web.guestbook.log4j;

import org.apache.log4j.MDC;

import javax.servlet.*;
import java.io.IOException;

public class RemoteAddressLogger implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        MDC.put("RemoteAddr", request.getRemoteAddr());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.remove("RemoteAddr");
    }

}
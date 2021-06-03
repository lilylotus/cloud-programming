package cn.nihility.cloud.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("MyAuthenticationFailureHandler uri [" + request.getRequestURI() + "], ex [" + exception.getMessage() + "]");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.write("{\"code\": 401, \"msg\": \"" + request.getRequestURI() + " 登录失败 [" + exception.getMessage() + "]\"}");
        writer.flush();
    }
}

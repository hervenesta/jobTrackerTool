package io.cloudcomputing.jobtracker.security;

import com.google.gson.Gson;
import io.cloudcomputing.jobtracker.exceptions.InvalidLoginReponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // AuthenticationEntryPoint is an interface that provides implementation for entry point

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        // this shows what user see when he/she is not authenticated
        InvalidLoginReponse loginReponse = new InvalidLoginReponse();
        String jsonLoginResponse = new Gson().toJson(loginReponse);//convert loginResponse in json

        // message we sent to the user
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }
}

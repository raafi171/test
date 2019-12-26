package com.viva.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.viva.dto.AuthDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

    @Autowired HttpSession session; //autowiring session

    @Autowired UserService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        String userName = "";
        userName = ((UserDetailsImpl)authentication.getPrincipal()).getUsername();
        log.info("userName: " + userName);
        HttpSession session = request.getSession();
        session.setAttribute("userName", userName);
        AuthDto dto = service.getUserByUserName(userName);
        session.setAttribute("userId", dto.getId());
        response.sendRedirect("/home");
    }

}
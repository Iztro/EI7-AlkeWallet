package config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.TwoFactorAuthenticationService;
import service.UserService;

public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private TwoFactorAuthenticationService twoFactorAuthenticationService;

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if (user.is2faEnabled()) {
            String code = request.getParameter("code");
            if (twoFactorAuthenticationService.verifyCode(user.getSecret(), code)) {
                super.successfulAuthentication(request, response, chain, authResult);
            } else {
                response.sendRedirect("/login?error=2fa");
            }
        } else {
            super.successfulAuthentication(request, response, chain, authResult);
        }
    }
}
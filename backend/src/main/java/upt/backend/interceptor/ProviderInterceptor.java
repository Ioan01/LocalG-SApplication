package upt.backend.interceptor;

import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Component
public class ProviderInterceptor implements HandlerInterceptor {
    UserService userService;
    TokenService tokenService;

    public ProviderInterceptor(){
        userService = new UserService();
        tokenService = new TokenService();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!tokenService.hasAuthority(request.getHeader("Authorization"), "provider")){
            System.out.println("Only providers may add products");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only providers may make this request");
            //return false;
        }
        else{
            System.out.println("A provider made an add-product request");
            return true;
        }
    }
}
package upt.backend.interceptor;

import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        String token = request.getHeader("Authorization");
        ArrayList auth = (ArrayList) tokenService.extractJWTMap(token).get("auth");
        HashMap authmap = (HashMap)  auth.get(0);
        boolean ok = authmap.containsValue("provider");

        if(!ok){
            System.out.println("Only providers may add products");
            return false;
        }
        else{
            System.out.println("A provider made an add-product request");
            return true;
        }
    }
}
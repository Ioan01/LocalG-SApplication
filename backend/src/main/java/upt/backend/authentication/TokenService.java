package upt.backend.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

@Service
public class TokenService
{
    //@Value injection here makes TokenService unusable for the interceptors
    //made a constructor instead
    private String secretKey;
    private String issuer;

    @Autowired
    private UserService userService;


    public TokenService(){
        Properties prop = new Properties();
        try{
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            this.secretKey = prop.getProperty("security.jwt.secretKey");
            this.issuer = prop.getProperty("security.jwt.issuer");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                .setAudience(userDetails.getUsername())
                .setHeaderParam("auth",userDetails.getAuthorities())
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public HashMap<String,Object> extractJWTMap(String token)
    {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        HashMap hashMap = new HashMap<>();
        hashMap.putAll(claimsJws.getHeader());
        hashMap.putAll(claimsJws.getBody());

        return hashMap;
    }

    public HashMap getAuthorities(String token)
    {
        ArrayList auth = (ArrayList) extractJWTMap(token).get("auth");
        return (HashMap)  auth.get(0);
    }

    public boolean hasAuthority(String token, String authority)
    {
        System.out.println(getAuthorities(token) + " " + authority);
        System.out.println(getAuthorities(token).containsValue(authority));
        return getAuthorities(token).containsValue(authority);
    }

    public String getAudience(String token)
    {
        // user has valid token, but deleted their account
        return userService.getUser((String) extractJWTMap(token).get("aud")).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account deleted")).getUsername();
    }

    public String getIssuer(String token)
    {
        return (String) extractJWTMap(token).get("iss");
    }
}

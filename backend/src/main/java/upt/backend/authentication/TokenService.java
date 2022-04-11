package upt.backend.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TokenService
{
    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

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
    
    public static ArrayList<String> getAuthorities(HashMap jwtmap)
    {
        return (ArrayList<String>) jwtmap.get("auth");
    }

    public static String getAudience(HashMap jwtmap)
    {
        return (String) jwtmap.get("aud");
    }

    public static String getIssuer(HashMap jwtmap)
    {
        return (String) jwtmap.get("iss");
    }
}

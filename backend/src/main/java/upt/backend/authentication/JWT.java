package upt.backend.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JWT
{
    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder().setClaims(new HashMap<>())
                .setAudience(userDetails.getUsername())
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
}

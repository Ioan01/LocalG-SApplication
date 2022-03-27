package upt.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import upt.backend.services.FirebaseDatabase;

public class FirebaseAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private FirebaseDatabase database;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        database.getClass();
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return false;
    }
}

package upt.backend.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/register").permitAll();
    }
}

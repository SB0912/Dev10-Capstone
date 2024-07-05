package learn.blackjack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter){
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http, AuthenticationConfiguration config ) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers( HttpMethod.GET,"/api/blackjack/public").permitAll()
                .antMatchers( HttpMethod.GET, "/api/blackjack").authenticated()
                .antMatchers( HttpMethod.GET, "/api/blackjack/admin").permitAll()
                .antMatchers( HttpMethod.POST, "/api/security/login").permitAll()
                .antMatchers( HttpMethod.GET, "/api/blackjack/profilepage").authenticated()
                .antMatchers( HttpMethod.DELETE, "/api/blackjack/*").authenticated()
                .antMatchers( HttpMethod.POST, "/api/blackjack/createuser").permitAll()
                .antMatchers( HttpMethod.GET, "/api/blackjack/play/**").permitAll()
                .antMatchers( HttpMethod.PUT,"/api/blackjack/play/**").permitAll()
                .antMatchers("/**").denyAll()


                .and()
                .addFilter( new JwtRequestFilter(authenticationManager(config), converter ))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



//    @Bean
//    public JwtConverter getConverter(){
//        return new JwtConverter();
//    }

}

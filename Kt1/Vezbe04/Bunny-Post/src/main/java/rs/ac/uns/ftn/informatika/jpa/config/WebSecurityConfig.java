package rs.ac.uns.ftn.informatika.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rs.ac.uns.ftn.informatika.jpa.dto.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.jpa.security.auth.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.informatika.jpa.security.auth.TokenAuthenticationFilter;
import rs.ac.uns.ftn.informatika.jpa.service.CustomUserDetailService;
import rs.ac.uns.ftn.informatika.jpa.utils.TokenUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Autowired
    private TokenUtils tokenUtils;
    
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()  
                .antMatchers("/api/foo").permitAll()      
                .anyRequest().authenticated()
            .and()
            .cors().and()
            .csrf().disable();

        // Dodajte filter za autentifikaciju pre BasicAuthenticationFilter, ali obavezite samo za rute koje nisu javne
        http.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailService), BasicAuthenticationFilter.class);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

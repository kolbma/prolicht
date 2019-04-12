package eu.n4v.prolicht;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Authorization on http method and path. Dummy UserDetailsService for authentication.
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${prolichtuser}")
    private String username;
    @Value("${prolichtpassword}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/account/**").authenticated()
                .antMatchers(HttpMethod.GET, "/applicant").authenticated()
                .antMatchers(HttpMethod.GET, "/applicant/**").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/csrf", "/h2-console/**", "/index.html",
                        "/swagger-ui.html", "/webjars/springfox-swagger-ui/**",
                        "/swagger-resources/**", "/v2/api-docs", "/event/**", "/eventcategories/**",
                        "/hobby/**", "/hobbycategories/**", "/knowledge/**",
                        "/knowledgecategories/**", "/photo/**")
                .permitAll().anyRequest().authenticated().and().httpBasic().and().csrf().disable()
                .headers().frameOptions().disable();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        if (username.isEmpty() || password.isEmpty()) {
            log.error("Missing environment variables for PROLICHTUSER and/or PROLICHTPASSWORD");
            return super.userDetailsService();
        }
        UserBuilder users = User.withDefaultPasswordEncoder(); // TODO: DefaultPasswordEncoder for
                                                               // demo OK
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username(username).password(password).roles("USER").build());
        return manager;
    }
}

/*
   Copyright 2019 Markus Kolb

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
                .antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/csrf", "/index.html", "/vendors/**", "/css/**",
                        "/js/**", "/fonts/**", "/img/**", "/swagger-ui.html",
                        "/webjars/springfox-swagger-ui/**", "/swagger-resources/**", "/v2/api-docs",
                        "/event/**", "/eventcategories/**", "/hobby/**", "/hobbycategories/**",
                        "/knowledge/**", "/knowledgecategories/**", "/photo/**")
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

package jun.th.custome_filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    CustomeFilter1 filter = new CustomeFilter1();
    filter.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher("/login", "POST"));
    filter.setAuthenticationManager(new ProviderManager(new CustomAuthenticationProvider()));

		http
      .authorizeHttpRequests(request -> request
        .requestMatchers("/csrf").permitAll()
        .anyRequest().authenticated())
      .addFilter(filter)
      .logout((logout) -> logout.permitAll());
    return http.build();
	}

}

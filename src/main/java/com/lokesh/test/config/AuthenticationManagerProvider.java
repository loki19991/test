package com.lokesh.test.config;

import com.lokesh.test.filter.RequestLoggingFilterConfig;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthenticationManagerProvider extends WebSecurityConfigurerAdapter {
  private static List<String> SKIP_PATHS =
      Arrays.asList(
          "/console/**", "/webjars/**", "/swagger**", "/swagger-resources/**", "/v2/api-docs");

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public FilterRegistrationBean loggingFilterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setUrlPatterns(Arrays.asList("/*"));
    registrationBean.setName("Logging Filter");
    registrationBean.setFilter(new RequestLoggingFilterConfig().loggingFilter());
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registrationBean;
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.headers().frameOptions().disable().and().csrf().disable();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(String.valueOf(SKIP_PATHS));
  }
}

package com.baosight.iplat4j.config;

import com.baosight.iplat4j.core.security.IServiceValidateFilter;
import com.baosight.iplat4j.core.security.PlatServiceValidateFilter;
import com.baosight.iplat4j.core.security.SecurityTokenFilter;
import com.baosight.iplat4j.core.security.UserSessionFilter;
import com.baosight.iplat4j.core.security.authentication.ServiceAuthenticationEntryPoint;
import com.baosight.iplat4j.core.security.web.PlatAnonymousRequestMatcher;
import com.baosight.iplat4j.core.security.web.PlatformAccessDeniedHandler;
import com.baosight.iplat4j.core.security.web.PlatformWebAuthenticationDetailsSource;
import com.baosight.iplat4j.core.web.filter.DiagnoseFilter;
import javax.servlet.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@Order(103)
@ConditionalOnProperty(name = {"iplat.core.security.configType"}, havingValue = "default", matchIfMissing = true)
public class ServiceSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger logger = LogManager.getLogger(ServiceSecurityConfiguration.class);

    @Autowired
    protected ServiceAuthenticationEntryPoint serviceAuthenticationEntryPoint;

    @Autowired
    protected PlatformWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    protected HttpSessionSecurityContextRepository securityContextRepository;

    @Autowired
    protected PlatformAccessDeniedHandler accessDeniedHandler;

    @Autowired
    protected UserSessionFilter userSessionFilter;

    @Autowired
    protected SecurityTokenFilter securityTokenFilter;

    @Autowired
    protected DiagnoseFilter diagnoseFilter;

    @Autowired(required = false)
    protected IServiceValidateFilter iServiceValidateFilter;

    @Autowired
    protected PlatAnonymousRequestMatcher platAnonymousRequestMatcher;

    @Autowired
    protected PlatServiceValidateFilter platServiceValidateFilter;

    protected void configure(HttpSecurity http) {
        this.logger.debug("Using iPlat configure(HttpSecurity). ");
        try {
            ((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)http
                    .requestMatcher((RequestMatcher)new OrRequestMatcher(new RequestMatcher[] { (RequestMatcher)new AntPathRequestMatcher("/*/**") })).httpBasic()
                    .authenticationDetailsSource((AuthenticationDetailsSource)this.authenticationDetailsSource)
                    .authenticationEntryPoint((AuthenticationEntryPoint)this.serviceAuthenticationEntryPoint).and())
                    .authorizeRequests()
                    .antMatchers(
                            AnonymousUrls.getUrls()))
                    .permitAll().requestMatchers(new RequestMatcher[] { (RequestMatcher)this.platAnonymousRequestMatcher })).permitAll()
                    .antMatchers(new String[] { "/service/XS0104/*" })).hasRole("CHANGEPASS")
                    .antMatchers(new String[] { "/service/*/*" })).permitAll()
                    .anyRequest()).hasRole("VERIFIED")
                    .and())
                    .csrf().disable())
                    .headers().frameOptions().sameOrigin().and())
                    .securityContext().securityContextRepository((SecurityContextRepository)this.securityContextRepository).and())
                    .addFilterBefore((Filter)this.securityTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore((Filter)this.diagnoseFilter, UsernamePasswordAuthenticationFilter.class)
                    .anonymous().key("foobar").and())
                    .sessionManagement().and()).addFilterAfter((Filter)this.userSessionFilter, SessionManagementFilter.class)
                    .addFilterAfter((Filter)this.platServiceValidateFilter, ExceptionTranslationFilter.class)
                    .exceptionHandling().accessDeniedHandler((AccessDeniedHandler)this.accessDeniedHandler).authenticationEntryPoint((AuthenticationEntryPoint)this.serviceAuthenticationEntryPoint);
            if (this.iServiceValidateFilter != null)
                http.addFilterAfter((Filter)this.iServiceValidateFilter, ExceptionTranslationFilter.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

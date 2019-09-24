package com.lokesh.test.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@Slf4j
public class RequestLoggingFilterConfig {

  private static String getStringRepresentation(Map<String, String[]> parameters) {
    return parameters
        .entrySet()
        .stream()
        .map(entry -> entry.getKey().concat("=").concat(Arrays.toString(entry.getValue())))
        .collect(Collectors.toList())
        .toString();
  }

  public Filter loggingFilter() {

    final OncePerRequestFilter requestFilter =
        new OncePerRequestFilter() {
          @Override
          protected void doFilterInternal(
              final HttpServletRequest request,
              final HttpServletResponse response,
              final FilterChain filterChain)
              throws ServletException, IOException {
            setMDCValues(request);
            final String requestURI = request.getRequestURI();
            log.info(
                "action=REQ_ENTER uri={} requestContentType={} requestLocale={} clientIP={} method={} protocol={} requestParameters={} headers={}",
                requestURI,
                request.getContentType(),
                request.getLocale(),
                request.getRemoteAddr(),
                request.getMethod(),
                request.getProtocol(),
                getStringRepresentation(request.getParameterMap()),
                getHeaders(request));
            filterChain.doFilter(request, response);
            log.info(
                "action=REQ_EXIT uri={} statusCode={} responseContentType={} responseLocale={} request-headers={}",
                requestURI,
                response.getStatus(),
                response.getContentType(),
                response.getLocale(),
                getHeaders(request));
          }
        };
    return requestFilter;
  }

  private void setMDCValues(final HttpServletRequest request) throws UnsupportedEncodingException {
    final Optional<String> uuid = Optional.ofNullable(request.getHeader("X-Correlation-Id"));
    final Optional<String> sessionid = Optional.ofNullable(request.getRequestedSessionId());
    MDC.put("uuid", uuid.orElse(UUID.randomUUID().toString()));
    sessionid.ifPresent(value -> MDC.put("sessionid", value));
  }

  private String getHeaders(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames())
        .stream()
        .map(e -> e.concat("=").concat(request.getHeader(e)))
        .collect(Collectors.toList())
        .toString();
  }
}

package com.mycompany.webtechnikonproject.security;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 *
 * @author iracl
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
  public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) throws IOException {
    crc1.getHeaders().add("Access-Control-Allow-Origin", "*");
    crc1.getHeaders().add("Access-Control-Allow-Credentials", "true");
    crc1.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
    crc1.getHeaders().add("Access-Control-Allow-Headers", "username, password, X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
    crc1.getHeaders().add("Access-Control-Max-Age", "1209600");
    crc1.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, crossdomain");

    }
} 

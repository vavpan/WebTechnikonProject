//package com.mycompany.webtechnikonproject.security;
//
//
//import jakarta.annotation.security.DenyAll;
//import jakarta.annotation.security.PermitAll;
//import jakarta.annotation.security.RolesAllowed;
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.container.ResourceInfo;
//import jakarta.ws.rs.core.Context;
//import jakarta.ws.rs.core.MultivaluedMap;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.Provider;
//
//import java.lang.reflect.Method;
//import java.util.*;
//
//@Provider
//public class AuthenticationFilter implements ContainerRequestFilter {
//
//    @Context
//    private ResourceInfo resourceInfo;
//
//    private static final String AUTHORIZATION_PROPERTY = "Authorization";
//    private static final String AUTHENTICATION_SCHEME = "Basic";
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//        Method method = resourceInfo.getResourceMethod();
//        //Access allowed for all
//        if (!method.isAnnotationPresent(PermitAll.class)) {
//            //Access denied for all
//            if (method.isAnnotationPresent(DenyAll.class)) {
//                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
//                        .entity("Access blocked for all users !!").build());
//                return;
//            }
//
//            //Get request headers
//            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
//
//            //Fetch authorization header
//            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
//
//            //If no authorization information present; block access
//            if (authorization == null || authorization.isEmpty()) {
//                requestContext
//                        .abortWith(Response
//                                .status(Response.Status.UNAUTHORIZED)
//                                .entity("You cannot access this resource")
//                                .build());
//                return;
//            }
//
//            //Get encoded username and password
//            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//
//            //Decode username and password
//            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));;
//
//            //Split username and password tokens
//            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//            final String username = tokenizer.nextToken();
//            final String password = tokenizer.nextToken();
//
//            //Verify user access
//            if (method.isAnnotationPresent(RolesAllowed.class)) {
//                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
//                Set<String> rolesSetForTheResource = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
//
//                //Is user valid?
//                if (!isUserAllowed(username, password, rolesSetForTheResource)) {
//                    requestContext.abortWith(Response
//                            .status(Response.Status.UNAUTHORIZED)
//                            .entity("You cannot access this resource")
//                            .build());
//                    return;
//                }
//            }
//        }
//    }
//
//    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSetExpected) {
//        boolean isAllowed = false;
//
//        if (username.equals("admin") && password.equals("password")) {
//            String userRole = "ADMIN"; // the role of the username, password
//            if (rolesSetExpected.contains(userRole)) {
//                isAllowed = true;
//            }
//        }
//        return isAllowed;
//    }
//
//}

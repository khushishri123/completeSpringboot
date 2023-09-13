package com.example.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //UserDetailsService is an interface and there are so many implementations of it. But since we want to fetch user
    // from our db we will write our own implementation, we will create  a ApplicationConfig class which will hold
    // all the application configuration.
    @Autowired
    private UserDetailsService userDetailsService;
    private final JwtService jwtService=new JwtService ();
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request ,@NonNull HttpServletResponse response ,@NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader=request.getHeader ( "Authorization" );
    String jwt;
    String userEmail;
        System.out.println ("doFilterInternal got executed in JWTAuthneticationFilter");
    if(authHeader==null || !authHeader.startsWith ( "Bearer " ))
    {
        filterChain.doFilter ( request,response );
        return;
    }
    //now we want to fetch the jwt token from the header but we will start fetching after index 7 because "Bearer " is
        //of length 7
    jwt=authHeader.substring ( 7 );
    //after that we will call UserDetailService to check that whether we have that user in our db or not.
    //But for that we need to call JwtService to extractr the username
    //for that we have taken userEmail field
    // we have to extract the userEmail from the JwtToken but for that we need a class that can manipulate this
    // JWT token, so we created JwtService class. We will define "extractUsername" method and passed jwt as paramater
    userEmail=jwtService.extractUsername(jwt);
    //once we have done validation process, we also need to check if we have user within the db.
    // if we get userEmail and user is not Authenticated, we will get the userDetails from the db, we will check
// if the user is valid or not. If the user and token is valid, we create an object of UsernamePasswordAuthentication
   // we passed userDetails and authorities as parameters and we inforce or extend the details of users with our JWT
    // token and then we update the authentication token.

    if(userEmail!=null && SecurityContextHolder.getContext ().getAuthentication ()==null)
    {
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
        System.out.println ("Loaded successfully");
        //The next step is to check that whether the token is still valid or not
        if(jwtService.isTokenValid (jwt,userDetails))
        {
            //We need to create an object of UsernamePasswordAuthenticationToken , it’s object is needed by the Spring
            // and SecurityContextHolder in order to update our SecurityContext. While creating object since we don't have
            //credentials in the userDetails we have passed null.

            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken (
                    userDetails,null,userDetails.getAuthorities ()
            );
            // to give some more details
            authToken.setDetails (
                    new WebAuthenticationDetailsSource ().buildDetails ( request )
            );
            // update SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication ( authToken );

        }
    }
    //after that always call filterChain.doFilter method , to pass the hand to the next filters to be executed.
        filterChain.doFilter ( request,response );


    }
}

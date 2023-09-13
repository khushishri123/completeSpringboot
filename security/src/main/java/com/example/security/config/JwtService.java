package com.example.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    //we have generated this secret key randomly, it is a 256 bit key , to generate this key go to this website:
    //https://generate-random.org/encryption-key-generator?count=1&bytes=256&cipher=aes-256-cbc&string=&password=
    private static final String secretKey="3wtz5NCeiPeifn/gO9nqN/FOwr99eHJ+1YfJ7pKyoOzcQqy4iryDtEaZXuXQ1A7H";

    public String extractUsername(String token) {
        return extractClaim ( token,Claims::getSubject ); // getSubject will give you the username or userEmail
    }
    //if we want to pass only userDetails and not  extra claims
    public String generateToken(UserDetails userDetails)
    {
        return generateToken ( new HashMap<> (),userDetails );
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails)
    {
        return Jwts.builder ().
                setClaims ( extraClaims ).
                setSubject ( userDetails.getUsername () ).
                setIssuedAt ( new Date (System.currentTimeMillis ()) ).
                setExpiration ( new Date (System.currentTimeMillis () +1000*60*24)).
                signWith ( getSignInKey (), SignatureAlgorithm.HS256 ).
                compact ();
    }
    // we defined the below method to extract a single claim
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver)
    {
        final Claims claims=extractAllClaims ( token );
        return claimResolver.apply ( claims );
    }
    public Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder ().
                setSigningKey ( getSignInKey() ). // we have defined getSignInKey method
                build().   // since it is a buider we have to to use build
                parseClaimsJws ( token ). // we need to parse our token
                getBody ();
    }

    private Key getSignInKey() {
        byte []keyBytes= Decoders.BASE64.decode (secretKey);
        return Keys.hmacShaKeyFor ( keyBytes );
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username=extractUsername ( token );
        return (username.equals ( userDetails.getUsername ())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        //here we want to fetch the expiration date of the token from the given token
        return extractClaim ( token, Claims::getExpiration );
    }

}

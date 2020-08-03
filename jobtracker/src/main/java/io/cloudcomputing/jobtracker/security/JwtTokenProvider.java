package io.cloudcomputing.jobtracker.security;

import io.cloudcomputing.jobtracker.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.cloudcomputing.jobtracker.security.SecurityConstants.EXPIRATION_TIME;
import static io.cloudcomputing.jobtracker.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    //generate the token
    public String generateToken(Authentication authentication){
        User user =(User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());// cast the user id to string before passing to the token
        // we're going to pass all these to the client
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException ex){
            System.out.println("Invalid JWT Sinature");
        }catch(MalformedJwtException ex){
            System.out.println("InvalidJWT Token");
        }catch(ExpiredJwtException ex){
            System.out.println("Expired JWT tOken");
        }catch(UnsupportedJwtException ex){
            System.out.println("Unsupported JWT Token");
        }catch(IllegalArgumentException ex){
            System.out.println("JWT claims String is empty");
        }
        return false;
    }

    //Get the user Id from the token
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }
}

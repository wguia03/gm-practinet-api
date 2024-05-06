    package com.gm.practicasya.utils;

    import com.auth0.jwt.JWT;
    import com.auth0.jwt.JWTVerifier;
    import com.auth0.jwt.algorithms.Algorithm;
    import com.auth0.jwt.exceptions.JWTVerificationException;
    import com.auth0.jwt.interfaces.Claim;
    import com.auth0.jwt.interfaces.DecodedJWT;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.stereotype.Component;

    import java.util.Date;
    import java.util.Map;
    import java.util.UUID;
    import java.util.stream.Collectors;

    @Component
    public class JwtUtils {

        @Value("${security.jwt.key}")
        private String privateKey;

        @Value("${security.jwt.generator}")
        private String userGenerator;

        public String createToken(Authentication authentication){

            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            String username = authentication.getPrincipal().toString();
            String authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            return JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // valid only 30 minutes
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date())
                    .sign(algorithm);
        }

        public DecodedJWT validateToken(String token){
            try {
                Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
                JWTVerifier jwtVerifier = JWT.require(algorithm)
                        .withIssuer(this.userGenerator)
                        .build();
                return jwtVerifier.verify(token);
            } catch (JWTVerificationException e){
              throw new JWTVerificationException("Invalid token");
            }
        }

        public String getUsername(DecodedJWT decodedJWT){
            return decodedJWT.getSubject();
        }

        public Claim getClaim(DecodedJWT decodedJWT, String claimName){
            return decodedJWT.getClaim(claimName);
        }

        public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT){
            return decodedJWT.getClaims();
        }
    }

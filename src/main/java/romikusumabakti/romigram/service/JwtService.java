package romikusumabakti.romigram.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    Algorithm algorithm = Algorithm.HMAC256("secret");

    public String create(String issuer) {
        return JWT.create().withIssuer(issuer).sign(algorithm);
    }

    public Boolean verify(String token, String issuer) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String getIssuer(String token) {
        return JWT.decode(token).getIssuer();
    }

}
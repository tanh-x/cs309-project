package cs309.backend.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public final class AuthorizationUtils {
    private static final int BCRYPT_LOG_ROUNDS = 8;
    private static final long SESSION_EXPIRATION_MILLIS = 1000 * 3600 * 24 * 12;  // 12 days

    private static final Algorithm jwtAlgorithm = Algorithm.HMAC384("giQsPsKmNmQaLPNsHMVjrSv32IBRMjCQQXrcrsnJAHQ0a8qV");

    public static String bcryptHash(String pwd) { return BCrypt.hashpw(pwd, BCrypt.gensalt(BCRYPT_LOG_ROUNDS)); }

    public static String createSessionJwt(String username) {
        Date iat = new Date();  // Issued at
        Date exp = new Date(iat.getTime() + SESSION_EXPIRATION_MILLIS);  // Expires at

        String token = JWT.create()
            .withClaim("userName", username)
            .withIssuedAt(iat)
            .withExpiresAt(exp)
            .sign(jwtAlgorithm);

        System.out.println("Creating new token for " + username + ": " + token);
        return token;
    }

    // Prevent instantiation
    private AuthorizationUtils() { throw new UnsupportedOperationException(); }
}

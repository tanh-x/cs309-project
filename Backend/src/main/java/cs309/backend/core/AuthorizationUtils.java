package cs309.backend.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public final class AuthorizationUtils {
    private static final int BCRYPT_LOG_ROUNDS = 8;
    private static final long SESSION_EXPIRATION_MILLIS = 1000 * 3600 * 24 * 12;  // 12 days

    @Value("${privateKey}")
    private static String privateKey;
    private static final Algorithm jwtAlgorithm = Algorithm.HMAC384(privateKey);

    public static String bcryptHash(String pwd) { return BCrypt.hashpw(pwd, BCrypt.gensalt(BCRYPT_LOG_ROUNDS)); }

    public static String createSessionJwt(int userId) {
        Date iat = new Date();  // Issued at
        Date exp = new Date(iat.getTime() + SESSION_EXPIRATION_MILLIS);  // Expires at

        String token = JWT.create()
            .withClaim("userId", userId)
            .withIssuedAt(iat)
            .withExpiresAt(exp)
            .sign(jwtAlgorithm);

        System.out.println("Creating new token for " + userId + ": " + token);
        return token;
    }

    // Prevent instantiation
    private AuthorizationUtils() { throw new UnsupportedOperationException(); }
}

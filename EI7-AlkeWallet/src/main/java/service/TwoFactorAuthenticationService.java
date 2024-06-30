package service;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthenticationService {

    public String generateSecret() {
        return Base32.random();
    }

    public boolean verifyCode(String secret, String code) {
        Totp totp = new Totp(secret);
        return totp.verify(code);
    }
}
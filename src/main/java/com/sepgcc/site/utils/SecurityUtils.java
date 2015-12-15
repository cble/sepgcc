package com.sepgcc.site.utils;

import com.google.common.collect.Lists;
import com.sepgcc.site.constants.SecurityConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SecurityUtils {

    private static final Logger log = Logger.getLogger(SecurityUtils.class);

    private static final String SEP = "/";
    private static final String ALGORITHM = "AES";
    private static final String MODE = "ECB";
    private static final String PADDING = "PKCS5Padding";
    private static final String TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;

    /**
     * aes(salt|timestamp|userId)
     */
    public static String generateToken(int userId) {
        try {
            String plainString = StringUtils.join(Lists.newArrayList(
                    SecurityConstants.TOKEN_SALT,
                    String.valueOf(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRE_TIME),
                    String.valueOf(userId)
            ), SEP);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            Key k = new SecretKeySpec(SecurityConstants.TOKEN_AES_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byte[] result = cipher.doFinal(plainString.getBytes());
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            log.error("generateToken error", e);
        }
        return null;
    }

    public static int parseToken(String token) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            Key k = new SecretKeySpec(SecurityConstants.TOKEN_AES_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] result = cipher.doFinal(Base64.decodeBase64(token));
            String[] tokenItems = (new String(result)).split(SEP);
            Validate.isTrue(tokenItems.length == 3, "invalid token");
            Validate.isTrue(SecurityConstants.TOKEN_SALT.equals(tokenItems[0]), "invalid token");
            if (NumberUtils.toLong(tokenItems[1]) > System.currentTimeMillis()) {
                return NumberUtils.toInt(tokenItems[2]);
            }
        } catch (Exception e) {
            log.error("parseToken error", e);
        }
        return 0;
    }

    public static String encryptPassword(String password) {
        try {
            return DigestUtils.md5DigestAsHex((SecurityConstants.PWD_SALT + password).getBytes("UTF-8")).toUpperCase();
        } catch (Exception e) {
            log.error("encryptPassword error", e);
        }
        return null;
    }
}

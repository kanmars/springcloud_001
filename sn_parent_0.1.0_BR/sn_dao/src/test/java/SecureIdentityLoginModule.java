/**
 * Created by zhaojiuyang on 2015/12/10.
 */

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SecureIdentityLoginModule {

    public static final HLogger logger = LoggerManager.getLoger("SecureIdentityLoginModule");

    private static byte[] ENC_KEY_BYTES;
    private static final String defaultCharset = "UTF-8";

    static {
        try {
            ENC_KEY_BYTES = "This is a finger".getBytes(defaultCharset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDecodedPassword() throws Exception {
        return new String(decode(password));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SecureIdentityLoginModule other = (SecureIdentityLoginModule) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    public static String encode(String encKey, String secret) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        BigInteger n = null;
        byte[] kbytes = SecureIdentityLoginModule.ENC_KEY_BYTES;
        if (StringUtils.isNotBlank(encKey)) {
            kbytes = encKey.getBytes(defaultCharset);
        }
        SecretKeySpec key = new SecretKeySpec(kbytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encoding = cipher.doFinal(secret.getBytes(defaultCharset));
        n = new BigInteger(encoding);
        return n.toString(16);
    }

    public static String encode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return SecureIdentityLoginModule.encode(null, secret);
    }

    public static String decode(String encKey, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] kbytes = SecureIdentityLoginModule.ENC_KEY_BYTES;
        if (StringUtils.isNotBlank(encKey)) {
            kbytes = encKey.getBytes(defaultCharset);
        }
        SecretKeySpec key = new SecretKeySpec(kbytes, "AES");
        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = cipher.doFinal(encoding);
        return new String(decode,defaultCharset);
    }

    public static char[] decode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return SecureIdentityLoginModule.decode(null, secret).toCharArray();
    }

    public static void main(String[] args) throws Exception {
        logger.info("Encoded password: " + SecureIdentityLoginModule.encode("root"));
        logger.info("decoded password: " + new String(SecureIdentityLoginModule.decode("-64d29910cc13d220ea2e89c490b1e4bf")));
        logger.info("Encoded password: " + SecureIdentityLoginModule.encode("123456"));
        logger.info("decoded password: " + new String(SecureIdentityLoginModule.decode("29ec2159f152a824fccd68a871b83415")));
    }
}
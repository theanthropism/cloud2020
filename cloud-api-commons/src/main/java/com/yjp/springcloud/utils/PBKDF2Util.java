package com.yjp.springcloud.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * ClassName: PBKDF2Util
 * Description:
 * date: 2020/12/23 15:54
 *
 * @author yan
 */
public class PBKDF2Util {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";


    public static final int SALT_BYTE_SIZE = 32 / 2;         //盐的长度
    public static final int HASH_BIT_SIZE = 128 * 4;         //生成密文的长度
    public static final int PBKDF2_ITERATIONS = 1000;        //迭代次数

    /**
     * @auther: Ragty
     * @describe: 对输入的密码进行验证
     * @param: [attemptedPassword(待验证密码), encryptedPassword(密文), salt(盐值)]
     * @return: boolean
     * @date: 2018/11/2
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * @auther: Ragty
     * @describe: 生成密文
     * @param: [password(明文密码), salt(盐值)]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * @auther: Ragty
     * @describe: 通过加密的强随机数生成盐(最后转换为16进制)
     * @param: []
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return toHex(salt);
    }

    /**
     * @auther: Ragty
     * @describe: 十六进制字符串转二进制字符串
     * @param: [hex]
     * @return: byte[]
     * @date: 2018/11/2
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }


    /**
     * @auther: Ragty
     * @describe: 二进制字符串转十六进制字符串
     * @param: [array]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else {
            return hex;
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        String salt = null;
        try {
            salt = PBKDF2Util.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String pbkdf2 = null;
        try {
            pbkdf2 = PBKDF2Util.getEncryptedPassword(password,salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean pdkdf3 = null;
        try {
            pdkdf3 = PBKDF2Util.authenticate(password,pbkdf2,salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String md5 = Md5Utils.getMD5(password);

        System.out.println("原始密码:"+password);
        System.out.println("MD5加密后的密码:"+md5);
        System.out.println("盐值:"+salt);
        System.out.println("PBKDF2加盐后的密码:"+pbkdf2);
        System.out.println("Test success");
        System.out.println("PBKDF3=2加盐后的解密:"+pdkdf3);
    }

}

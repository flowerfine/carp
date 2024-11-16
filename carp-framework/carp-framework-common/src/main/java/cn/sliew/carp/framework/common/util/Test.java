package cn.sliew.carp.framework.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.security.KeyPair;

public class Test {

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIM2r5hz37QIlsSNN0UNaya8REFxlPoEoc6kN69KEqjKnFG0SFeyTgcHsTlz7NDNgZKuL5R3NT/T/RkFZJZzynlwc+Dim5eeKTGp4EptjthsvKVDJ6N6+p3IatJR0OsHIWKnQlfgzCkni49VQo5vGVM7sk0QQb3SBxRyAMv1WIZPAgMBAAECgYASHYKwsY649FacXEK9LdUVS/jAcEX5HpqYROPLN5bL5u0d+p3iPaO4JL+7Bt4zFaxF6/Z1sN+RKFtREQWKIAJb/rYrB/gJ1auN7dIXao1Xcq3pJvqYNqoV9JbGN1wwgyojFri2eTdPCrLeuIwCxYUJoHmZE8ODdXPCE+ZAtyMLQQJBANEflTbmJhuaAS5LFVmEdCclwRncZtOspS9ODn99Zd69HGtiCw+YqMnnIKDTwI3ep1qs8/MvqaLuDFg4TDY3aOsCQQCgoEyg+8ll4xATdkaaIWwxhOa9OD8blSPePbt/3gyISddMhLVV53Dq1GDNBwrzV6sLQb0781FFTVTXrhyuC/8tAkEAq/SdXJwYn7+d0vQYZRhd7kbEJsCtqMaguWokz75MAsBb2wyuba+oswSjNruH7OA1moD2w3PguEGn0u7P9BDR6wJAOs10QBrtB9ewMu/BuPszWI2Gyw6kS7y1fM6srYrkm6AsqV6L4/7uX4mQRn6li6A0hdJzLFdPEUfWH38mELFjoQJALbHs/5FmwxQFp04ZtlI74AKIUCnzvBdt+9EBIhhj/Hi3fNyOiNBNi/unONvCYq89zMxsypOIFvMynKHwJWRrZg==";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDNq+Yc9+0CJbEjTdFDWsmvERBcZT6BKHOpDevShKoypxRtEhXsk4HB7E5c+zQzYGSri+UdzU/0/0ZBWSWc8p5cHPg4puXnikxqeBKbY7YbLylQyejevqdyGrSUdDrByFip0JX4MwpJ4uPVUKObxlTO7JNEEG90gcUcgDL9ViGTwIDAQAB";

    public static void main(String[] args) {
        String text = "1000000000000000000000000000000000000000000000000000000";
        RSA rsa = SecureUtil.rsa(PRIVATE_KEY, PUBLIC_KEY);
        String encrypted = rsa.encryptBase64(text, KeyType.PublicKey);
        System.out.println("长度: " + encrypted.length() + ", 密文: " + encrypted);
        String decrypted = rsa.decryptStr(encrypted, KeyType.PrivateKey);
        System.out.println(decrypted);
    }


    private static void generateKeyPair() {
        KeyPair rsa = SecureUtil.generateKeyPair("rsa");
        System.out.println(Base64.encode(rsa.getPrivate().getEncoded()));
        System.out.println(Base64.encode(rsa.getPublic().getEncoded()));
    }
}

package xyz.frankiegao123.crypto.service.base;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import xyz.frankiegao123.crypto.bean.UserData;

public class UserDataCryptology {

    protected UserData ud;
    protected KeyPair keyPair;

    public UserDataCryptology(UserData ud) {
        this.ud = ud;
    }

    /**
     * 截取算法名
     *
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected String getAlgorithm() {
        String alg = ud.getAlgorithm();
        int slash = alg.indexOf('/');
        if(slash < 0) {
            return alg;
        }
        return alg.substring(0, slash);
    }

    /**
     * 获取非对称加密的密钥
     *
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected Key getAsymmetricalKey(String algorithm) throws Exception {
        if(ud.getKey().isEmpty()) {
            // 没有填写密钥时，根据算法的密钥规范随机生成密钥
            KeyPairGenerator gen = KeyPairGenerator.getInstance(algorithm);
            if(ud.getKeysize() > 0) {
                // 填写密钥长度时，设置密钥长度
                gen.initialize(ud.getKeysize());
            }
            // 生成密钥对
            this.keyPair = gen.generateKeyPair();

            // 确认使用公钥还是私钥
            return ud.usingPublicKey() ? keyPair.getPublic() : keyPair.getPrivate();
        } else {
            // 如果填写了密钥，根据密钥字节包装 Key 对象
            // 公钥采用 X509EncodedKeySpec
            // 私钥采用 PKCS8EncodedKeySpec
            KeySpecWrap wrap = ud.usingPublicKey() ? KeySpecWrap.X509 : KeySpecWrap.PKCS8;
            return wrap.getKey(ud.getKey(), algorithm);
        }
    }

    /**
     * 获取数字签名的密钥
     *
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected Key getSignatureKey(String algorithm) throws Exception {
        if(ud.getKey().isEmpty()) {
            KeyPairGenerator gen = KeyPairGenerator.getInstance(algorithm);
            if(ud.getKeysize() > 0) {
                gen.initialize(ud.getKeysize());
            }
            this.keyPair = gen.generateKeyPair();
            return ud.isSignature() ? keyPair.getPrivate() : keyPair.getPublic();
        } else {
            KeySpecWrap wrap = ud.isSignature() ? KeySpecWrap.PKCS8 : KeySpecWrap.X509;
            return wrap.getKey(ud.getKey(), algorithm);
        }
    }

    /**
     * 获取对称加密的密钥
     *
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected Key getSecretKey(String algorithm) throws NoSuchAlgorithmException {
        if(ud.getKey().isEmpty()) {
            KeyGenerator gen = KeyGenerator.getInstance(algorithm);
            if(ud.getKeysize() > 0) {
                gen.init(ud.getKeysize());
            }
            return gen.generateKey();
        } else {
            return new SecretKeySpec(ud.getKey().getEncode(), algorithm);
        }
    }
}

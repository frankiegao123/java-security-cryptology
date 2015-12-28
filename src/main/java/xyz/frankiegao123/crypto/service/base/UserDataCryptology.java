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
     * <p>��ȡ�㷨��</p>
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
     * <p>��ȡ�ǶԳƼ��ܵ���Կ</p>
     *
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected Key getAsymmetricalKey(String algorithm) throws Exception {
        if(ud.getKey().isEmpty()) {
            // û����д��Կʱ�������㷨����Կ�淶���������Կ
            KeyPairGenerator gen = KeyPairGenerator.getInstance(algorithm);
            if(ud.getKeysize() > 0) {
                // ��д��Կ����ʱ��������Կ����
                gen.initialize(ud.getKeysize());
            }
            // ������Կ��
            this.keyPair = gen.generateKeyPair();
            
            // ȷ��ʹ�ù�Կ����˽Կ
            return ud.usingPublicKey() ? keyPair.getPublic() : keyPair.getPrivate();
        } else {
            // �����д����Կ��������Կ�ֽڰ�װ Key ����
            // ��Կ���� X509EncodedKeySpec
            // ˽Կ���� PKCS8EncodedKeySpec
            KeySpecWrap wrap = ud.usingPublicKey() ? KeySpecWrap.X509 : KeySpecWrap.PKCS8;
            return wrap.getKey(ud.getKey(), algorithm);
        }
    }
    
    /**
     * <p>��ȡ����ǩ������Կ</p>
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
     * <p>��ȡ�ԳƼ��ܵ���Կ</p>
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

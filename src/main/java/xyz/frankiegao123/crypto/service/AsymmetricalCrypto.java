package xyz.frankiegao123.crypto.service;

import java.security.Key;

import javax.crypto.Cipher;

import xyz.frankiegao123.crypto.bean.ByteInfo;
import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

/**
 * <p>非对称加密</p>
 * @author frankiegao123
 * 2010-6-6 下午01:48:44
 */
public class AsymmetricalCrypto extends UserDataCryptology implements Cryptology  {
    
    public AsymmetricalCrypto(UserData ud) {
        super(ud);
    }

    @Override
    public CryptoData encrypt() throws Exception {
        
        // 初始化密码对象
        Cipher cipher = Cipher.getInstance(ud.getAlgorithm());
        
        // 获取密钥
        Key key = getAsymmetricalKey(getAlgorithm());
        
        // 加密、解密初始化
        cipher.init(ud.getCipherMode(), key);
        
        // 执行加密、解密操作
        byte[] crypto = cipher.doFinal(ud.getBytesData());
        
        CryptoData data = new CryptoData(crypto, ud, cipher.getProvider());
        
        // 处理密钥用于页面显示
        if(ud.getKey().isEmpty()) {
            if(keyPair != null) {
                data.setPublicKey(new ByteInfo(keyPair.getPublic().getEncoded()));
                data.setPrivateKey(new ByteInfo(keyPair.getPrivate().getEncoded()));
            }
        } else {
            if(ud.usingPublicKey()) {
                data.setPublicKey(new ByteInfo(ud.getKey().getEncode()));
            } else {
                data.setPrivateKey(new ByteInfo(ud.getKey().getEncode()));
            }
        }

        return data;
    }
}

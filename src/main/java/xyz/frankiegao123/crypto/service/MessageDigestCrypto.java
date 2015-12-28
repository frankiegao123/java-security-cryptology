package xyz.frankiegao123.crypto.service;

import java.security.MessageDigest;

import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

public class MessageDigestCrypto extends UserDataCryptology implements Cryptology {
    
    public MessageDigestCrypto(UserData ud) {
        super(ud);
    }
    
    public CryptoData encrypt() throws Exception {        
        
        MessageDigest md = MessageDigest.getInstance(ud.getAlgorithm());
        md.update(ud.getBytesData());
        byte[] digest = md.digest();
        
        return new CryptoData(digest, ud, md.getProvider());
    }
}

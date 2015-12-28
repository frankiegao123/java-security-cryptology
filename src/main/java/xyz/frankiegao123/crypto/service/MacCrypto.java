package xyz.frankiegao123.crypto.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import xyz.frankiegao123.crypto.bean.ByteInfo;
import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

public class MacCrypto extends UserDataCryptology implements Cryptology {

    public MacCrypto(UserData ud) {
        super( ud );
    }

    @Override
    public CryptoData encrypt() throws Exception {
        Mac mac = Mac.getInstance( ud.getAlgorithm( ) );
        Key key = getKey( );
        mac.init( key );
        byte[] crypto = mac.doFinal( ud.getBytesData( ) );
        CryptoData data = new CryptoData( crypto , ud , mac.getProvider( ) );
        data.setKey( new ByteInfo( key.getEncoded( ) ) );
        return data;
    }

    private Key getKey() throws NoSuchAlgorithmException {
        String alg = getAlgorithm( );
        if ( ud.getKey( ).isEmpty( ) ) {
            KeyGenerator gen = KeyGenerator.getInstance( alg );
            return gen.generateKey( );
        }
        return new SecretKeySpec( ud.getKey( ).getEncode( ) , alg );
    }
}

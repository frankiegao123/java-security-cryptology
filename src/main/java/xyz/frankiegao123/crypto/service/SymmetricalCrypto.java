package xyz.frankiegao123.crypto.service;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import xyz.frankiegao123.crypto.bean.ByteInfo;
import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

public class SymmetricalCrypto extends UserDataCryptology implements Cryptology {

    public SymmetricalCrypto(UserData ud) {
        super( ud );
    }

    @Override
    public CryptoData encrypt() throws Exception {
        Cipher cipher = Cipher.getInstance( ud.getAlgorithm( ) );
        Key key = getSecretKey( getAlgorithm( ) );
        if ( ud.getParameterSpec( ).isEmpty( ) ) {
            cipher.init( ud.getCipherMode( ) , key );
        }
        else {
            cipher.init( ud.getCipherMode( ) , key , new IvParameterSpec( ud.getParameterSpec( ).getEncode( ) ) );
        }
        byte[] crypto = cipher.doFinal( ud.getBytesData( ) );

        CryptoData data = new CryptoData( crypto , ud , cipher.getProvider( ) );
        data.setParameterSpec( new ByteInfo( cipher.getIV( ) ) );
        data.setKey( new ByteInfo( key.getEncoded( ) ) );
        return data;
    }
}

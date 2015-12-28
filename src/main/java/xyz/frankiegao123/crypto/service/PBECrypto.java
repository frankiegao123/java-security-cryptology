package xyz.frankiegao123.crypto.service;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEParameterSpec;

import xyz.frankiegao123.crypto.bean.ByteInfo;
import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.KeySpecWrap;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

/**
 * <p>
 * 基于口令的对称加密
 * </p>
 *
 * @author frankiegao123
 */
public class PBECrypto extends UserDataCryptology implements Cryptology {

    /**
     * 迭代次数
     */
    private final static int ITERATION_COUNT = 100;

    public PBECrypto(UserData ud) {
        super( ud );
    }

    @Override
    public CryptoData encrypt() throws Exception {
        Cipher cipher = Cipher.getInstance( ud.getAlgorithm( ) );
        byte[] salt = initSale( );
        Key key = getKey( );
        cipher.init( ud.getCipherMode( ) , key , getSaltParameterSpec( salt ) );
        byte[] crypto = cipher.doFinal( ud.getBytesData( ) );
        CryptoData result = new CryptoData( crypto , ud , cipher.getProvider( ) );
        result.setParameterSpec( new ByteInfo( salt ) );
        result.setKey( new ByteInfo( key.getEncoded( ) ) );
        return result;
    }

    private byte[] initSale() {
        if ( ud.getParameterSpec( ).isEmpty( ) ) {
            SecureRandom ran = new SecureRandom( );
            return ran.generateSeed( 8 );
        }
        else {
            return ud.getParameterSpec( ).getEncode( );
        }
    }

    private AlgorithmParameterSpec getSaltParameterSpec(byte[] salt) {
        return new PBEParameterSpec( salt , ITERATION_COUNT );
    }

    private Key getKey() throws Exception {
        return KeySpecWrap.PBE.getSecretKey( ud.getPasswordChars( ) , ud.getAlgorithm( ) );
    }
}

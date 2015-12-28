package xyz.frankiegao123.crypto.service.base;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;

import xyz.frankiegao123.crypto.bean.KeyInfo;

public enum KeySpecWrap {

    DES {

        @Override
        public KeySpec getKeySpec(byte[] encodedKey) throws InvalidKeyException {
            return new DESKeySpec( encodedKey );
        }
    },

    DESEDE {

        @Override
        public KeySpec getKeySpec(byte[] encodedKey) throws InvalidKeyException {
            return new DESedeKeySpec( encodedKey );
        }
    },

    X509 {

        @Override
        public KeySpec getKeySpec(byte[] encodedKey) throws InvalidKeyException {
            return new X509EncodedKeySpec( encodedKey );
        }
    },

    PKCS8 {

        @Override
        public KeySpec getKeySpec(byte[] encodedKey) throws InvalidKeyException {
            return new PKCS8EncodedKeySpec( encodedKey );
        }
    },

    PBE {

        @Override
        public KeySpec getKeySpec(char[] password) throws InvalidKeyException {
            return new PBEKeySpec( password );
        }
    };

    public KeySpec getKeySpec(byte[] encodedKey) throws InvalidKeyException {
        throw new AbstractMethodError( this.name( ) + " using the method is valid" );
    }

    public KeySpec getKeySpec(char[] password) throws InvalidKeyException {
        throw new AbstractMethodError( this.name( ) + " using the method is valid" );
    }

    public Key getSecretKey(char[] password, String algorithm) throws Exception {
        KeySpec keySpec = getKeySpec( password );
        SecretKeyFactory factory = SecretKeyFactory.getInstance( algorithm );
        return factory.generateSecret( keySpec );
    }

    public Key getSecretKey(KeyInfo keyInfo, String algorithm) throws Exception {
        KeySpec keySpec = getKeySpec( keyInfo.getEncode( ) );
        SecretKeyFactory factory = SecretKeyFactory.getInstance( algorithm );
        return factory.generateSecret( keySpec );
    }

    public Key getKey(KeyInfo keyInfo, String algorithm) throws Exception {
        KeySpec keySpec = getKeySpec( keyInfo.getEncode( ) );
        KeyFactory factory = KeyFactory.getInstance( algorithm );
        if ( this == X509 ) {
            return factory.generatePublic( keySpec );
        }
        return factory.generatePrivate( keySpec );
    }
}

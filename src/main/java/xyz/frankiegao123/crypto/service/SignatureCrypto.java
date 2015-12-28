package xyz.frankiegao123.crypto.service;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import xyz.frankiegao123.crypto.bean.ByteInfo;
import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

public class SignatureCrypto extends UserDataCryptology implements Cryptology {

    public SignatureCrypto(UserData ud) {
        super( ud );
    }

    @Override
    public CryptoData encrypt() throws Exception {
        String[] algorithms = ud.getAlgorithm( ).split( "\\|" );
        String keyAlgorithm = algorithms[1];
        String signurateAlgorithm = algorithms[0];

        Signature sig = Signature.getInstance( signurateAlgorithm );
        Key key = getSignatureKey( keyAlgorithm );

        CryptoData data = null;
        if ( ud.isSignature( ) ) {
            sig.initSign( (PrivateKey) key );
            sig.update( ud.getBytesData( ) );
            byte[] crypto = sig.sign( );
            data = new CryptoData( crypto , ud , sig.getProvider( ) );
        }
        else {
            sig.initVerify( (PublicKey) key );
            sig.update( ud.getBytesData( ) );
            boolean result = sig.verify( ud.getSignatureData( ).getEncode( ) );
            data = new CryptoData( null , ud , sig.getProvider( ) );
            data.setVerifySignature( result );
        }

        if ( ud.getKey( ).isEmpty( ) ) {
            if ( keyPair != null ) {
                data.setPublicKey( new ByteInfo( keyPair.getPublic( ).getEncoded( ) ) );
                data.setPrivateKey( new ByteInfo( keyPair.getPrivate( ).getEncoded( ) ) );
            }
        }
        else {
            if ( ud.isSignature( ) ) {
                data.setPrivateKey( new ByteInfo( ud.getKey( ).getEncode( ) ) );
            }
            else {
                data.setPublicKey( new ByteInfo( ud.getKey( ).getEncode( ) ) );
            }
        }
        return data;
    }
}

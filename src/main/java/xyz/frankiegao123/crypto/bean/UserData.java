package xyz.frankiegao123.crypto.bean;

import java.nio.charset.Charset;
import java.util.Map;

import javax.crypto.Cipher;

import xyz.frankiegao123.crypto.util.Tools;

public class UserData {

    public final static int      ENCRYPT         = 1, PUBLIC_ENCRYPT = 1, PRIVATE_SIGNATURE = 1;

    public final static int      DECRYPT         = 2, PUBLIC_DECRYPT = 2, PUBLIC_VERIFY = 2;

    public final static int      PRIVATE_ENCRYPT = 3;

    public final static int      PRIVATE_DECRYPT = 4;

    private static final Charset UTF_8           = Charset.forName( "UTF-8" );

    private byte[]               byteData;

    private String               algorithm;

    private boolean              usingBouncyCastle;

    private KeyInfo              signatureData;

    private int                  cipherMode;

    private String               password;

    private int                  keysize         = -1;                                           ;

    private KeyInfo              key;

    private KeyInfo              parameterSpec;

    private UserData() {
    }

    public static UserData newInstance(Map<String, String> map) {
        UserData ud = new UserData( );
        ud.password = map.get( "password" );
        ud.byteData = convertByte( map.get( "data" ) );
        ud.signatureData = KeyInfo.getInstance( map.get( "signature" ) );
        ud.algorithm = map.get( "algorithm" );
        ud.usingBouncyCastle = "bc".equals( map.get( "bc" ) );
        ud.cipherMode = Tools.parseInt( map.get( "mode" ) , 1 );
        ud.key = KeyInfo.getInstance( map.get( "key" ) );
        ud.parameterSpec = KeyInfo.getInstance( map.get( "parameterSpec" ) );
        ud.keysize = Tools.parseInt( map.get( "keysize" ) , -1 );
        return ud;
    }

    public int getKeysize() {
        return keysize;
    }

    public int getCipherMode() {
        return ( cipherMode == ENCRYPT ) || ( cipherMode == PRIVATE_ENCRYPT ) ? Cipher.ENCRYPT_MODE
                : Cipher.DECRYPT_MODE;
    }

    public boolean usingPublicKey() {
        return ( cipherMode == PUBLIC_ENCRYPT || cipherMode == PUBLIC_DECRYPT );
    }

    public boolean isSignature() {
        return ( cipherMode == PRIVATE_SIGNATURE );
    }

    public boolean isEmptyData() {
        return Tools.isEmpty( byteData );
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public boolean isUsingBouncyCastle() {
        return usingBouncyCastle;
    }

    public byte[] getBytesData() {
        return byteData;
    }

    public KeyInfo getKey() {
        return key;
    }

    public KeyInfo getParameterSpec() {
        return parameterSpec;
    }

    public char[] getPasswordChars() {
        if ( Tools.isEmpty( password ) ) {
            return new char[0];
        }
        return password.toCharArray( );
    }

    private static byte[] convertByte(String str) {
        byte[] bys = new byte[0];
        if ( str == null ) {
            return bys;
        }
        if ( str.startsWith( "[" ) && str.endsWith( "]" ) ) {
            bys = Tools.toByte( str.substring( 1 , str.length( ) - 1 ).trim( ) );
        }
        else {
            bys = str.getBytes( UTF_8 );
        }
        return bys;
    }

    public KeyInfo getSignatureData() {
        return signatureData;
    }
}

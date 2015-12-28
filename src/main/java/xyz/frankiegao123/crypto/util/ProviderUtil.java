package xyz.frankiegao123.crypto.util;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ProviderUtil {

    private ProviderUtil() {
    }

    public static void usingBouncyCastle(boolean using) {
        if ( using ) {
            loadBouncyCastle( );
        }
        else {
            removeBouncyCastle( );
        }
    }

    public static void loadBouncyCastle() {
        Provider p = Security.getProvider( "BC" );
        if ( p == null ) {
            Security.insertProviderAt( new BouncyCastleProvider( ) , 1 );
        }
    }

    public static void removeBouncyCastle() {
        Provider p = Security.getProvider( "BC" );
        if ( p != null ) {
            Security.removeProvider( "BC" );
        }
    }
}

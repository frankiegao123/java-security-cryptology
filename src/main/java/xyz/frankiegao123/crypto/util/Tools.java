package xyz.frankiegao123.crypto.util;

public class Tools {

    public static boolean isEmpty(String str) {
        return ( str == null || str.trim( ).length( ) == 0 );
    }

    public static boolean isEmpty(byte[] bys) {
        return ( bys == null || bys.length == 0 );
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt( str );
        }
        catch ( NumberFormatException e ) {
            return defaultValue;
        }
    }

    public static byte[] toByte(String byteStr) {
        if ( isEmpty( byteStr ) ) {
            return new byte[0];
        }
        String[] strs = byteStr.split( " +" );
        byte[] bys = new byte[strs.length];
        for ( int i = 0 ; i < bys.length ; i++ ) {
            bys[i] = (byte) Integer.parseInt( strs[i] , 16 );
        }
        return bys;
    }
}

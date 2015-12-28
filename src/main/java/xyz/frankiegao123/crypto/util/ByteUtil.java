package xyz.frankiegao123.crypto.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Base64;

/**
 * 字节工具类
 * 
 * @author frankiegao123
 * @since 1.0.0
 */
public class ByteUtil {

    private final static char[] HEX = "0123456789abcdef".toCharArray( );

    public static byte[] xor(byte[] data, byte[] key) {
        if ( data == null || key == null ) {
            return data;
        }
        byte[] xor = new byte[key.length];
        int offset = 0;
        while ( offset < data.length ) {
            xor[offset] = (byte) ( data[offset] ^ key[offset] );
            offset++;
        }
        while ( offset < key.length ) {
            xor[offset] = key[offset];
            offset++;
        }
        return xor;
    }

    public static String encodeBase64(byte[] data) {
        return new String( Base64.encodeBase64( data ) );
    }

    public static String decodeBase64(byte[] base64Data, String charset) {
        try {
            return new String( Base64.decodeBase64( base64Data ) , charset );
        }
        catch ( UnsupportedEncodingException e ) {
            throw new IllegalArgumentException( e );
        }
    }

    public static String decodeBase64(byte[] data) {
        return new String( Base64.decodeBase64( data ) );
    }

    /**
     * 将字节数组转成 16 进制的字符串来表示，每个字节采用两个字符表表示，每个字节间采用 一个空格分隔
     * 采用实现较为高效的 bytes2Hex 方法
     * 
     * @param bys 需要转换成 16 进制的字节数组
     * @return
     * @deprecated
     */
    @Deprecated
    public static String bytes2Hex1(byte[] bys) {
        StringBuilder sb = new StringBuilder( );
        for ( int i = 0 ; i < bys.length ; i++ ) {
            if ( i > 0 ) {
                sb.append( " " );
            }
            sb.append( HEX[bys[i] >> 4 & 0xf] );
            sb.append( HEX[bys[i] & 0xf] );
        }
        return sb.toString( );
    }

    public static String byteBuffer2HexSpace(ByteBuffer buffer) {
        buffer.flip( );
        if ( !buffer.hasRemaining( ) ) {
            return null;
        }
        char[] chs = new char[buffer.limit( ) * 3 - 1];
        int offset = 0;
        while ( buffer.hasRemaining( ) ) {
            if ( offset > 0 ) {
                chs[offset++] = ' ';
            }
            byte b = buffer.get( );
            chs[offset++] = HEX[b >> 4 & 0xf];
            chs[offset++] = HEX[b & 0xf];
        }
        buffer.limit( buffer.capacity( ) );
        return new String( chs );
    }

    /**
     * 将字节数组转成 16 进制的字符串来表示，每个字节采用两个字符表表示，每个字节间采用 一个空格分隔
     * 
     * @param bys
     *            需要转换成 16 进制的字节数组
     * @return
     */
    public static String bytes2HexSpace(byte[] bys) {
        char[] chs = new char[bys.length * 2 + bys.length - 1];
        for ( int i = 0 , offset = 0 ; i < bys.length ; i++ ) {
            if ( i > 0 ) {
                chs[offset++] = ' ';
            }
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
        }
        return new String( chs );
    }

    public static String bytes2BinarySpace(byte[] bys) {
        if ( bys == null ) {
            return null;
        }
        if ( bys.length == 0 ) {
            return "";
        }
        char[] chs = new char[bys.length * 9 - 1];
        for ( int i = 0 , k = 0 ; i < bys.length ; i++ ) {
            if ( i > 0 ) {
                chs[k++] = ' ';
            }
            for ( int j = Byte.SIZE - 1 ; j >= 0 ; j-- ) {
                chs[k++] = (char) ( ( ( bys[i] >> j ) & 1 ) + '0' );
            }
        }
        return new String( chs );
    }

    /**
     * 将字节数组转成 16 进制的字符串来表示，每个字节采用两个字符表表示
     * 
     * @param bys
     *            需要转换成 16 进制的字节数组
     * @return
     */
    public static String bytes2Hex(byte[] bys) {
        char[] chs = new char[bys.length * 2];
        for ( int i = 0 , offset = 0 ; i < bys.length ; i++ ) {
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
        }
        return new String( chs );
    }

    /**
     * 将字节数组转成 16 进制的字符串来表示，每个字节采用两个字符表表示，字节间没有分隔 符。<br />
     * 采用实现较为高效的 bytes2Hex 方法
     * 
     * @param bys
     *            需要转换成 16 进制的字节数组
     * @return
     * @deprecated
     */
    @Deprecated
    public static String bytes2Hex2(byte[] bys) {
        StringBuilder sb = new StringBuilder( );
        for ( int i = 0 ; i < bys.length ; i++ ) {
            sb.append( HEX[bys[i] >> 4 & 0xf] );
            sb.append( HEX[bys[i] & 0xf] );
        }
        return sb.toString( );
    }

    public static byte[] int2BytesBE(int num) {
        byte[] bys = new byte[Integer.SIZE / Byte.SIZE];
        for ( int i = 0 , k = bys.length ; i < k ; i++ ) {
            bys[i] = (byte) ( num >>> ( ( k - 1 - i ) * Byte.SIZE ) & 0xff );
        }
        return bys;
    }

    public static byte[] int2BytesLE(int num) {
        return int2BytesBE( Integer.reverseBytes( num ) );
    }

    /**
     * 采用 Big-Endian 方式将 long 数据转为 byte 数组
     * 
     * @param num
     * @return 转为 Big-Endian 方式的 byte 数组
     */
    public static byte[] long2BytesBE(long num) {
        byte[] bys = new byte[Long.SIZE / Byte.SIZE];
        for ( int i = 0 , k = bys.length ; i < k ; i++ ) {
            bys[i] = (byte) ( num >>> ( ( k - 1 - i ) * Byte.SIZE ) & 0xff );
        }
        return bys;
    }

    /**
     * 采用 Little-Endian 方式将 long 数据转为 byte 数组
     * 
     * @param num
     * @return 转为 Little-Endian 方式的 byte 数组
     */
    public static byte[] long2BytesLE(long num) {
        return long2BytesBE( Long.reverseBytes( num ) );
    }

    /**
     * 将 Little-Endian 的字节数组转为 int 类型的数据<br />
     * Little-Endian 表示高位字节在低位索引中
     * 
     * @param bys
     *            字节数组
     * @param start
     *            需要转换的开始索引位数
     * @param len
     *            需要转换的字节数量
     * @return 指定开始位置和长度以 LE 方式表示的 int 数值
     */
    public static int bytes2IntLE(byte[] bys, int start, int len) {
        return bytes2Int( bys , start , len , false );
    }

    /**
     * 将 Big-Endian 的字节数组转为 int 类型的数据<br />
     * Big-Endian 表示高位字节在高位索引中
     * 
     * @param bys
     *            字节数组
     * @param start
     *            需要转换的开始索引位数
     * @param len
     *            需要转换的字节数量
     * @return 指定开始位置和长度以 BE 方式表示的 int 数值
     */
    public static int bytes2IntBE(byte[] bys, int start, int len) {
        return bytes2Int( bys , start , len , true );
    }

    private static int bytes2Int(byte[] bys, int start, int len, boolean isBigEndian) {
        int n = 0;
        for ( int i = start , k = start + len % ( Integer.SIZE / Byte.SIZE + 1 ) ; i < k ; i++ ) {
            n |= ( bys[i] & 0xff ) << ( ( isBigEndian ? ( k - i - 1 ) : i ) * Byte.SIZE );
        }
        return n;
    }

    /**
     * 将 Little-Endian 的字节数组转为 long 类型的数据<br />
     * Little-Endian 表示高位字节在低位索引中
     * 
     * @param bys
     *            字节数组
     * @param start
     *            需要转换的开始索引位数
     * @param len
     *            需要转换的字节数量
     * @return 指定开始位置和长度以 LE 方式表示的 long 数值
     */
    public static long bytes2LongLE(byte[] bys, int start, int len) {
        return bytes2Long( bys , start , len , false );
    }

    public static long bytes2LongLE(byte[] bys) {
        return bytes2Long( bys , 0 , bys.length , false );
    }

    /**
     * 将 Big-Endian 的字节数组转为 long 类型的数据<br />
     * Big-Endian 表示高位字节在高位索引中
     * 
     * @param bys
     *            字节数组
     * @param start
     *            需要转换的开始索引位数
     * @param len
     *            需要转换的字节数量
     * @return 指定开始位置和长度以 BE 方式表示的 long 数值
     */
    public static long bytes2LongBE(byte[] bys, int start, int len) {
        return bytes2Long( bys , start , len , true );
    }

    public static long bytes2LongBE(byte[] bys) {
        return bytes2Long( bys , 0 , bys.length , true );
    }

    private static long bytes2Long(byte[] bys, int start, int len, boolean isBigEndian) {
        long n = 0L;
        for ( int i = start , k = start + len % ( Long.SIZE / Byte.SIZE + 1 ) ; i < k ; i++ ) {
            n |= ( bys[i] & 0xffL ) << ( ( isBigEndian ? ( k - i - 1 ) : i ) * Byte.SIZE );
        }
        return n;
    }
}

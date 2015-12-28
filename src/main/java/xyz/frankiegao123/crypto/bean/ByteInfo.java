package xyz.frankiegao123.crypto.bean;

import xyz.frankiegao123.crypto.util.ByteUtil;
import xyz.frankiegao123.crypto.util.Tools;

public class ByteInfo {

    private byte[] bytes;

    public ByteInfo(byte[] bytes) {
        this.bytes = bytes;
    }

    public boolean isEmpty() {
        return Tools.isEmpty( bytes );
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBase64() {
        if ( isEmpty( ) ) {
            return null;
        }
        return ByteUtil.encodeBase64( bytes );
    }

    public String getHex() {
        if ( isEmpty( ) ) {
            return null;
        }
        return ByteUtil.bytes2HexSpace( bytes );
    }

    public String getString() {
        if ( isEmpty( ) ) {
            return null;
        }
        return new String( bytes );
    }

    public int getLength() {
        return bytes.length;
    }

    public int getBitLength() {
        return bytes.length * 8;
    }
}

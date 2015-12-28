package xyz.frankiegao123.crypto.bean;

import xyz.frankiegao123.crypto.util.Tools;

public class KeyInfo {

    private final byte[] encode;

    private KeyInfo(String keyStr) {
        encode = Tools.toByte( keyStr );
    }

    public static KeyInfo getInstance(String keyStr) {
        KeyInfo info = new KeyInfo( keyStr );
        return info;
    }

    public byte[] getEncode() {
        return encode;
    }

    public boolean isEmpty() {
        return Tools.isEmpty( encode );
    }
}

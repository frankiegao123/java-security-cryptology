package xyz.frankiegao123.crypto.bean;

public class KeyData {

    private byte[] key;
    private byte[] iv;

    public byte[] getKey() {
        return key;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}

package xyz.frankiegao123.crypto.bean;

import java.security.Provider;
import java.util.Set;

public class CryptoData {

    private final Provider           provider;
    private final ByteInfo           crypto;
    private final UserData           ud;
    private ByteInfo                 key;
    private ByteInfo                 parameterSpec;
    private ByteInfo                 privateKey;
    private ByteInfo                 publicKey;
    private Set<ProviderServiceInfo> algorithms;
    private boolean                  verifySignature;

    public CryptoData(byte[] crypto, UserData ud, Provider provider) {
        this.crypto = new ByteInfo( crypto );
        this.ud = ud;
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public ByteInfo getKey() {
        return key;
    }

    public void setKey(ByteInfo key) {
        this.key = key;
    }

    public ByteInfo getParameterSpec() {
        return parameterSpec;
    }

    public void setParameterSpec(ByteInfo parameterSpec) {
        this.parameterSpec = parameterSpec;
    }

    public ByteInfo getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(ByteInfo privateKey) {
        this.privateKey = privateKey;
    }

    public UserData getUd() {
        return ud;
    }

    public ByteInfo getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ByteInfo publicKey) {
        this.publicKey = publicKey;
    }

    public String getAlgorithm() {
        return ud.getAlgorithm( );
    }

    public ByteInfo getCrypto() {
        return crypto;
    }

    public Set<ProviderServiceInfo> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Set<ProviderServiceInfo> algorithms) {
        this.algorithms = algorithms;
    }

    public boolean isVerifySignature() {
        return verifySignature;
    }

    public void setVerifySignature(boolean verifySignature) {
        this.verifySignature = verifySignature;
    }
}

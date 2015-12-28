package xyz.frankiegao123.crypto.bean;

import java.security.Provider.Service;

public class ProviderServiceInfo implements Comparable<ProviderServiceInfo> {

    private final String providerName;
    private final String type;
    private final String algorithm;
    private final String className;
    private final String serviceInfo;
    
    public ProviderServiceInfo(String providerName, Service service) {
        this.providerName = providerName;
        this.type = service.getType();
        this.algorithm = service.getAlgorithm();
        this.className = service.getClassName();
        this.serviceInfo = service.toString();
    }    

    public String getProviderName() {
        return providerName;
    }

    public String getType() {
        return type;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getClassName() {
        return className;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    @Override
    public int compareTo(ProviderServiceInfo o) {
        int n = type.compareTo(o.type);
        if(n != 0) {
            return n;
        }
        n = algorithm.compareTo(o.algorithm);
        if(n != 0) {
            return n;
        }
        return providerName.compareTo(o.providerName);
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %-20s %-35s %-65s %s",
                providerName, truncateType(),
                truncate(algorithm, 35),
                truncate(className, 65),
                serviceInfo.replaceAll("\\s{2,}", " "));
    }
    
    private String truncateType() {
        if("AlgorithmParameterGenerator".equals(type)) {
            return "AlgParamGen...";
        }
        if("AlgorithmParameters".equals(type)) {
            return "AlgParams...";
        }
        return type;
    }
    
    private String truncate(String str, int len) {
        if(str == null) {
            return str;
        }
        if(str.length() < len) {
            return str;
        }
        return className.substring(0, len - 3) + "...";
    }
}

package xyz.frankiegao123.crypto.servlet;

import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.AsymmetricalCrypto;
import xyz.frankiegao123.crypto.service.MacCrypto;
import xyz.frankiegao123.crypto.service.MessageDigestCrypto;
import xyz.frankiegao123.crypto.service.PBECrypto;
import xyz.frankiegao123.crypto.service.ProviderInfoCrypto;
import xyz.frankiegao123.crypto.service.SignatureCrypto;
import xyz.frankiegao123.crypto.service.SymmetricalCrypto;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.util.Tools;

public enum ServiceLocator {

    ProviderInfo {
        public Cryptology getCryptology(UserData ud) {
            return new ProviderInfoCrypto(ud);
        }
    },

    MessageDigest {
        public Cryptology getCryptology(UserData ud) {
            return new MessageDigestCrypto(ud);
        }
    },

    Mac {
        public Cryptology getCryptology(UserData ud) {
            return new MacCrypto(ud);
        }
    },

    SymmetricalCrypto {
        public Cryptology getCryptology(UserData ud) {
            return new SymmetricalCrypto(ud);
        }
    },
    
    PBECrypto {
        public Cryptology getCryptology(UserData ud) {
            return new PBECrypto(ud);
        }
    },

    AsymmetricalCrypto {
        public Cryptology getCryptology(UserData ud) {
            return new AsymmetricalCrypto(ud);
        }
    },
    
    DigitalSignature {
        public Cryptology getCryptology(UserData ud) {
            return new SignatureCrypto(ud);
        }
    }
    ;

    public static ServiceLocator getCryptology(String name) {
        if(Tools.isEmpty(name)) {
            return null;
        }
        ServiceLocator[] services = values();
        for(int i = 0; i < services.length; i++) {
            if(services[i].name().equalsIgnoreCase(name)) {
                return services[i];
            }
        }
        return null;
    }

    public String getPageName() {
        return name() + ".jsp";
    }

    public abstract Cryptology getCryptology(UserData ud);
}

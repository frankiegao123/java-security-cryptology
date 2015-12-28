package xyz.frankiegao123.crypto.service;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.ProviderServiceInfo;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.service.base.UserDataCryptology;

public class ProviderInfoCrypto extends UserDataCryptology implements Cryptology {

    public ProviderInfoCrypto(UserData ud) {
        super( ud );
    }

    @Override
    public CryptoData encrypt() throws Exception {
        Provider[] providers = Security.getProviders( );
        Set<ProviderServiceInfo> all = new TreeSet<ProviderServiceInfo>( );
        boolean isAll = "ALL".equals( ud.getAlgorithm( ) );
        for ( int i = 0 ; i < providers.length ; i++ ) {
            Set<Service> services = providers[i].getServices( );
            String providerName = providers[i].getName( );
            if ( isAll ) {
                for ( Service service : services ) {
                    all.add( new ProviderServiceInfo( providerName , service ) );
                }
            }
            else {
                for ( Service service : services ) {
                    if ( service.getType( ).equals( ud.getAlgorithm( ) ) ) {
                        all.add( new ProviderServiceInfo( providerName , service ) );
                    }
                }
            }
        }
        CryptoData crypto = new CryptoData( null , ud , null );
        crypto.setAlgorithms( all );
        return crypto;
    }
}

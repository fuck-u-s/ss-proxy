package com.iamza.proxyapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/29.
 */

public class OvertureConfigModel {
    private String BindAddress;

    private boolean RedirectIPv6Record = true;

    private boolean DomainBase64Decode = true;

    private String HostsFile="hosts";

    private int MinimumTTL=3600;

    private int CacheSize=4096;

    private boolean OnlyPrimaryDNS = true;

    private List<DNSModel> PrimaryDNS = new ArrayList<>();

    public String getBindAddress() {
        return BindAddress;
    }

    public void setBindAddress(String bindAddress) {
        BindAddress = bindAddress;
    }

    public boolean isRedirectIPv6Record() {
        return RedirectIPv6Record;
    }

    public void setRedirectIPv6Record(boolean redirectIPv6Record) {
        RedirectIPv6Record = redirectIPv6Record;
    }

    public boolean isDomainBase64Decode() {
        return DomainBase64Decode;
    }

    public void setDomainBase64Decode(boolean domainBase64Decode) {
        DomainBase64Decode = domainBase64Decode;
    }

    public String getHostsFile() {
        return HostsFile;
    }

    public void setHostsFile(String hostsFile) {
        HostsFile = hostsFile;
    }

    public int getMinimumTTL() {
        return MinimumTTL;
    }

    public void setMinimumTTL(int minimumTTL) {
        MinimumTTL = minimumTTL;
    }

    public int getCacheSize() {
        return CacheSize;
    }

    public void setCacheSize(int cacheSize) {
        CacheSize = cacheSize;
    }

    public boolean isOnlyPrimaryDNS() {
        return OnlyPrimaryDNS;
    }

    public void setOnlyPrimaryDNS(boolean onlyPrimaryDNS) {
        OnlyPrimaryDNS = onlyPrimaryDNS;
    }

    public List<DNSModel> getPrimaryDNS() {
        return PrimaryDNS;
    }

    public void setPrimaryDNS(List<DNSModel> primaryDNS) {
        PrimaryDNS = primaryDNS;
    }
}

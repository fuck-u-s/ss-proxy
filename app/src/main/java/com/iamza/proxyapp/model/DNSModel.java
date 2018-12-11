package com.iamza.proxyapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/29.
 */

public class DNSModel {

    private String Name;

    private String Address;

    private int Timeout;

    private Map<String,String> EDNSClientSubnet;

    private String Protocol;

    private String Socks5Address;

    public DNSModel() {
        EDNSClientSubnet = new HashMap<String,String>();
        EDNSClientSubnet.put("Policy","disable");
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    public Map<String, String> getEDNSClientSubnet() {
        return EDNSClientSubnet;
    }

    public void setEDNSClientSubnet(Map<String, String> EDNSClientSubnet) {
        this.EDNSClientSubnet = EDNSClientSubnet;
    }

    public String getProtocol() {
        return Protocol;
    }

    public void setProtocol(String protocol) {
        Protocol = protocol;
    }

    public String getSocks5Address() {
        return Socks5Address;
    }

    public void setSocks5Address(String socks5Address) {
        Socks5Address = socks5Address;
    }
}

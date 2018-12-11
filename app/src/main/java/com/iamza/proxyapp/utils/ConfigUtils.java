package com.iamza.proxyapp.utils;

/**
 * Created by lenovo on 2017/6/29.
 */

public class ConfigUtils {

    public static String REDSOCKS = "base {\n" +
            " log_debug = off;\n" +
            " log_info = off;\n" +
            " log = stderr;\n" +
            " daemon = off;\n" +
            " redirector = iptables;\n" +
            "}\n" +
            "redsocks {\n" +
            " local_ip = 127.0.0.1;\n" +
            " local_port = 8123;\n" +
            " ip = 127.0.0.1;\n" +
            " port = %s;\n" +
            " type = socks5;\n" +
            "}\n";




}

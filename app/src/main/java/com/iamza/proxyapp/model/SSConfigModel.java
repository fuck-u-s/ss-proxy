package com.iamza.proxyapp.model;

import com.google.gson.annotations.Expose;

/**
 * Created by lenovo on 2017/6/29.
 */

public class SSConfigModel {

    private String server;

    private String server_port;

    private String password="";

    private String method="";

    private String plugin;

    private String plugin_opts;

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getPlugin_opts() {
        return plugin_opts;
    }

    public void setPlugin_opts(String plugin_opts) {
        this.plugin_opts = plugin_opts;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer_port() {
        return server_port;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

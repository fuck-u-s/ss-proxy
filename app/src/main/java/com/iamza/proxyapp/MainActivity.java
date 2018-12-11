package com.iamza.proxyapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.iamza.proxyapp.model.SSConfigModel;
import com.iamza.proxyapp.receiver.ProxyReceiver;
import com.xa.proxyapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProxyReceiver.prosy(this,toJson(this,"trace.thws.ltd","5000"));
/*        int[] ids = InputDevice.getDeviceIds();
        int deviceId=0;
        for (int id : ids) {
            InputDevice device = InputDevice.getDevice(id);
            if (device != null) {
                String name = device.getName();
                Log.e("deviceId=====",name+"="+id);
                if ((device.getSources() & InputDevice.SOURCE_TOUCHSCREEN) == InputDevice.SOURCE_TOUCHSCREEN) {
                    deviceId = id;
                }
            }
        }*/
//        Log.e("deviceId=====","correct did:"+deviceId+"");
//        Intent intent = new Intent("xa.switch.proxy");
//        sendBroadcast(intent);
        /*UtilsFile.writeFile(this.getFilesDir()+"/redsocks-nat.conf", String.format(Locale.ENGLISH,ConfigUtils.REDSOCKS,"1080"));

        OvertureConfigModel overtureConfigModel = new OvertureConfigModel();
        overtureConfigModel.setBindAddress("127.0.0.1:1133");
        DNSModel dnsModel = new DNSModel();
        dnsModel.setName("UserDef-0");
        dnsModel.setAddress("119.29.29.29:53");
        dnsModel.setTimeout(6);
        dnsModel.setProtocol("tcp");
        dnsModel.setSocks5Address("127.0.0.1:1080");
        overtureConfigModel.getPrimaryDNS().add(dnsModel);


        String overConfig = gson.toJson(overtureConfigModel);
        UtilsFile.writeFile(this.getFilesDir()+"/ov.conf", String.format(Locale.ENGLISH,overConfig));*/
        ///////////////////////////////////////////////////////////////////
        /*UtilsShell.execCommand("killall libredsocks.so",true);
        UtilsShell.execCommand("killall libss-local.so",true);
        UtilsShell.execCommand("killall liboverture.so",true);
        Gson gson = new Gson();
        SSConfigModel ssConfigModel = new SSConfigModel();
        ssConfigModel.setServer("107.163.216.12");
        ssConfigModel.setServer_port("20010");
        ssConfigModel.setPassword("abc!@#123d");
        ssConfigModel.setMethod("aes-256-cfb");
        ssConfigModel.setPlugin(this.getFilesDir().getAbsoluteFile()+"/libkcptun.so");
        ssConfigModel.setPlugin_opts("key=Iamza2015;mode=fast;crypt=aes");
        String ss = gson.toJson(ssConfigModel);

        Intent intent = new Intent("xa.proxy");
        intent.putExtra("proxy",ss);
        sendBroadcast(intent);*/
        /*Gson g = new GsonBuilder().create();
        String st = g.toJson(g);
        System.out.println(st);*/
        //////////////////////////
        //sendBroadcast(intent);

        /*UtilsFile.writeFile(this.getFilesDir()+"/ss-local-nat.conf", String.format(Locale.ENGLISH,ss));

        FileUtil.copyFromAsset(this,"liboverture.so",this.getFilesDir().getAbsolutePath(),"liboverture.so");
        UtilsShell.execCommand(String.format("chmod 755 %s",this.getFilesDir()+"/liboverture.so"),true);
        FileUtil.copyFromAsset(this,"libredsocks.so",this.getFilesDir().getAbsolutePath(),"libredsocks.so");
        UtilsShell.execCommand(String.format("chmod 755 %s",this.getFilesDir()+"/libredsocks.so"),true);

        FileUtil.copyFromAsset(this,"libss-local.so",this.getFilesDir().getAbsolutePath(),"libss-local.so");
        UtilsShell.execCommand(String.format("chmod 755 %s",this.getFilesDir()+"/libss-local.so"),true);

        FileUtil.copyFromAsset(this,"libss-tunnel.so",this.getFilesDir().getAbsolutePath(),"libss-tunnel.so");
        UtilsShell.execCommand(String.format("chmod 755 %s",this.getFilesDir()+"/libss-tunnel.so"),true);

        FileUtil.copyFromAsset(this,"libtun2socks.so",this.getFilesDir().getAbsolutePath(),"libtun2socks.so");
        UtilsShell.execCommand(String.format("chmod 755 %s",this.getFilesDir()+"/libtun2socks.so"),true);

        UtilsShell.execCommand("killall libredsocks.so",true);
        UtilsShell.execCommand("killall libss-local.so",true);
        UtilsShell.execCommand("killall liboverture.so",true);
        String cmd = String.format("nohup %s/libredsocks.so -c %s/redsocks-nat.conf > /dev/null 2>&1 &",this.getFilesDir(),this.getFilesDir());
        System.out.println(cmd);
        UtilsShell.execCommand(cmd,true);
        UtilsShell.execCommand(String.format("nohup %s/libss-local.so -b 127.0.0.1 -l 1080 -t 600 -c %s/ss-local-nat.conf > /dev/null 2>&1 &",this.getFilesDir(),this.getFilesDir()),true);
        UtilsShell.execCommand(String.format("nohup %s/liboverture.so -c %s/ov.conf > /dev/null 2>&1 &",this.getFilesDir(),this.getFilesDir()),true);

        UtilsShell.execCommand("iptables -t nat -F OUTPUT",true);
        UtilsShell.execCommand("iptables -t nat -A OUTPUT -p tcp -d 23.231.158.156 -j RETURN",true);
        UtilsShell.execCommand("iptables -t nat -A OUTPUT -p tcp -d 127.0.0.1 -j RETURN",true);
        UtilsShell.execCommand("iptables -t nat -A OUTPUT -p tcp --dport 53 -j RETURN",true);
        UtilsShell.execCommand("iptables -t nat -A OUTPUT -p udp --dport 53 -j DNAT --to-destination 127.0.0.1:1133",true);
        UtilsShell.execCommand("iptables -t nat -A OUTPUT -p tcp -j DNAT --to-destination 127.0.0.1:8123",true);*/

    }

    public static String toJson(Context context, String sip, String sport){
        Gson gson = new Gson();
        SSConfigModel ssConfigModel = new SSConfigModel();
        ssConfigModel.setServer(sip);
        ssConfigModel.setServer_port(sport);
        ssConfigModel.setPassword("abc!@#123d");
        ssConfigModel.setMethod("aes-256-cfb");
//        ssConfigModel.setPlugin(context.getFilesDir().getAbsoluteFile()+"/libkcptun.so");
//        ssConfigModel.setPlugin_opts("key=Iamza2015;mode=fast;crypt=aes");
        return gson.toJson(ssConfigModel);
    }
    public static String toJson(Context context, String sip, String sport,String user,String passwd){
        Gson gson = new Gson();
        SSConfigModel ssConfigModel = new SSConfigModel();
        ssConfigModel.setServer(sip);
        ssConfigModel.setServer_port(sport);
        ssConfigModel.setPassword(passwd!=null?passwd:"");
        ssConfigModel.setMethod(user!=null?user:"");
//        ssConfigModel.setPlugin(context.getFilesDir().getAbsoluteFile()+"/libkcptun.so");
//        ssConfigModel.setPlugin_opts("key=Iamza2015;mode=fast;crypt=aes");
        return gson.toJson(ssConfigModel);
    }

    public static String loadFromRemote(String url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    buffer.append(str);
                }
                return buffer.toString();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int deviceId=event.getDeviceId();
        Log.e("deviceId=====","real did:"+deviceId+"");
        return super.onTouchEvent(event);
    }
}

package com.iamza.proxyapp.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.iamza.proxyapp.MainActivity;
import com.iamza.proxyapp.model.SSConfigModel;
import com.iamza.proxyapp.utils.FileUtil;
import com.iamza.proxyapp.utils.UtilsShell;

/**
 * Created by lenovo on 2017/6/29.
 */

public class ProxyReceiver extends BroadcastReceiver {

    private static final String PROXY_ACTION = "xa.proxy";


    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        String proxy = intent.getStringExtra("proxy");
        if(action.equals("xa.info")){
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Log.d("hujian","xxxxxxxxxxxx");
            return;
        }else if("xa.switch.proxy".equals(action)){
            String sport=intent.getStringExtra("sport");
            String ip=intent.getStringExtra("ip");
            String user=intent.getStringExtra("user");
            String passwd=intent.getStringExtra("passwd");
            if(ip!=null&&sport!=null&&ip.trim().length()>5){
                proxy=MainActivity.toJson(context,ip,sport,user,passwd);
                action=PROXY_ACTION;
                Log.d("zpnet","change ip to "+ip+":"+sport);
            }
        }
        if(PROXY_ACTION.equals(action)){
            prosy(context, proxy);

        }
    }

    public static void prosy1(final Context context, String proxy) {
        Gson gson = new Gson();
        final SSConfigModel model = gson.fromJson(proxy,SSConfigModel.class);

        new Thread(){
            @Override
            public void run() {
                Log.d("zpnet","id : "+ UtilsShell.execCommand("id",true,true).successMsg);
                String dp=context.getFilesDir().getAbsolutePath();

                Log.d("zpnet","change ip to "+model.getServer()+":"+model.getServer_port()+" "+dp);
                FileUtil.copyFromAsset(context,"kcp.json",context.getFilesDir().getAbsolutePath(),"kcp.json");
                FileUtil.copyFromAsset(context,"ngov",context.getFilesDir().getAbsolutePath(),"ngov");
                FileUtil.copyFromAsset(context,"ngov.sh",context.getFilesDir().getAbsolutePath(),"ngov.sh");
                FileUtil.copyFromAsset(context,"ov.conf",context.getFilesDir().getAbsolutePath(),"ov.conf");

                Log.d("zpnet",String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov"));
                UtilsShell.execCommand(String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov"),true);
                UtilsShell.execCommand(String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov.sh"),true);
                UtilsShell.execCommand("chmod -R 755 "+context.getFilesDir().getAbsolutePath(),true);

//                ngov "-L=:10100" "-F=ss://aes-256-cfb:abc!%40%23123d@$IP:$PORT
                String shell = String.format("nohup %s -L=:10100 '-F=ss://aes-256-cfb:abc!@#123d@%s:%s' >/dev/null 2>&1 &", context.getFilesDir().getAbsolutePath() + "/ngov", model.getServer(), model.getServer_port());
                Log.d("zpnet","shell: "+shell);
                UtilsShell.execCommand(shell,true);
            }
        }.start();
    }

    public static void prosy(final Context context, String proxy) {
        Gson gson = new Gson();
        final SSConfigModel model = gson.fromJson(proxy,SSConfigModel.class);

        new Thread(){
            @Override
            public void run() {
                Log.d("zpnet","id : "+ UtilsShell.execCommand("id",false,true).successMsg);
                String dp=context.getFilesDir().getAbsolutePath();

                Log.d("zpnet","change ip to "+model.getServer()+":"+model.getServer_port()+" "+dp);
                FileUtil.copyFromAsset(context,"kcp.json",context.getFilesDir().getAbsolutePath(),"kcp.json");
                FileUtil.copyFromAsset(context,"ngov",context.getFilesDir().getAbsolutePath(),"ngov");
                FileUtil.copyFromAsset(context,"ngov.sh",context.getFilesDir().getAbsolutePath(),"ngov.sh");
                FileUtil.copyFromAsset(context,"ov.conf",context.getFilesDir().getAbsolutePath(),"ov.conf");

                Log.d("zpnet",String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov"));
                UtilsShell.execCommand(String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov"),true);
                UtilsShell.execCommand(String.format("chmod 755 %s",context.getFilesDir().getAbsolutePath()+"/ngov.sh"),true);
                UtilsShell.execCommand("chmod -R 755 "+context.getFilesDir().getAbsolutePath(),true);
                Log.d("zpnet",String.format("%s %s %s",context.getFilesDir().getAbsolutePath()+"/ngov.sh",model.getServer(),model.getServer_port()));

                String[] pkgs=new String[]{"com.facebook.katana","com.android.browser","com.facebook.orca"};
                StringBuffer uidSb = new StringBuffer();
                for(String pkg:pkgs){
                    int uid=getUidByPackageName(context,pkg);
                    if(uid>0){
                        uidSb.append(uid).append(" ");
                    }
                }
                String uids=uidSb.toString().trim();

                String shell = String.format("nohup %s %s %s \"%s\" \"%s\" \"%s\">/dev/null 2>&1 &", context.getFilesDir().getAbsolutePath() + "/ngov.sh", model.getServer(), model.getServer_port(), uids,model.getMethod(),model.getPassword());
                Log.d("zpnet","shell: "+shell);
                UtilsShell.execCommand(shell,true);
            }
        }.start();
    }

    private static void waitSeconds(int second) {
        try{
            Thread.sleep(second*1000);
        }catch (Throwable ex){

        }
    }

    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        try {
            @SuppressLint("WrongConstant") ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
            uid = info.uid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uid;
    }
}

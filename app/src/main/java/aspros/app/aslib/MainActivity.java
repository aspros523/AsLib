package aspros.app.aslib;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;

import org.apache.commons.lang3.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;

import aspros.app.aslib.view.FlipableView;

public class MainActivity extends AppCompatActivity
{

    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private static final int MY_REQUEST_CODE = 9999;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView()
    {
        Button button0 = (Button) findViewById(R.id.btn0);
        Button button1 = (Button) findViewById(R.id.btn1);
        Button button2 = (Button) findViewById(R.id.btn2);
        Button button3 = (Button) findViewById(R.id.btn3);

        button0.setOnClickListener(v -> restart());
        button1.setOnClickListener(v -> hibernate());
        button2.setOnClickListener(v -> wakeup());
        button3.setOnClickListener(v -> shutdown());
    }

    public void restart()
    {
        try
        {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream out = new DataOutputStream(process.getOutputStream());
            out.writeBytes("reboot \n");
            out.writeBytes("exit\n");
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        ToastUtil.showShort(this, keyCode + "");
        String s = getCardInfo();
        ToastUtil.showShort(this, "卡号：" + s);
        return super.onKeyDown(keyCode, event);

    }

    public String getCardInfo()
    {
        String resultStr = null;
        //        LogUtil.d(Thread.currentThread().getName());
        try
        {
            TvManager.getInstance().setTvosInterfaceCommand("GetCardUid");
            String str = TvManager.getInstance().getTvosInterfaceCommand();
            if (StringUtils.isEmpty(str))
            {
                return null;
            }
            int index = str.lastIndexOf(":");
            resultStr = str.substring(index);
            resultStr = resultStr.replace(":", "");
        }
        catch (TvCommonException e)
        {
            e.printStackTrace();
        }
        return resultStr;
    }


    public void hibernate()
    {

        //
        //        try
        //        {
        //            Process process = Runtime.getRuntime().exec("su");
        //            DataOutputStream out = new DataOutputStream(process.getOutputStream());
        //            out.writeBytes("echo mem > /sys/power/state \n");
        //            out.writeBytes("exit\n");
        //            out.flush();
        //        }
        //        catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }

//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, getClass()
//                .getCanonicalName());
//        if (null != wakeLock)
//        {
//            wakeLock.acquire();
//        }


                policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                componentName = new ComponentName(this, AdminReceiver.class);
                if (policyManager.isAdminActive(componentName))
                {
                    policyManager.lockNow();
                }
                else
                {
                    activeManage();
                }

    }

    //获取权限
    private void activeManage()
    {
        // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才能使用锁屏功能哦亲^^");

        startActivityForResult(intent, MY_REQUEST_CODE);
    }


    public void wakeup()
    {
        //        try
        //        {
        //            Process process = Runtime.getRuntime().exec("su");
        //            DataOutputStream out = new DataOutputStream(process.getOutputStream());
        //            out.writeBytes("echo on > /sys/power/state \n");
        //            out.writeBytes("exit\n");
        //            out.flush();
        //        }
        //        catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }
        //
        //        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        //        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
        //        keyguardLock.disableKeyguard();

        Toast.makeText(this, "10秒", Toast.LENGTH_SHORT).show();

//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent("com.android.settings.action.REQUEST_POWER_ON");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(10000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        wake(true);
                    }
                }).start();

    }


    public void shutdown()
    {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.android.settings.action.REQUEST_POWER_ON");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + 20000, pendingIntent);

        try
        {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            process.waitFor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void wake(boolean b)
    {
        Log.i("wake", "sssssss");
        if (b)
        {
            //获取电源管理器对象
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

            //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

            //点亮屏幕
            wl.acquire();

            //得到键盘锁管理器对象
            KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");

            //解锁
            kl.disableKeyguard();
        }

    }

}

package aspros.app.aslib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aspros on 2017/10/23.
 */

public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Log.d("MyTag", "onclock......................");
        String msg = intent.getAction();
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

}

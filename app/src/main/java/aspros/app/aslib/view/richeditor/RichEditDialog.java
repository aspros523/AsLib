package aspros.app.aslib.view.richeditor;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import aspros.app.aslib.R;

/**
 * Created by aspros on 2017/5/5.
 */

public class RichEditDialog extends Dialog
{
    View view;
    public RichEditDialog(@NonNull Context context)
    {
        super(context, R.style.bottom_dialog);
        view= LayoutInflater.from(context).inflate(R.layout.dialog_rich_edit,null);
        setContentView(view);
        Window window = getWindow();
        Display display = window.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = display.getWidth();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}

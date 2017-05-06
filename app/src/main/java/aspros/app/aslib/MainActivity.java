package aspros.app.aslib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView()
    {
        findViewById(R.id.xrecycler).setOnClickListener(v ->
        {
            Intent intent=new Intent(MainActivity.this,XRecyclerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.richedit_tv).setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,RichEditActivity.class);
            startActivity(intent);
        });
    }

}

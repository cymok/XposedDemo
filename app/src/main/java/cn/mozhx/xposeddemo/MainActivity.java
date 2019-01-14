package cn.mozhx.xposeddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        isActivating(false);

    }

    private void isActivating(boolean activating) {
        ImageView imageView = (ImageView) findViewById(R.id.iv);
        String text;
        if (activating) {
            imageView.setImageResource(R.drawable.qy);
            text = "activating";
        } else {
            imageView.setImageResource(0);
            text = "no activating";
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}

package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 27/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainList = new Intent(this, SpaceListActivity.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.finish();
                startActivity(mainList);
            }
        }, 1000);
    }
}

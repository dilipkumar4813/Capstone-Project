package iamdilipkumar.com.spacedig.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class FullDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        ButterKnife.bind(this);
    }
}

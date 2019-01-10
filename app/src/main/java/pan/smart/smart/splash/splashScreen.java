package pan.smart.smart.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import pan.smart.smart.Login.loginActivity;
import pan.smart.smart.R;

public class splashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        },2000);
        setContentView(R.layout.activity_splash_screen);


    }

}

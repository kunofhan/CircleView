package demo.dagger.com.myview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ControlView controlView = findViewById(R.id.control_view);

        controlView.setOnControlViewClickListener(new ControlView.OnControlViewClickListener() {
            @Override
            public void onAddClick() {
                System.out.println("add clicked");
            }

            @Override
            public void onReduceClick() {
                System.out.println("reduce clicked");
            }

            @Override
            public void onRideClick() {
                System.out.println("ride clicked");
            }

            @Override
            public void onExceptClick() {
                System.out.println("except clicked");
            }

            @Override
            public void onOKClick() {
                Toast.makeText(MainActivity.this, "ok clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

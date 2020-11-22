package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.convenientrate.ConvenientRate;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConvenientRate.Rate(MainActivity.this
                , new ConvenientRate.CallBack_UserRating() {
                    @Override
                    public void userRating(int rating,String msg) {

                        Log.d("111",msg);
                        Log.d("111", String.valueOf(rating));
                    }
                }
        );


    }
}
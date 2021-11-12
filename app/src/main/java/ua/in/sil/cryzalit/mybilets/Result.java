package ua.in.sil.cryzalit.mybilets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class Result extends AppCompatActivity {
    String number;
    String n;
    int no;
    SharedPreferences preferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int totalCount;
    String l;
    String gplay;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String err0r = intent.getStringExtra("error");
        int error = Integer.parseInt(err0r);
        final int right = 20 - error;
        number = intent.getStringExtra("number");
        no = Integer.parseInt(number);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(number,right+"");
        editor.apply();
        totalCount = preferences.getInt("counter", 1);
        totalCount++;
        editor.putInt("counter", totalCount);
        editor.commit();
        l = preferences.getString("language", "");
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-1979562813095678/5941445792", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(Result.this);
                } else if (totalCount==30||totalCount==40){
                    if (l.equals("r_")) {
                        gplay = "Вы уже прошли более половины билетов, оставьте пожалуйста отзыв об этом приложении в Google Play. Спасибо.";
                    }
                    else {
                        gplay = "Ви вже пройшли більше половини білетів, залиште будь ласка відгук про цей приложении в Google Play. Дякую..";

                    }
                    Toast toast = Toast.makeText(getApplicationContext(),
                            gplay, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(
                            "http://play.google.com/store/apps/details?id=ua.in.sil.cryzalit.mybilets"));
                    startActivity(intent);
                }



            }
        }, 10000);

        if (no == 28) {
            no = 29;
        } else if (no == 66) {
            no = 67;
        } else if (no == 12) {
            no = 13;

    } else if (no == 68) {
        no = 0;
    }
        n = String.valueOf(no + 1);
        TextView er = findViewById(R.id.ers);
        er.setTextScaleX(1.35f);
        String green = String.valueOf(right);
        if (error >= 7) {
            er.setTextColor(Color.RED);

        }
        if (error < 7) {
            er.setTextColor(Color.YELLOW);

        }
        if (error < 3) {
            er.setTextColor(Color.GREEN);

        }

        er.setText(green + " / " + "20");

        if (Build.VERSION.SDK_INT >= 25) {
            er.setTextSize(80);
            er.setTextScaleX(1.3f);
            er.setText(green + " / " + "20");

        }

        Button nxtbtn = findViewById(R.id.nextbnt);
        Button table = findViewById(R.id.tableresult);
        Button ext = findViewById(R.id.extbtn);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String l = preferences.getString("language", "");
        if (l.equals("r_")) {
            nxtbtn.setText(R.string.r_next_test);
            table.setText(R.string.r_table_result);
            ext.setText(R.string.r_exit);
        }
        else {
            nxtbtn.setText(R.string.next_test);
            table.setText(R.string.table_result);
            ext.setText(R.string.exit);
        }

        View.OnClickListener myOnlyhandler = new View.OnClickListener() {
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.nextbnt:
                        Intent MainIntent = new Intent(Result.this, MainActivity.class);
                        MainIntent.putExtra("currentno", n);
                        Result.this.startActivity(MainIntent);
                        finish();
                        break;

                    case R.id.tableresult:
                        Intent tableintent = new Intent(Result.this, TableResult.class);
                        Result.this.startActivity(tableintent);
                        // it was the second button
                        break;
                    case R.id.extbtn:
                        goExit();
                        return;


                }
            }
        };
        nxtbtn.setOnClickListener(myOnlyhandler);
        ext.setOnClickListener(myOnlyhandler);
        table.setOnClickListener(myOnlyhandler);

    }

    private void goExit() {
        // Show the next level and reload the ad to prepare for the level after.
        Intent intent = new Intent(Result.this, Launch.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }



}

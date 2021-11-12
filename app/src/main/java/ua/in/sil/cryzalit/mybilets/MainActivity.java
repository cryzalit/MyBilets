package ua.in.sil.cryzalit.mybilets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity  {

    private byte err = 0;
    private byte answers = 0;
    private int no;
    String error;
    private String number;
    private int[][] small = new int[21][6];
    private TextView[] qst = new TextView[21];
    ImageView[] im = new ImageView[21];
    RadioGroup[] g = new RadioGroup[21];


    private float mScale = 1f;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector gestureDetector;

    RadioButton[][] buttons = new RadioButton[21][6];
    public int[] q = new int[21];
    public int[][] varianty = new int[21][6];
    Drawable[][] img = new Drawable[21][6];

    String[][] vr = new String[21][6];
    int[] im_ = new int[21];
    Answers ans;
    MyTask mt;
    private AdView mAdView10;
    private AdView mAdView20;
    SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String l;
    boolean ngt;
    ScrollView layout;

    private InterstitialAd mInterstitialAd;




    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        layout = findViewById(R.id.scrollView1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ngt= false;
        if (intent.hasExtra("currentno")) {
            number = intent.getExtras().getString("currentno");

        } else {

            Random r = new Random();
            no = r.nextInt(68 - 1) + 1;
            if (no == 29) {
                no = 30;
            } else if (no == 67) {
                no = 68;
            } else if (no == 13) {
                no = 14;
            }
            number = no + "";
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("report")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("report", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("e_data", "Error getting documents: ", task.getException());
                        }
                    }
                });

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getResources().getDrawable(R.drawable.g_bar));
        preferences= getSharedPreferences("pref", MODE_PRIVATE);
        ngt = preferences.getBoolean("night1", false);
        l = preferences.getString("language", "");

        if (l.equals("r_")) {
            this.setTitle("Билет №" + number);
        } else {
            this.setTitle("Білет №" + number);
        }


        mAdView10 = findViewById(R.id.add10);
        mAdView20 = findViewById(R.id.add20);
        error = err + " помилок";
        Answers ans = new Answers();
        ans.setNo(Integer.parseInt(number));
        mt = new MyTask();
         mt.execute();
        gestureDetector = new GestureDetector(this, new GestureListener());
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();

                float prevScale = mScale;
                mScale += scale;

                if (mScale < 0.3f) // Minimum scale condition:
                    mScale = 0.3f;

                if (mScale > 1f) // Maximum scale condition:
                    mScale = 1f;
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                layout.startAnimation(scaleAnimation);
                return true;

            }
        });
    }


        class MyTask extends AsyncTask<Void, Void, Void> {


            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView10.loadAd(adRequest);
                mAdView20.loadAd(adRequest);
                actionB();
                actionC();
                if (ngt) {
                    actionCd();
                } else {

                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Thread thread = new Thread(){
                            @Override
                            public void run(){
                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(MainActivity.this);
                                } else {
                                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                }
                            }
                        };
                        thread.run();
                    }

                }, 500000);
            }
        }

    public void actionB() {
        for (int i = 1; i < 21; i++) {
            im_[i] = this.getResources().getIdentifier("im" + i + "_" + number, "drawable", this.getPackageName());
            int mID = this.getResources().getIdentifier("im" + i, "id", this.getPackageName());
            im[i] = findViewById(mID);
            if (im_[i] != 0) {

                try {

                    Picasso.with(this)
                            .load(im_[i])
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(im[i]);

                } catch (NullPointerException | Resources.NotFoundException | OutOfMemoryError ex ) {
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void actionC() {

        for (int i = 1; i < 21; i++) {
            final int gr = i;
            layout.setBackgroundColor(Color.parseColor("#fafafa"));
            for (int j = 1; j < 6; j++) {

                varianty[i][j] = this.getResources().getIdentifier(l+"a" + j + "_" + i + "_" + number, "string", this.getPackageName());
                int aID = this.getResources().getIdentifier("a" + j + i, "id", this.getPackageName());
                buttons[i][j] = findViewById(aID);
                Typeface font = ResourcesCompat.getFont(this, R.font.tahoma);
                buttons[i][j].setTypeface(font);
                buttons[i][j].setTextColor(getResources().getColor(R.color.Black));
                buttons[i][j].setTextSize(18);
                small[i][j] = this.getResources().getIdentifier("im" + j + "_" + i + "_" + number, "drawable", this.getPackageName());
                if (varianty[i][j] == 0 && small[i][j] == 0) {
                    buttons[i][j].setVisibility(View.GONE);

                } else {
                    try {
                        buttons[i][j].setText(varianty[i][j]);
                        vr[i][j] = getResources().getString(varianty[i][j]);
                    } catch (NullPointerException | Resources.NotFoundException ex) {
                        Log.d("log", i + j + "");


                    }
                    try {

                        img[i][j] = getResources().getDrawable(small[i][j]);
                        buttons[i][j].setCompoundDrawablesWithIntrinsicBounds(img[i][j], null, null, null);

                    } catch (NullPointerException | Resources.NotFoundException ex) {
                        Log.d("log", i + j + "");

                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        buttons[i][j].setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.Black)));
                    }
                    else{
                        buttons[i][j].setHighlightColor(getResources().getColor(R.color.Black));
                    }
                }
            }
            q[i] = this.getResources().getIdentifier(l+"q" + i + "_" + number, "string", this.getPackageName());
            int qID = this.getResources().getIdentifier("q" + i, "id", this.getPackageName());
            qst[i] = findViewById(qID);
            qst[i].setTextSize(32);
            qst[i].setTextColor(getResources().getColor(R.color.Black));
            try {

                qst[i].setText(q[i]);

            } catch (NullPointerException | Resources.NotFoundException ex) {
                Log.d("log_ask", i +  "");

            }
            int gID = this.getResources().getIdentifier("g" + i, "id", this.getPackageName());
            g[gr] = findViewById(gID);

            g[gr].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int index = g[gr].indexOfChild(findViewById(g[gr].getCheckedRadioButtonId()));
                    ((RadioButton) g[gr].getChildAt(index)).setChecked(true);
                    int tru = ans.ANS[gr] - 1;
                    answers++;
                    if (ans.ANS[gr] == index + 1) {
                        g[gr].getChildAt(index).setBackgroundResource(R.drawable.g_text);
                        ((RadioButton) g[gr].getChildAt(tru)).setTypeface(null, Typeface.BOLD);

                    } else {
                        g[gr].getChildAt(index).setBackgroundResource(R.drawable.g_text2);
                        g[gr].getChildAt(tru).setBackgroundResource(R.drawable.g_text);
                        ((RadioButton) g[gr].getChildAt(tru)).setTypeface(null, Typeface.BOLD);

                        err++;
                        String error = err + " помилка(-ки)";

                        Toast toast = Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView messageTextView = (TextView) group.getChildAt(0);
                        toast.show();

                    }
                    if (answers == 20) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(1700);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    Intent myIntent = new Intent(MainActivity.this, Result.class);
                                    myIntent.putExtra("error", String.valueOf(err));
                                    myIntent.putExtra("number", number);
                                    finish();
                                    MainActivity.this.startActivity(myIntent);
                                }
                            }
                        };
                        thread.start();

                    }

                    String answer = answers + "";
                    Log.d("answer", answer);
                    buttons[gr][1].setEnabled(false);
                    buttons[gr][2].setEnabled(false);
                    buttons[gr][3].setEnabled(false);
                    buttons[gr][4].setEnabled(false);
                    buttons[gr][5].setEnabled(false);
                    g[gr].getChildAt(index).setEnabled(true);

                }

            });
        }

    }
    public void actionCd() {

        for (int i = 1; i < 21; i++) {

            for (int j = 1; j < 6; j++) {
                buttons[i][j].setTextColor(getResources().getColor(R.color.White));
                buttons[i][j].setHighlightColor(getResources().getColor(R.color.White));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buttons[i][j].setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.White)));
                }
                buttons[i][j].setHighlightColor(getResources().getColor(R.color.White));
            }
            qst[i].setTextColor(getResources().getColor(R.color.White));
        }
        layout.setBackgroundColor(Color.parseColor("#008080"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (l.equals("r_")) {
            menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.icon_restart), "Заново"));
            menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.icon_table), getResources().getString(R.string.r_table_result)));
            menu.add(1, 4, 4, menuIconWithText(getResources().getDrawable(R.drawable.icon_pdr), getResources().getString(R.string.r_pdr)));
            menu.add(1, 5, 5, menuIconWithText(getResources().getDrawable(R.drawable.icon_pdr_ru), getResources().getString(R.string.rr_pdr)));
            menu.add(0, 6, 6, menuIconWithText(getResources().getDrawable(R.drawable.icon_lagnuage), "Изменить язык (UA)"));
            menu.add(0, 7, 7, menuIconWithText(getResources().getDrawable(R.drawable.icon_index), getResources().getString(R.string.r_index)));
            menu.add(0, 8, 8, menuIconWithText(getResources().getDrawable(R.drawable.icon_night), getResources().getString(R.string.r_dark_mode)));
            menu.add(0, 9, 9, menuIconWithText(getResources().getDrawable(R.drawable.icon_gplay), getResources().getString(R.string.r_review)));
            menu.add(0, 10, 10, menuIconWithText(getResources().getDrawable(R.drawable.icon_bug), getResources().getString(R.string.r_report)));
            menu.add(0, 11, 11, menuIconWithText(getResources().getDrawable(R.drawable.icon_exit), getResources().getString(R.string.r_exit)));
            // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_patient_home_screen, menu);

        }
        else {
            menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.icon_restart), "Заново"));
            menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.icon_table), getResources().getString(R.string.table_result)));
            menu.add(0, 4, 4, menuIconWithText(getResources().getDrawable(R.drawable.icon_pdr), getResources().getString(R.string.pdr)));
            menu.add(0, 6, 6, menuIconWithText(getResources().getDrawable(R.drawable.icon_lagnuage), "Змінити мову (RU)"));
            menu.add(0, 7, 7, menuIconWithText(getResources().getDrawable(R.drawable.icon_index), getResources().getString(R.string.index)));
            menu.add(0, 8, 8, menuIconWithText(getResources().getDrawable(R.drawable.icon_night), getResources().getString(R.string.dark_mode)));
            menu.add(0, 9, 9, menuIconWithText(getResources().getDrawable(R.drawable.icon_gplay), getResources().getString(R.string.review)));
            menu.add(0, 10, 10, menuIconWithText(getResources().getDrawable(R.drawable.icon_bug), getResources().getString(R.string.report)));
            menu.add(0, 11, 11, menuIconWithText(getResources().getDrawable(R.drawable.icon_exit), getResources().getString(R.string.exit)));

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case 1:
                Intent restart = getIntent();
                restart.putExtra("currentno", number);
                finish();
                startActivity(restart);
                return true;
            case 3:
                Intent table = new Intent(MainActivity.this, TableResult.class);
                MainActivity.this.startActivity(table);
                return true;

            case 4:
                Intent pdr = new Intent(MainActivity.this, Pdr.class);
                MainActivity.this.startActivity(pdr);
                return true;
            case 5:
                Intent pdr_r = new Intent(MainActivity.this, Pdrru.class);
                MainActivity.this.startActivity(pdr_r);
                return true;

            case 6:
                if (l.equals("r_")) {
                    SharedPreferences.Editor lng = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    lng.putString("language", "");
                    lng.apply();
                    this.setTitle("Білет №" + number);
                    invalidateOptionsMenu();
                    l = "";
                } else {
                    SharedPreferences.Editor lng = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    lng.putString("language", "r_");
                    lng.apply();
                    this.setTitle("Билет №" + number);
                    invalidateOptionsMenu();
                    l = "r_";

                }
                actionC();
                return true;

            case 10:
                Intent report = new Intent(MainActivity.this, Report.class);
                MainActivity.this.startActivity(report);
                return true;
            case 9:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "http://play.google.com/store/apps/details?id=ua.in.sil.cryzalit.mybilets"));
                startActivity(intent);
            case 7:
                Intent index = new Intent(MainActivity.this, CodeRegions.class);
                MainActivity.this.startActivity(index);
                return true;
            case 8:
                SharedPreferences.Editor nght = getSharedPreferences("pref", MODE_PRIVATE).edit();
                if (ngt) {
                    nght.putBoolean("night1", false);
                    nght.apply();
                    ngt =false;
                    actionC();
                }
                    else {
                    nght.putBoolean("night1", true);
                    nght.apply();
                    ngt = true;
                    actionC();
                    actionCd();
                    }

                return true;

            case 11:
                goExit();
                return true;


        }
            return super.onOptionsItemSelected(item);


        }





    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    @Override
    protected void onPause() {
        ByteBuffer.allocateDirect(1);
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

        private void goExit() {
            // Show the next level and reload the ad to prepare for the level after.
            Intent intent = new Intent(MainActivity.this, Launch.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }





    }
































package ua.in.sil.cryzalit.mybilets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Pdr extends AppCompatActivity {
    int[] pdr = new int[35];
    private Button[] btn = new Button[35];
    SharedPreferences preferences;
    String l;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdr);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String l = preferences.getString("language", "");
        if (l.equals("r_")) {
            this.setTitle(R.string.r_pdr);
        }
        else {
            this.setTitle("Правила дорожнього руху");

        }

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.g_bar));

        for (int i = 1; i < 35; i++) {
                pdr[i] = this.getResources().getIdentifier("pdr_" + i, "string", this.getPackageName());
                int bID = this.getResources().getIdentifier("b" + i, "id", this.getPackageName());
                btn[i] = findViewById(bID);
                btn[i].setTextSize(14);
                btn[i].setTextColor(getResources().getColor(R.color.Black));
                btn[i].setText(pdr[i]);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) btn[i].getLayoutParams();
                Typeface font = ResourcesCompat.getFont(this, R.font.tahoma);
                btn[i].setTypeface(font);
                btn[i].setLayoutParams(params);
                btn[i].setGravity(Gravity.TOP | Gravity.CENTER);
                btn[i].setTypeface(null, Typeface.BOLD);
                btn[i].setPadding(10, 30, 10, 30);


        }


    }



    public void click(View v) {
        Intent myIntent = new Intent(Pdr.this, Pdrshow.class);
        Button b = (Button) v;
        int id = b.getId();

        switch (id) {
            case R.id.b1:
                Log.d("number_pdr", 1 + "");
                myIntent.putExtra("pdr_n", 1 + "");
                Pdr.this.startActivity(myIntent);
                return;

            case R.id.b2:
                Log.d("number_pdr", 2 + "");
                myIntent.putExtra("pdr_n", 2 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b3:
                Log.d("number_pdr", 3 + "");
                myIntent.putExtra("pdr_n", 3 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b4:
                Log.d("number_pdr", 4 + "");
                myIntent.putExtra("pdr_n", 4 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b5:
                Log.d("number_pdr", 5 + "");
                myIntent.putExtra("pdr_n", 5 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b6:
                Log.d("number_pdr", 6 + "");
                myIntent.putExtra("pdr_n", 6 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b7:
                Log.d("number_pdr", 7 + "");
                myIntent.putExtra("pdr_n", 7 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b8:
                Log.d("number_pdr", 8 + "");
                myIntent.putExtra("pdr_n", 8 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b9:
                Log.d("number_pdr", 9 + "");
                myIntent.putExtra("pdr_n", 9 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b10:
                Log.d("number_pdr", 10 + "");
                myIntent.putExtra("pdr_n", 10 + "");
                Pdr.this.startActivity(myIntent);
                return;

            case R.id.b11:
                Log.d("number_pdr", 11 + "");
                myIntent.putExtra("pdr_n", 11 + "");
                Pdr.this.startActivity(myIntent);
                return;

            case R.id.b12:
                Log.d("number_pdr", 12 + "");
                myIntent.putExtra("pdr_n", 12 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b13:
                Log.d("number_pdr", 13 + "");
                myIntent.putExtra("pdr_n", 13 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b14:
                Log.d("number_pdr", 14 + "");
                myIntent.putExtra("pdr_n", 14 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b15:
                Log.d("number_pdr", 15 + "");
                myIntent.putExtra("pdr_n", 15 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b16:
                Log.d("number_pdr", 16 + "");
                myIntent.putExtra("pdr_n", 16 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b17:
                Log.d("number_pdr", 17 + "");
                myIntent.putExtra("pdr_n", 17 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b18:
                Log.d("number_pdr", 18 + "");
                myIntent.putExtra("pdr_n", 18 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b19:
                Log.d("number_pdr", 19 + "");
                myIntent.putExtra("pdr_n", 19 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b20:
                Log.d("number_pdr", 20 + "");
                myIntent.putExtra("pdr_n", 20 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b21:
                Log.d("number_pdr", 21 + "");
                myIntent.putExtra("pdr_n", 21 + "");
                Pdr.this.startActivity(myIntent);
                return;

            case R.id.b22:
                Log.d("number_pdr", 22 + "");
                myIntent.putExtra("pdr_n", 22 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b23:
                Log.d("number_pdr", 23 + "");
                myIntent.putExtra("pdr_n", 23 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b24:
                Log.d("number_pdr", 24 + "");
                myIntent.putExtra("pdr_n", 24 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b25:
                Log.d("number_pdr", 25 + "");
                myIntent.putExtra("pdr_n", 25 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b26:
                Log.d("number_pdr", 26 + "");
                myIntent.putExtra("pdr_n", 26 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b27:
                Log.d("number_pdr", 27 + "");
                myIntent.putExtra("pdr_n", 27 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b28:
                Log.d("number_pdr", 28 + "");
                myIntent.putExtra("pdr_n", 28 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b29:
                Log.d("number_pdr", 29 + "");
                myIntent.putExtra("pdr_n", 29 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b30:
                Log.d("number_pdr", 30 + "");
                myIntent.putExtra("pdr_n", 30 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b31:
                Log.d("number_pdr", 31 + "");
                myIntent.putExtra("pdr_n", 31 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b32:
                Log.d("number_pdr", 32 + "");
                myIntent.putExtra("pdr_n", 32 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b33:
                Log.d("number_pdr", 33 + "");
                myIntent.putExtra("pdr_n", 33 + "");
                Pdr.this.startActivity(myIntent);
                return;
            case R.id.b34:
                Log.d("number_pdr", 34 + "");
                myIntent.putExtra("pdr_n", 34 + "");
                Pdr.this.startActivity(myIntent);
                return;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_patient_home_screen, menu);


        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.icon_back),"Назад"));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.icon_table), getResources().getString(R.string.table_result)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.icon_bug), getResources().getString(R.string.report)));
        menu.add(0, 4, 4, menuIconWithText(getResources().getDrawable(R.drawable.icon_exit), getResources().getString(R.string.exit)));

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

                super.onBackPressed();

                return true;

            case 2:

                Intent start = new Intent(Pdr.this, TableResult.class);
                Pdr.this.startActivity(start);
                return true;

            case 3:

                Intent report = new Intent(Pdr.this, Report.class);
                Pdr.this.startActivity(report);


            case 4:

                goExit();
                return true;


            default:

                return super.onOptionsItemSelected(item);
        }

    }
    private void goExit() {
        // Show the next level and reload the ad to prepare for the level after.
        Intent intent = new Intent(Pdr.this, Launch.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }


    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}



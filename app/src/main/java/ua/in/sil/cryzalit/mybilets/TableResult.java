package ua.in.sil.cryzalit.mybilets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class TableResult extends AppCompatActivity {
    Button tbtn[] = new Button[101];
    String result [] = new String[101];
    int number [] = new int [101];
    int i;
    SharedPreferences edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_result);
        this.setTitle(R.string.table_result);
        edt = PreferenceManager.getDefaultSharedPreferences(this);
        String l = edt.getString("language", "");

        if (l.equals("r_")) {
            this.setTitle(R.string.r_table_result);
        }
        else {
            this.setTitle(R.string.table_result);

        }
        result [25] = edt.getString(25+"", "");

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.g_bar));
        for (i = 1; i < 71; i++) {

                int tID = this.getResources().getIdentifier("t" + i, "id", this.getPackageName());
                tbtn[i] = findViewById(tID);
                tbtn[i].setTextSize(30);
                tbtn[i].setTypeface(null, Typeface.BOLD);
                result [i] = edt.getString(i+"", "");


            if( result [i]!= null && ! result [i].equalsIgnoreCase(""))
            {
                Log.d("bilety",result[i]);
                number [i] = Integer.parseInt(result[i]);

                if ( number [i] <=13 ) {
                    tbtn[i].setBackgroundResource(R.drawable.red_gradient);

                }
                if (number [i] >= 14) {
                    tbtn[i].setBackgroundResource(R.drawable.yellow_gradient);

                }
                if (number [i]  > 17) {
                    tbtn[i].setBackgroundResource(R.drawable.green_gradient);

                }
            }



            tbtn[i].setOnClickListener(new View.OnClickListener() {
                String n;
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    n = b.getText().toString();
                    if (n.equals("29")||n.equals("67")||n.equals("13")||n.equals("")||n.equals("0")){

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Вибачте, такого білета немає в базі" , Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.getBackground().setColorFilter(Color.rgb(227,217,4), PorterDuff.Mode.SRC_IN);
                        toast.show();
                    }
                    else {
                        Intent myIntent = new Intent(TableResult.this, MainActivity.class);
                        myIntent.putExtra("currentno", n);
                        TableResult.this.startActivity(myIntent);
                        finish();

                    }



                }
            });







        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_patient_home_screen, menu);


        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.icon_back),"Назад"));
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
                finish();
                super.onBackPressed();
                return true;



            case 4:
                goExit();
                return true;



            default:

                return super.onOptionsItemSelected(item);
        }

    }
    private void goExit() {
        // Show the next level and reload the ad to prepare for the level after.
        Intent intent = new Intent(TableResult.this, Launch.class);
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

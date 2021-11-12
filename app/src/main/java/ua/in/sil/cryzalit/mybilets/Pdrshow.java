package ua.in.sil.cryzalit.mybilets;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;



public class Pdrshow extends AppCompatActivity {
    String pdr_n;
    WebView  showview;
    RelativeLayout scrl_show;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdrshow);
        final Intent intent = getIntent();
        pdr_n = intent.getStringExtra("pdr_n");
        int pdr = this.getResources().getIdentifier("pdr_" + pdr_n, "string", this.getPackageName());
        this.setTitle(pdr);
        showview = (WebView)findViewById(R.id.show);
        scrl_show = (RelativeLayout) findViewById(R.id.scrl_show);
        showview.getSettings().setBuiltInZoomControls(true);
        showview.getSettings().setLightTouchEnabled(true);
        showview.getSettings().setSupportZoom(true);
        showview.getSettings().setDisplayZoomControls(true);
        showview.requestFocus();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.g_bar));
        showview.loadUrl("file:///android_asset/pdr_"+pdr_n+".html");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_patient_home_screen, menu);


        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.icon_back),"Назад"));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.icon_next), "Наступний розділ"));
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

            case 2:
                Intent next = new Intent(Pdrshow.this, Pdrshow.class);
                int pdr_next = Integer.parseInt(pdr_n);
                if (pdr_n.equals("34")){
                    pdr_n=1+"";
                    next.putExtra("pdr_n",pdr_n);

                }
                else{
                    next.putExtra("pdr_n",pdr_next+1+"" );

                }
                Pdrshow.this.startActivity(next);
                finish();
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
        Intent intent = new Intent(Pdrshow.this, Launch.class);
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

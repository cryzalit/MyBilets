package ua.in.sil.cryzalit.mybilets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Report extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText email;
    EditText text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Button send = findViewById(R.id.send);
        email = findViewById(R.id.report_email);
        text = findViewById(R.id.report_text);
        TextView tv1 =  findViewById(R.id.tv1);
        TextView tv2 =  findViewById(R.id.tv2);

        FirebaseFirestore.setLoggingEnabled(true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String l = preferences.getString("language", "");
        if (l.equals("r_")) {
            tv1.setText("Введите свой адрес электронной почты");
            tv2.setText("Какие проблемы (вопросы) у вас возникли?");
            email.setHint("Например: oleg.filimonov@gmail.com");
            text.setHint("Например: Билет 7, вопрос 8 - указан неправильный ответ");
            send.setText("ОТПРАВИТЬ");

        }
        else {

        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> report = new HashMap<>();
                report.put("email", email.getText().toString());
                report.put("massage", text.getText().toString());


// Add a new document with a generated ID
                db.collection("report")
                        .add(report)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("помилка", "Error adding document", e);
                            }
                        });
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ми вдячні за Ваш відгук" , Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();


            }
        });
    }
}

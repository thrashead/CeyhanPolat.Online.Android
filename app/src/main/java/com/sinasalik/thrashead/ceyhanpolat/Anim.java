package com.sinasalik.thrashead.ceyhanpolat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;

public class Anim extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim);
        SayfaGec();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SayfaGec();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SayfaGec();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SayfaGec();
    }

    @Override
    public void onBackPressed() {

    }

    public void Kapat() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    public void SayfaGec() {
        if (TDAraclar.InternetVarmi(context) == false) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("İnternet Kontrol")
                    .setMessage("İnternet olmadığı için program başlatılamayacaktır.")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Kapat();
                        }
                    });
            alertDialog.show();
        } else {
            Thread acilis = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                        startActivity(new Intent(context, AnaSayfa.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            acilis.start();
        }
    }
}

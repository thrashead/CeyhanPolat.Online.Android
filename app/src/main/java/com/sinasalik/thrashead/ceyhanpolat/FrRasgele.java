package com.sinasalik.thrashead.ceyhanpolat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.app.ProgressDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;
import com.sinasalik.thrashead.tdlibrary.TDResimDoldur;
import com.sinasalik.thrashead.tdlibrary.TDAraclar;

import org.json.JSONException;
import org.json.JSONObject;

public class FrRasgele extends Fragment {
    private Context context;
    private View view;

    private ProgressDialog pd;

    private TextView lblBaslik, lblSiir, lblTarih, lblSehir;
    private ImageView imgSiir;
    private LinearLayout lytRasgele;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rasgele, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getContext();

        lblBaslik = (TextView) view.findViewById(R.id.lblBaslik);
        lblSiir = (TextView) view.findViewById(R.id.lblSiir);
        lblTarih = (TextView) view.findViewById(R.id.lblTarih);
        lblSehir = (TextView) view.findViewById(R.id.lblSehir);
        imgSiir = (ImageView) view.findViewById(R.id.imgSiir);
        lytRasgele = (LinearLayout) view.findViewById(R.id.lytRasgele);
    }

    private void Olaylar() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Rasgele Şiir")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Ekrana basılı tutarsanız yeni bir şiir gelecektir.")
                .setPositiveButton("Tamam", null).show();

        lytRasgele.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SayfaDoldur();
                return true;
            }
        });

        SayfaDoldur();
    }

    void SayfaDoldur() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Şiir Yükleniyor...");
        pd.show();

        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    try {
                        lblBaslik.setText(jsonObject.getString("PoetryName"));
                        lblSiir.setText(TDAraclar.WebAdresDuzelt(jsonObject.getString("PoetryContent")));
                        lblTarih.setText(jsonObject.getString("Date"));
                        lblSehir.setText(jsonObject.getString("City"));
                        new TDResimDoldur(imgSiir).execute(TDAraclar.WebAdresDuzelt(jsonObject.getString("Picture")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pd.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "ShufflePoetry");
    }
}

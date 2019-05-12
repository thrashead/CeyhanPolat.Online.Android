package com.sinasalik.thrashead.ceyhanpolat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;
import com.sinasalik.thrashead.tdlibrary.TDResimDoldur;
import com.sinasalik.thrashead.tdlibrary.TDAraclar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FrSiir extends Fragment {
    private Context context;
    private View view;

    private ProgressDialog pd;

    private TextView lblBaslik, lblSiir, lblTarih, lblSehir;
    private ImageView imgSiir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.siir, container, false);

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
    }

    private void Olaylar() {
        SayfaDoldur();
    }

    void SayfaDoldur() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Şiir Yükleniyor...");
        pd.show();
        Bundle bundle = getArguments();
        String poetryurl;

        if (bundle != null) {
            if (bundle.getString("poetryurl") != null) {
                poetryurl = bundle.getString("poetryurl");

                new TDJson(new JSONObject(), new TDJsonListener() {
                    @Override
                    public void successCallBack(String jsonResult) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(jsonResult);

                            lblBaslik.setText(jsonObject.getString("PoetryName"));
                            lblSiir.setText(TDAraclar.WebAdresDuzelt(jsonObject.getString("PoetryContent")));
                            lblTarih.setText(jsonObject.getString("Date"));
                            lblSehir.setText(jsonObject.getString("City"));
                            new TDResimDoldur(imgSiir).execute(TDAraclar.WebAdresDuzelt(jsonObject.getString("Picture")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void errorCallBack() {

                    }
                }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "PoetryByUrl?poetryurl=" + poetryurl);
            }
        }
    }
}

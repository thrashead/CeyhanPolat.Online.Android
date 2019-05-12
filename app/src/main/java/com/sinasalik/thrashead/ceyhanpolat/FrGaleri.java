package com.sinasalik.thrashead.ceyhanpolat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.tdlibrary.TDGaleri;
import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FrGaleri extends Fragment {
    private Context context;
    private View view;

    private ProgressDialog pd;

    private List<String> pics;
    private TDGaleri _imgAdap;
    private Gallery glGaleri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.galeri, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getContext();

        pics = new ArrayList<>();
        _imgAdap = new TDGaleri(context);
        glGaleri = (Gallery) view.findViewById(R.id.glGaleri);
    }

    private void Olaylar() {
        SayfaDoldur();
    }

    void SayfaDoldur() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Galeri Yükleniyor...");
        pd.show();

        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONArray jsonObject = new JSONArray(jsonResult);

                    for (int i = 0; i < jsonObject.length(); i++) {
                        try {
                            pics.add(TDAraclar.WebAdresDuzelt(jsonObject.getJSONObject(i).getString("Picture")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    _imgAdap.Resimler = pics.toArray(new String[pics.size()]);

                    glGaleri.setAdapter(_imgAdap);

                    /* FrGaleri otomatik load olmuyor. Aşağıdaki kod onu tetikliyor açılışta */
                    glGaleri.setSelection(1);
                    glGaleri.onFling(null, null, (float) (1) * 1000, 0);
                    /* FrGaleri otomatik load olmuyor. Yukarıdaki kod onu tetikliyor açılışta */
                } catch (Exception e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "Gallery");
    }
}

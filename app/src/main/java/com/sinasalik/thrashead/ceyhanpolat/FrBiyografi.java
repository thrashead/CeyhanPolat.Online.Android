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

import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;
import com.sinasalik.thrashead.tdlibrary.TDResimDoldur;

import org.json.JSONException;
import org.json.JSONObject;

public class FrBiyografi extends Fragment {
    private Context context;
    private View view;

    private ProgressDialog pd;

    private ImageView imgBiyografi;
    private TextView lblBaslik, lblBiyografi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.biyografi, container, false);

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
        lblBiyografi = (TextView) view.findViewById(R.id.lblBiyografi);
        imgBiyografi = (ImageView) view.findViewById(R.id.imgBiyografi);
    }

    private void Olaylar() {
        SayfaDoldur();
    }

    void SayfaDoldur() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Biyografi Yükleniyor...");
        pd.show();

        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    try {
                        lblBaslik.setText(jsonObject.getString("BioTitle"));
                        lblBiyografi.setText(TDAraclar.WebAdresDuzelt(jsonObject.getString("BioContent")));
                        new TDResimDoldur((ImageView) view.findViewById(R.id.imgBiyografi)).execute(TDAraclar.KaynakDegerDon(context, R.string.web_root) + "Uploads/Gallery/017562d5.jpg");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pd.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "Biography");
    }
}

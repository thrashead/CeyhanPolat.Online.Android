package com.sinasalik.thrashead.ceyhanpolat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FrSiirler extends Fragment {
    private Context context;
    private View view;

    private ProgressDialog pd;

    private String dataSiirismi = "";
    private String dataIlktarih = "";
    private String dataSontarih = "";

    private EditText etPoetrysearchSiirismi;
    private EditText etPoetrysearchIlktarih;
    private EditText etPoetrysearchSontarih;
    private Button btPoetrysearchAra;
    private ArrayList<String> poets;
    private ListView lvPoetrysearchListe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.siirler, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getContext();

        poets = new ArrayList<>();

        etPoetrysearchSiirismi = (EditText) view.findViewById(R.id.etPoetrysearchSiirismi);
        etPoetrysearchIlktarih = (EditText) view.findViewById(R.id.etPoetrysearchIlktarih);
        etPoetrysearchSontarih = (EditText) view.findViewById(R.id.etPoetrysearchSontarih);
        btPoetrysearchAra = (Button) view.findViewById(R.id.btPoetrysearchAra);
        lvPoetrysearchListe = (ListView) view.findViewById(R.id.lvPoetrysearchListe);
    }

    private void Olaylar() {
        SayfaDoldur();
    }

    void SayfaDoldur() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Şiir Listesi Yükleniyor...");
        pd.show();

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            etPoetrysearchIlktarih.setText("1900");
            etPoetrysearchSontarih.setText("2014");
        }

        SiirListeGetir();

        btPoetrysearchAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                SiirListeGetir();

                pd.dismiss();
            }
        });

        pd.dismiss();
    }

    public void SiirListeGetir() {
        dataSiirismi = etPoetrysearchSiirismi.getText().toString();
        dataIlktarih = etPoetrysearchIlktarih.getText().toString();
        dataSontarih = etPoetrysearchSontarih.getText().toString();

        poets.clear();
        SiirListe();

        lvPoetrysearchListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object satir = parent.getItemAtPosition(position);
                String poetryurl = TDAraclar.WebAdresDon(satir.toString());

                FrSiir frSiir = new FrSiir();

                Bundle bundle = new Bundle();
                bundle.putString("poetryurl", poetryurl);

                frSiir.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flytCP, frSiir, "FrSiir").addToBackStack("FrSiir").commit();
            }
        });
    }

    public void SiirListe() {
        String servisAdres;

        if (dataSiirismi.isEmpty() && dataIlktarih.isEmpty() && dataSontarih.isEmpty()) {
            servisAdres = TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "Poetries";
        } else {
            servisAdres = TDAraclar.KaynakDegerDon(context, R.string.wcf_root) + "SearchPoetry?poetryname=" + dataSiirismi + "&firstdate=" + dataIlktarih + "&lastdate=" + dataSontarih;
        }

        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonResult);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        poets.add(jsonArray.getJSONObject(i).getString("PoetryName"));
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("ID"));
                    }

                    lvPoetrysearchListe.setAdapter(new ArrayAdapter<String>(context, R.layout.stil_list_item, poets));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(servisAdres);
    }
}

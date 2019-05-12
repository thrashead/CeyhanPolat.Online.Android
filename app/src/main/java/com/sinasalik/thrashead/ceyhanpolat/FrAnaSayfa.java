package com.sinasalik.thrashead.ceyhanpolat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FrAnaSayfa extends Fragment {
    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.anasayfa, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
    }

    private void Nesneler() {
        context = getActivity().getApplicationContext();
    }
}

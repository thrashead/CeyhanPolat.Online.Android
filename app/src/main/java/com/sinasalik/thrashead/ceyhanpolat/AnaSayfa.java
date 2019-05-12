package com.sinasalik.thrashead.ceyhanpolat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;

public class AnaSayfa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;

    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        FrAnaSayfa frAnaSayfa = new FrAnaSayfa();
        getSupportFragmentManager().beginTransaction().add(R.id.flytCP, frAnaSayfa, "FrAnaSayfa").commit();
        navigationView.setCheckedItem(R.id.nav_anasayfa);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        Fragment current = getCurrentFragment();
                        if (current instanceof FrAnaSayfa) {
                            navigationView.setCheckedItem(R.id.nav_anasayfa);
                        } else if (current instanceof FrBiyografi) {
                            navigationView.setCheckedItem(R.id.nav_biyografi);
                        } else if (current instanceof FrGaleri) {
                            navigationView.setCheckedItem(R.id.nav_galeri);
                        } else if (current instanceof FrSiirler) {
                            navigationView.setCheckedItem(R.id.nav_siirler);
                        } else if (current instanceof FrRasgele) {
                            navigationView.setCheckedItem(R.id.nav_rasgele);
                        } else if (current instanceof FrSiir) {
                            navigationView.setCheckedItem(R.id.nav_siirler);
                        }
                    }
                });
    }

    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.flytCP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_kapat) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Çıkış Kontrol")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Programdan çıkmak istediğinize emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Kapat();
                        }
                    })
                    .setNegativeButton("Hayır", null).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_kapat) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Çıkış Kontrol")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Programdan çıkmak istediğinize emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Kapat();
                        }
                    })
                    .setNegativeButton("Hayır", null).show();
        } else {
            switch (id) {
                case R.id.nav_anasayfa:
                    FrAnaSayfa frAnaSayfaKon = (FrAnaSayfa) getSupportFragmentManager().findFragmentByTag("FrAnaSayfa");
                    if (frAnaSayfaKon == null || !frAnaSayfaKon.isVisible()) {
                        FrAnaSayfa frAnaSayfa = new FrAnaSayfa();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flytCP, frAnaSayfa, "FrAnaSayfa").addToBackStack("FrAnaSayfa").commit();
                    }
                    break;
                case R.id.nav_biyografi:
                    FrBiyografi frBiyografiKon = (FrBiyografi) getSupportFragmentManager().findFragmentByTag("FrBiyografi");
                    if (frBiyografiKon == null || !frBiyografiKon.isVisible()) {
                        FrBiyografi frBiyografi = new FrBiyografi();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flytCP, frBiyografi, "FrBiyografi").addToBackStack("FrBiyografi").commit();
                    }
                    break;
                case R.id.nav_galeri:
                    FrGaleri frGaleriKon = (FrGaleri) getSupportFragmentManager().findFragmentByTag("FrGaleri");
                    if (frGaleriKon == null || !frGaleriKon.isVisible()) {
                        FrGaleri frGaleri = new FrGaleri();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flytCP, frGaleri, "FrGaleri").addToBackStack("FrGaleri").commit();
                    }
                    break;
                case R.id.nav_siirler:
                    FrSiirler frSiirlerKon = (FrSiirler) getSupportFragmentManager().findFragmentByTag("FrSiirler");
                    if (frSiirlerKon == null || !frSiirlerKon.isVisible()) {
                        FrSiirler frSiirler = new FrSiirler();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flytCP, frSiirler, "FrSiirler").addToBackStack("FrSiirler").commit();
                    }
                    break;
                case R.id.nav_rasgele:
                    FrRasgele frRasgeleKon = (FrRasgele) getSupportFragmentManager().findFragmentByTag("FrRasgele");
                    if (frRasgeleKon == null || !frRasgeleKon.isVisible()) {
                        FrRasgele frRasgele = new FrRasgele();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flytCP, frRasgele, "FrRasgele").addToBackStack("FrRasgele").commit();
                    }
                    break;
                case R.id.nav_web:
                    Intent intentweb = new Intent(Intent.ACTION_VIEW);
                    intentweb.setData(Uri.parse(TDAraclar.KaynakDegerDon(context, R.string.web_root)));
                    context.startActivity(intentweb);
                    break;
                case R.id.nav_developer:
                    Intent intentdeveloper = new Intent(Intent.ACTION_VIEW);
                    intentdeveloper.setData(Uri.parse(TDAraclar.KaynakDegerDon(context, R.string.web_developer)));
                    context.startActivity(intentdeveloper);
                    break;
            }
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment current = getCurrentFragment();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 || current instanceof FrAnaSayfa) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Çıkış Kontrol")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Programdan çıkmak istediğinize emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Kapat();
                        }
                    })
                    .setNegativeButton("Hayır", null).show();
        }
        else {
            super.onBackPressed();
        }
    }

    void Kapat() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

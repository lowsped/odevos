package com.lengkap.pos.kode.indonesia;

        import android.app.AlertDialog;
        import android.content.ActivityNotFoundException;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.DialogInterface.OnClickListener;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.CardView;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.EditText;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.Toast;

        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdRequest.Builder;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.InterstitialAd;

        import java.io.FileOutputStream;
        import java.io.InputStream;
        import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    ListAdapter adapter;
    ListAdapter adapter2;
    ListAdapter adapter3;
    ListAdapter adapter4;
    ListAdapter adapter5;
    Cursor cursor;
    Cursor cursor2;
    Cursor cursor3;
    Cursor cursor4;
    Cursor cursor5;
    SimpleCursorAdapter cursorAdapter;
    SQLiteDatabase db;
    EditText keyword;
    ListView lv;
    DatabaseHelper mDBHelper;
    CardView panduan;
    InterstitialAd mInterstitialAd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);



        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1142409875326374/7039706837");


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdOpened() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdLeftApplication() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });




        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        this.mDBHelper = new DatabaseHelper(this);
        this.lv = (ListView) findViewById(R.id.listView);
        this.keyword = (EditText) findViewById(R.id.keyword_id);
        this.panduan = (CardView) findViewById(R.id.panduan);
        if (!getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME).exists()) {
            this.mDBHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", 0).show();
            } else {
                Toast.makeText(this, "Copy data error", 0).show();
                return;
            }
        }
        this.db = new DatabaseHelper(this).getWritableDatabase();
        this.keyword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String edit_db = MainActivity.this.keyword.getText().toString();
                if (edit_db.equals("")) {
                    MainActivity.this.lv.setVisibility(8);
                    MainActivity.this.panduan.setVisibility(0);
                } else {
                    MainActivity.this.lv.setVisibility(0);
                    MainActivity.this.panduan.setVisibility(8);
                    try {
                        MainActivity.this.cursor = MainActivity.this.db.rawQuery("SELECT * FROM kodepos WHERE desa LIKE ?", new String[]{"%" + edit_db + "%"});
                        MainActivity.this.cursor2 = MainActivity.this.db.rawQuery("SELECT * FROM kodepos WHERE kodepos LIKE ?", new String[]{"%" + edit_db + "%"});
                        MainActivity.this.cursor3 = MainActivity.this.db.rawQuery("SELECT * FROM kodepos WHERE kecamatan LIKE ?", new String[]{"%" + edit_db + "%"});
                        MainActivity.this.cursor4 = MainActivity.this.db.rawQuery("SELECT * FROM kodepos WHERE kabupaten LIKE ?", new String[]{"%" + edit_db + "%"});
                        MainActivity.this.cursor5 = MainActivity.this.db.rawQuery("SELECT * FROM kodepos WHERE provinsi LIKE ?", new String[]{"%" + edit_db + "%"});
                        MainActivity.this.adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, MainActivity.this.cursor, new String[]{DatabaseHelper.KEY_KODEPOS, DatabaseHelper.KEY_DESA, "kecamatan", DatabaseHelper.KEY_KABUPATEN, DatabaseHelper.KEY_PROVINSI}, new int[]{R.id.txt_kodepos, R.id.txt_desa, R.id.txt_kecamatan, R.id.txt_kabupaten, R.id.txt_provinsi});
                        MainActivity.this.adapter2 = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, MainActivity.this.cursor2, new String[]{DatabaseHelper.KEY_KODEPOS, DatabaseHelper.KEY_DESA, "kecamatan", DatabaseHelper.KEY_KABUPATEN, DatabaseHelper.KEY_PROVINSI}, new int[]{R.id.txt_kodepos, R.id.txt_desa, R.id.txt_kecamatan, R.id.txt_kabupaten, R.id.txt_provinsi});
                        MainActivity.this.adapter3 = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, MainActivity.this.cursor3, new String[]{DatabaseHelper.KEY_KODEPOS, DatabaseHelper.KEY_DESA, "kecamatan", DatabaseHelper.KEY_KABUPATEN, DatabaseHelper.KEY_PROVINSI}, new int[]{R.id.txt_kodepos, R.id.txt_desa, R.id.txt_kecamatan, R.id.txt_kabupaten, R.id.txt_provinsi});
                        MainActivity.this.adapter4 = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, MainActivity.this.cursor4, new String[]{DatabaseHelper.KEY_KODEPOS, DatabaseHelper.KEY_DESA, "kecamatan", DatabaseHelper.KEY_KABUPATEN, DatabaseHelper.KEY_PROVINSI}, new int[]{R.id.txt_kodepos, R.id.txt_desa, R.id.txt_kecamatan, R.id.txt_kabupaten, R.id.txt_provinsi});
                        MainActivity.this.adapter5 = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, MainActivity.this.cursor5, new String[]{DatabaseHelper.KEY_KODEPOS, DatabaseHelper.KEY_DESA, "kecamatan", DatabaseHelper.KEY_KABUPATEN, DatabaseHelper.KEY_PROVINSI}, new int[]{R.id.txt_kodepos, R.id.txt_desa, R.id.txt_kecamatan, R.id.txt_kabupaten, R.id.txt_provinsi});
                        if (MainActivity.this.adapter.getCount() == 0 && MainActivity.this.adapter2.getCount() == 0 && MainActivity.this.adapter3.getCount() == 0 && MainActivity.this.adapter4.getCount() == 0 && MainActivity.this.adapter5.getCount() == 0) {
                            Toast.makeText(MainActivity.this, "Tidak ditemukan data dengan kata kunci " + edit_db + "", 0).show();
                            MainActivity.this.lv.setVisibility(4);
                        } else if (MainActivity.this.adapter.getCount() != 0) {
                            MainActivity.this.lv.setAdapter(MainActivity.this.adapter);
                        } else if (MainActivity.this.adapter2.getCount() != 0) {
                            MainActivity.this.lv.setAdapter(MainActivity.this.adapter2);
                        } else if (MainActivity.this.adapter3.getCount() != 0) {
                            MainActivity.this.lv.setAdapter(MainActivity.this.adapter3);
                        } else if (MainActivity.this.adapter4.getCount() != 0) {
                            MainActivity.this.lv.setAdapter(MainActivity.this.adapter4);
                        } else if (MainActivity.this.adapter5.getCount() != 0) {
                            MainActivity.this.lv.setAdapter(MainActivity.this.adapter5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.this.lv.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Cursor cursor = (Cursor) MainActivity.this.lv.getItemAtPosition(position);
                        Log.d("Desa", cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESA)));
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            OutputStream outputStream = new FileOutputStream(DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME);
            byte[] buff = new byte[1024];
            while (true) {
                int length = inputStream.read(buff);
                if (length > 0) {
                    outputStream.write(buff, 0, length);
                } else {
                    outputStream.flush();
                    outputStream.close();
                    Log.w("MainActivity", "DB copied");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Kode Pos Indonesia lengkap");
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.lengkap.pos.kode.indonesia");
            startActivity(Intent.createChooser(share, "Share Aps!"));
            Toast.makeText(this, "share aps", Toast.LENGTH_LONG).show();
            return true;

        }

        if (id == R.id.more) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=pemikir%20versi%20baru"));
            startActivity(intent);
            Toast.makeText(this, "more aps", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;

    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Pastikan terhubung dengan INTERNET, Saat membuka aplikasi. Terima Kasih.. ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;

    }


}
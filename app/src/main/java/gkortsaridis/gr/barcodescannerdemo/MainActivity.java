package gkortsaridis.gr.barcodescannerdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView formatt,contentt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formatt = (TextView) findViewById(R.id.formatTxt);
        contentt = (TextView) findViewById(R.id.contentTxt);

        boolean installed  =   appInstalledOrNot("com.google.zxing.client.android");
        if(installed) Log.i("Installed","QR");
        else {
            //An den einai egatestimeno, emfanizo to katallilo Dialog Box
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ZXing QR Code Scanner");
            builder.setMessage("In order for the app to work properly, you need to install the Zxing QR Code Scanner");

            builder.setPositiveButton("Install", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.zxing.client.android"));
                    startActivity(i);
                }

            });

            AlertDialog alert = builder.create();
            alert.show();

        }


    }

    public void startscan(View v){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("xZing", "contents: "+contents+" format: "+format); // Handle successful scan
                formatt.setText("Format : " + format);
                contentt.setText("Content : "+contents);
            }
            else if(resultCode == RESULT_CANCELED){ // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }

    private boolean appInstalledOrNot(String uri)
    {
        //Elegxei an einai egatestimeno sti siskeui kapoio programma
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

}

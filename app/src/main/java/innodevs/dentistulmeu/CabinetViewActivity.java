package innodevs.dentistulmeu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CabinetViewActivity extends AppCompatActivity {
    ListView lv;
    Context context;
    TextView cabinetNameView;
    TextView cabinetRatingView;
    ArrayList prgmName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        cabinetNameView = (TextView) findViewById(R.id.cabinetName);
        cabinetRatingView = (TextView) findViewById(R.id.ratingText);
        cabinetNameView.setText(getIntent().getStringExtra("cabinetName"));
        lv = (ListView) findViewById(R.id.listView2);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            getCabinetInfo();

        }

    }
    private void getCabinetInfo(){
        Intent intent = getIntent();
        String cabinetName = intent.getStringExtra("cabinetName");
        String urlParameters = "cabinetName=" + cabinetName;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = "http://dentprice.ro/app/GetCabinetInfo";
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));

            conn.connect();
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            } catch (Exception e) {
            }
            int responseCode = conn.getResponseCode();
            String response = "";
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }




            String[] splitted = response.split(";");
            cabinetRatingView.setText("Rating: " + splitted[0]);
            ArrayList<String> cabinetNames = new ArrayList<>();
            ArrayList<String> ratings = new ArrayList<>();
            try{
                String[] splittedMedics = splitted[1].split(",");
                int i = 0;
                while(true){
                    cabinetNames.add(splittedMedics[i]);
                    ratings.add(splittedMedics[++i]);
                    i++;

                }



            }
            catch(Exception e){}




            int[] prgmImages = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share,R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share,R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share,R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share,R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share};
            lv = (ListView) findViewById(R.id.listView2);
            String[] cabinets = new String[cabinetNames.size()];
            cabinets = cabinetNames.toArray(cabinets);
            String[] ratingsArray = new String[ratings.size()];
            ratingsArray = ratings.toArray(ratingsArray);
            lv.setAdapter(new CustomAdapterDoctors(this, cabinets, ratingsArray, prgmImages));

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}

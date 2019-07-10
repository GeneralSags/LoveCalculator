package np.com.saugattimilsina.www.lovecalculator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Calculation extends AppCompatActivity
{
    //For Ad
    //private AdView mAdView;
    private double percent = 0, check = 0, total;
    private String name1, name2;
    private TextView result;
    DecimalFormat df = new DecimalFormat();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        ImageView myImage = (ImageView) findViewById(R.id.cupid);
        //Banner Ad
//        MobileAds.initialize(this, "ca-app-pub-8939125772345798~9865358296");//APP-ID
//        mAdView = (AdView)findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        Button calc = findViewById(R.id.completeButton);
        calc.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // do something when the button is clicked
                calculate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.share)
        {
            String shareBody = "Check out this awesome Love calculator app for free on PlayStore.\n" +
                    "https://play.google.com/store/apps/details?id=np.com.saugattimilsina.www.lovecalculator";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Love Calculator app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using..."));
        }

        else if(id == R.id.review)
        {
            Uri rate = Uri.parse("https://play.google.com/store/apps/details?id=np.com.saugattimilsina.www.lovecalculator");
            Intent rateUs = new Intent(Intent.ACTION_VIEW, rate);
            if(rateUs.resolveActivity(getPackageManager())!=null)
            {
                startActivity(rateUs);
            }
            else
            {
                Toast.makeText(this, "No browser found.", Toast.LENGTH_SHORT).show();
            }
        }

        else if(id == R.id.help)
        {
            Toast.makeText(this, "Disclaimer!\nThe result provided by this app shouldn't be taken seriously.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void calculate()
    {
        percent = 0; check = 0;
        EditText firstName = (EditText) findViewById(R.id.name);
        EditText secondName = (EditText) findViewById(R.id.anotherName);
        result = (TextView) findViewById(R.id.resultView);
        name1 = firstName.getText().toString();
        name1 = name1.trim();
        name1 = name1.toUpperCase();
        name2 = secondName.getText().toString();
        name2 = name2.trim();
        name2 = name2.toUpperCase();
        //Main Code Starts here
        if(name1.isEmpty() || name1.equals(""))
        {
            result.setText("0%");
            Toast.makeText(getApplicationContext(), "Fill your name.", Toast.LENGTH_SHORT).show();
        }
        else if(name2.isEmpty() || name2.equals(""))
        {
            result.setText("0%");
            Toast.makeText(getApplicationContext(), "Fill your crush's name.", Toast.LENGTH_SHORT).show();
        }
        if(!name1.isEmpty() && !name2.isEmpty())
        {
            for (int i = 0; i < name1.length(); i++) {
                for (int j = 0; j < name2.length(); j++) {
                    if ((name1.charAt(i) == name2.charAt(j))) {
                        check++;
                    }
                }
            }
            total = (name1.length() + name2.length());
            percent = ((check) / (total) * 100);
            exceptionalCase();
        }
    }
    private void exceptionalCase()
    {
        if(percent<30)
        {
            total = (name1.length()+name2.length());
            percent = (((check)/(total)*100)+36);
        }
        else if(percent<40)
        {
            total = (name1.length()+name2.length());
            percent = (((check)/(total)*100)+43);
        }
        else if(percent>100)
        {
            if(name1.length()<10)
            {
                percent = name2.length()*9;
            }
            else if(name2.length()<10)
            {
                percent = name2.length()*7;
                if (name1.length()>10)
                {
                    percent = name1.length()/10;
                    if(percent < 10)
                    {
                        percent = percent + 35;
                    }
                    else if(percent < 20)
                    {
                        percent = percent + 30;
                    }
                    else if(percent < 30)
                    {
                        percent = percent + 20;
                    }
                    else if(percent < 40)
                    {
                        percent = percent + 15;
                    }
                    else if(percent > 100)
                    {
                        percent = 85;
                    }
                }
            }
            else if(name2.length()>10)
            {
                percent = name2.length()/9;
                if (name1.length()>10)
                {
                    percent = name1.length()/10;
                    if(percent < 10)
                    {
                        percent = percent + 35;
                    }
                    else if(percent < 20)
                    {
                        percent = percent + 30;
                    }
                    else if(percent < 30)
                    {
                        percent = percent + 20;
                    }
                    else if(percent < 40)
                    {
                        percent = percent + 15;
                    }
                    else if(percent > 100)
                    {
                        percent = 85;
                    }
                }
            }
        }
        df.setMaximumFractionDigits(2);
        result.setText(df.format(percent)+"%");
    }
}
package cheetatech.com.colorhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutusActivity extends AppCompatActivity  implements View.OnClickListener{

    private String cheetatechUrl = "https://cheetatech.wordpress.com/";
    private String cheetatechEmail = "cheetatech@gmail.com";
    private String subjectEmail = "Need Help";
    private String[] email = new String[]{
        "https://www.linkedin.com/in/erkan-g%C3%BCzeler-95b47252",
        "https://www.linkedin.com/in/erkan-g%C3%BCzeler-95b47252",
        "https://www.linkedin.com/in/erkan-g%C3%BCzeler-95b47252",
        "https://www.linkedin.com/in/erkan-g%C3%BCzeler-95b47252"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("ColorHub");

        ((TextView)findViewById(R.id.link_web_text)).setOnClickListener(this);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.steam_1)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.steam_2)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.steam_3)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.gteam_1)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.link_web_text :
                openUrl(cheetatechUrl);
                break;
            case R.id.fab :
                sendEmail();
                break;

            case R.id.steam_1 :
                openUrl(email[0]);
                break;
            case R.id.steam_2 :
                openUrl(email[1]);
                break;
            case R.id.steam_3 :
                openUrl(email[2]);
                break;
            case R.id.gteam_1 :
                openUrl(email[3]);
                break;
        }
    }

    private void openCheetaTech() {
        Uri uri = Uri.parse(cheetatechUrl); // missing 'http://' will cause crashed
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void sendEmail()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { cheetatechEmail });
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);
        //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
    }
}

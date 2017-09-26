package cheetatech.com.colorhub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cheetatech.com.colorhub.social.Links;
import cheetatech.com.colorhub.social.Social;

public class AboutusActivity extends AppCompatActivity  implements View.OnClickListener{

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
        ((ImageView)findViewById(R.id.gteam_1)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.gteam_2)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.link_web_text :
                Social.Companion.openUrl(Links.ANTRIKOD_URL, AboutusActivity.this);
                break;
            case R.id.fab :
                Social.Companion.sendEmail(AboutusActivity.this);
                break;

            case R.id.steam_1 :
                Social.Companion.openUrl(Links.PERSON_LINKS[0], AboutusActivity.this);
                break;
            case R.id.steam_2 :
                Social.Companion.openUrl(Links.PERSON_LINKS[1], AboutusActivity.this);
                break;
            case R.id.gteam_1 :
                Social.Companion.openUrl(Links.PERSON_LINKS[3], AboutusActivity.this);
                break;
            case R.id.gteam_2 :
                Social.Companion.openUrl(Links.PERSON_LINKS[4], AboutusActivity.this);
                break;
        }
    }

}

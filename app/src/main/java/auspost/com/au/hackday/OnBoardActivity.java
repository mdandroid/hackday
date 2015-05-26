package auspost.com.au.hackday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OnBoardActivity extends Activity {

    private Button facebookBtn;
    private Button myPostBtn;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        registerFacebookBtn();
        registerMyPostBtn();
        registerSignUp();
    }

    private void registerSignUp() {
        signUpBtn = (Button) findViewById(R.id.no_id);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerMyPostBtn() {
        myPostBtn = (Button) findViewById(R.id.mypost);
        myPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerFacebookBtn() {
        facebookBtn = (Button) findViewById(R.id.facebook);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


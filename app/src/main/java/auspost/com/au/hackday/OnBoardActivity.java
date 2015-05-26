package auspost.com.au.hackday;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import auspost.com.au.hackday.model.User;
import auspost.com.au.hackday.persistence.AsyncTaskResult;
import auspost.com.au.hackday.persistence.DatabaseManager;
import auspost.com.au.hackday.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;


public class OnBoardActivity extends Activity {

    private Button facebookBtn;
    private Button myPostBtn;
    private Button signUpBtn;
    private LinearLayout loginSection;
    private LinearLayout onBoardSelection;
    private AutoCompleteTextView email;
    private EditText password;
    private ProgressBar progress;
    private DatabaseManager databaseManager;
    private String emailStr;
    private String passwordStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        databaseManager = new DatabaseManager(this);

        populateFakeUsers();

        loginSection = (LinearLayout) findViewById(R.id.login_section);
        onBoardSelection = (LinearLayout) findViewById(R.id.onboard_selection);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        progress = (ProgressBar) findViewById(R.id.progress);

        password = (EditText) findViewById(R.id.password);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        registerFacebookBtn();
        registerMyPostBtn();
        registerSignUp();
    }

    private void attemptLogin() {
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(emailStr) && !isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(emailStr)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            FacebookAuthTask task = new FacebookAuthTask();
            task.execute();
        }

    }

    private void showProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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
                onBoardSelection.setVisibility(View.GONE);
                loginSection.setVisibility(View.VISIBLE);
            }
        });
    }

    private void populateFakeUsers() {
        Map<String, String> user1 = new HashMap<>();
        user1.put(DatabaseManager.NAME, "Tony Stark");
        user1.put(DatabaseManager.PWD, "pepperpotts");
        user1.put(DatabaseManager.DOB, "12751200000");
        user1.put(DatabaseManager.DL, "01293982");
        user1.put(DatabaseManager.PN, "GA19283909");
        user1.put(DatabaseManager.PHONE, "04129384938");
        user1.put(DatabaseManager.EMAIL, "tony.stark@avengers.com");
        user1.put(DatabaseManager.ADS, "111 Bourke Street,VIC Melbourne:80 Collin Street,VIC Melbourne");
        databaseManager.save("tony.stark@avengers.com", user1);

        Map<String, String> user2 = new HashMap<>();
        user2.put(DatabaseManager.NAME, "John Smith");
        user2.put(DatabaseManager.PWD, "password");
        user2.put(DatabaseManager.DOB, "12751200000");
        user2.put(DatabaseManager.DL, "120398102");
        user2.put(DatabaseManager.PN, "EA23987429");
        user2.put(DatabaseManager.PHONE, "0419283949");
        user2.put(DatabaseManager.EMAIL, "john.smith@example.com");
        user2.put(DatabaseManager.ADS, "90 Bourke Street,VIC Melbourne:180 Collin Street,VIC Melbourne");
        databaseManager.save("john.smith@example.com", user2);
    }

    private class FacebookAuthTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

        @Override
        protected AsyncTaskResult<Boolean> doInBackground(Void... params) {
            AsyncTaskResult<Boolean> result;
            String password = databaseManager.getPassword(emailStr);
            if (password != null && password.equals(passwordStr)) {
                result = new AsyncTaskResult<>(true);
                Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
                User user = UserUtils.toUser(databaseManager.getUser(emailStr));
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                result = new AsyncTaskResult<>(false);
            }
            return result;
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Boolean> result) {
            if (!result.getResult()) {
                email.setError(getString(R.string.error_invalid_login));
                email.requestFocus();
                showProgress(false);
            }
        }
    }
}


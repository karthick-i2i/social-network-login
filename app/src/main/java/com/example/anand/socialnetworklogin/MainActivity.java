package com.example.anand.socialnetworklogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "GtjUdlcXqhlLQfgdKkJo3za4k";
    private static final String TWITTER_SECRET = "oru8zfEXfzjlegJPxSmLbGPIHFReDYMuvF1ehE8AXh09QXIZp7";


    private TextView info;
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private TwitterLoginButton twitterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSocialNetworkSdks();

        // view
        setContentView(R.layout.activity_main);
        info =(TextView) findViewById(R.id.info);

        // set facebok onlick event
        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);
        findViewById(R.id.fb_login_button).setOnClickListener(this);

        // google listener
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // linkedin listener
        findViewById(R.id.linkedin_login_button).setOnClickListener(this);

        // twitter listener
        twitterLogin = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        findViewById(R.id.twitter_login_button).setOnClickListener(this);
        twitterLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                info.setText("User ID: " + session.getUserId());
            }
            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void initializeSocialNetworkSdks() {
        //TODO : Initialize sdk based on some configuration. Load api keys from properties

        // Initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        // Google plus
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        // Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

    }

    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fb_login_button) {
            FacebookAuthentication facebookAuthentication = new FacebookAuthentication(fbLoginButton, info,callbackManager);
            facebookAuthentication.sigin();
        } else if(id == R.id.sign_in_button) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else if (id == R.id.linkedin_login_button) {
            LinkedInAuthentication linkedInAuthentication = new LinkedInAuthentication(info,getApplicationContext());
        } else if(id == R.id.twitter_login_button) {
            TwitterAuthentication twitterAuthentication = new TwitterAuthentication(twitterLogin,info);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle facebook login response
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Google signin - Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleAuthentication googleAuthentication = new GoogleAuthentication(result,info);
        }

        twitterLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.anand.socialnetworklogin;

import android.content.Intent;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by anand on 1/3/16.
 */
public class GoogleAuthentication implements SocialNetworkManager {


    private final GoogleSignInResult result;
    private final TextView info;

    public GoogleAuthentication(GoogleSignInResult result, TextView info) {
        this.result = result;
        this.info = info;
    }

    @Override
    public void sigin() {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            info.setText("User ID: " +result.getSignInAccount().getDisplayName() );
        } else {
            info.setText("Login Failed");
        }
    }
}

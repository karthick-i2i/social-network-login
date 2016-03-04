package com.example.anand.socialnetworklogin;

import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by anand on 1/3/16.
 */
public class FacebookAuthentication implements SocialNetworkManager {

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    private TextView info;


    public FacebookAuthentication(LoginButton fbLoginButton, TextView info,CallbackManager callbackManager) {
        this.fbLoginButton = fbLoginButton;
        this.info = info;
        this.callbackManager = callbackManager;
    }


    @Override
    public void sigin() {
        // Initialize facebook sdk
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("User ID: " + loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed");
            }

        });

    }
}

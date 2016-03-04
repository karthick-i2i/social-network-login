package com.example.anand.socialnetworklogin;

import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by anand on 1/3/16.
 */
public class TwitterAuthentication implements SocialNetworkManager{
    private final TwitterLoginButton twitterLogin;
    private final TextView info;

    public TwitterAuthentication(TwitterLoginButton twitterLogin, TextView info) {
        this.twitterLogin = twitterLogin;
        this.info = info;
    }

    @Override
    public void sigin() {

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
}

package com.example.anand.socialnetworklogin;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

/**
 * Created by anand on 1/3/16.
 */
public class LinkedInAuthentication extends Activity implements SocialNetworkManager{
    private final TextView info;
    private Context context;

    public LinkedInAuthentication(TextView info, Context applicationContext) {
        this.info = info;
        this.context = context;
    }

    @Override
    public void sigin() {

        LISessionManager.getInstance(context).init(this,buildScope(), new AuthListener() {
                    public void onAuthSuccess() {
                        info.setText("Login success!");
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        info.setText("Login failed!");
                    }
                }, true);
    }

    // This method is used to make permissions to retrieve data from linkedin
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }
}

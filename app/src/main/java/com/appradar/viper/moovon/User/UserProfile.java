package com.appradar.viper.moovon.User;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.appradar.viper.moovon.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by shaktiman on 29/09/16.
 */

public class UserProfile {

    public static DatabaseReference getUserRootReference()
    {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles);
    }

    public static Query getUserProfile(String userId) {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId);
    }

    public static String getLoggedOnUserId() {

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return "Guest";
    }

    public static String getLoggedOnUserDisplayName()
    {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }
        return "Guest";
    }


    public static DatabaseReference getDrinkTargetReference(String userId)
    {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId).child(DatabaseNodes.nodeDrinkTarget);
    }

    public static DatabaseReference getMoveTargetReference(String userId)
    {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId).child(DatabaseNodes.nodeMoveTarget);
    }


    public static void logout_user(Context context){
        //Do some task here
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                if (providerId == "google.com") {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(context.getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                            .enableAutoManage((FragmentActivity) context /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) context /* OnConnectionFailedListener */)
                            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                            .build();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                }
                            });
                } else {
                    LoginManager.getInstance().logOut();
                }
            }
        }
        FirebaseAuth.getInstance().signOut();

    }

    public static FirebaseUser getCurrentUser()
    {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            return FirebaseAuth.getInstance().getCurrentUser();
        }
        return null;
    }

}

package com.appradar.viper.moovon.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by shaktiman on 29/09/16.
 */

public class UserProfile {

    public static Query getUserProfile(String userId) {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId);
    }

    public static String getLoggedOnUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
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
}

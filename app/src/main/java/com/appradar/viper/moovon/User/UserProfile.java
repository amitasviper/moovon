package com.appradar.viper.moovon.User;

import com.google.firebase.auth.FirebaseAuth;
<<<<<<< Updated upstream
import com.google.firebase.database.DatabaseReference;
=======
import com.google.firebase.auth.FirebaseUser;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public static DatabaseReference getDrinkTargetReference(String userId)
    {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId).child(DatabaseNodes.nodeDrinkTarget);
    }

    public static DatabaseReference getMoveTargetReference(String userId)
    {
        return FirebaseDatabase.getInstance().getReference(DatabaseNodes.rootUserProfiles).child(userId).child(DatabaseNodes.nodeMoveTarget);
    }

=======
    public static FirebaseUser getCurrentUser()
    {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            return FirebaseAuth.getInstance().getCurrentUser();
        }
        return null;
    }
>>>>>>> Stashed changes
}

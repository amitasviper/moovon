package mutils;

import com.appradar.viper.moovon.User.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ServiceBeforeSelf on 9/29/2016.
 */

public class UserProgress {
    private int year;
    private int month;
    private int day;

    private long waterConsumedInYear;
    private long walkedInYear;

    private long waterConsumedInMonth;
    private long walkedInMonth;

    private long waterConsumedInDay;
    private long walkedInDay;

    public static final String rootUserProgress = "UserProgress";
    public static final String rootPaani = "Paani";
    public static final String rootChal = "Chal";
    public static final String childCount = "Count";

    public static final String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static UserProgress instance;

    UserProgress()
    {

    }

    public static UserProgress getInstance()
    {
        if(instance == null)
        {
            instance = new UserProgress();
        }
        return instance;
    }

    public int getYear()
    {
        return this.year;
    }

    public int getMonth()
    {
        return this.month;
    }

    public int getDay()
    {
        return this.day;
    }

    public long getWaterConsumedInYear(){ return waterConsumedInYear;}
    public long getWalkedInYear() { return walkedInYear;}
    public long getWaterConsumedInMonth() { return waterConsumedInMonth;}
    public long getWalkedInMonth(){ return walkedInMonth;}
    public long getWaterConsumedInDay(){ return waterConsumedInDay;}
    public long getWalkedInDay() { return walkedInDay;}

    public void setWaterConsumedInYear( long waterConsumedInYear)
    {
        this.waterConsumedInYear = waterConsumedInYear;
    }

    public void setWaterConsumedInMonth(long waterConsumedInMonth)
    {
        this.waterConsumedInMonth = waterConsumedInMonth;
    }


    public void setWaterConsumedInDay(long waterConsumedInDay)
    {
        this.waterConsumedInDay = waterConsumedInDay;
    }

    public void setWalkedInYear(long walkedInYear)
    {
        this.walkedInYear = walkedInYear;
    }

    public void setWalkedInMonth(long walkedInMonth)
    {
        this.walkedInMonth = walkedInMonth;
    }

    public void setWalkedInDay(long walkedInDay)
    {
        this.walkedInDay = walkedInDay;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put(DatabaseNodes.childAnswersCount, this.answersCount);
//        result.put(DatabaseNodes.childCategory, this.category);
        return result;
    }

    public static String getCurrentTimeAsKey()
    {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);

        String key = String.valueOf(hours) + "_" + String.valueOf(minutes);
        return key;
    }

    public int getCurrentYear()
    {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public int getCurrentMonth()
    {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public int getCurrentDay()
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    void updateYearlyConsumption(String year, String childNode, final long incrementCount)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(rootUserProgress).child(UserProfile.getLoggedOnUserId()).child(year).child(childNode).child(childCount).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count  = 0;
                if(dataSnapshot.exists())
                {
                    count = dataSnapshot.getValue(long.class);
                }
                count += incrementCount;
                ref.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error in case of network failure.
            }
        });
    }

    void updateMonthlyConsumption(String year, String month, String childNode, final long incrementCount)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(rootUserProgress).child(UserProfile.getLoggedOnUserId()).child(year).child(childNode).child(month).child(childCount).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count  = 0;
                if(dataSnapshot.exists())
                {
                    count = dataSnapshot.getValue(long.class);
                }
                count += incrementCount;
                ref.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error in case of network failure.
            }
        });
    }

    public static Query getDailyProgressReference(String year, String month, String day, String childNode)
    {
        return FirebaseDatabase.getInstance().getReference(rootUserProgress).child(UserProfile.getLoggedOnUserId()).child(year).child(childNode).child(month).child(day);
    }

    public static Query getMonthlyProgressReference(String year, String month, String childNode)
    {
        return FirebaseDatabase.getInstance().getReference(rootUserProgress).child(UserProfile.getLoggedOnUserId()).child(year).child(childNode).child(month);
    }

    void updateDailyConsumption(String year, String month, String day, String childNode, final long incrementCount)
    {
        final DatabaseReference ref = getDailyProgressReference(year, month, day, childNode).getRef().child(childCount).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count  = 0;
                if(dataSnapshot.exists())
                {
                    count = dataSnapshot.getValue(long.class);
                }
                count += incrementCount;
                ref.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error in case of network failure.
            }
        });
    }


    void addNewProgress(long value, String childNode)
    {
        String key = getCurrentTimeAsKey();
        String year = String.valueOf(getCurrentYear());
        String month = String.valueOf(getCurrentMonth());
        String day = String.valueOf(getCurrentDay());

        getDailyProgressReference(year, month, day, childNode).getRef().child(key).setValue(value);
        updateDailyConsumption(year, month, day, childNode, value);
        updateMonthlyConsumption(year, month, childNode, value);
        updateYearlyConsumption(year, childNode, value);
    }

    public void addNewWaterProgress(long quantity)
    {
        addNewProgress(quantity, rootPaani);
    }

    public void addNewWalkingProgress(long distance)
    {
        addNewProgress(distance, rootChal);
    }

    public void getTodayReportForPaani()
    {
        String year = String.valueOf(getCurrentYear());
        String month = String.valueOf(getCurrentMonth());
        String day = String.valueOf(getCurrentDay());

        getDailyProgressReference(year, month, day, rootPaani).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey() == childCount)
                {
                    // assign for gettint total consumption on that day.
                }
                else
                {
                    String timeKey = dataSnapshot.getKey();
                    String hours = getHoursFromKey(timeKey);
                    String minutes = getMinutesFromKey(timeKey);
                    long countOnTimeKey = dataSnapshot.getValue(long.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getTodayReportForWalk()
    {
        String year = String.valueOf(getCurrentYear());
        String month = String.valueOf(getCurrentMonth());
        String day = String.valueOf(getCurrentDay());


        getDailyProgressReference(year, month, day, rootChal).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey() == childCount)
                {
                    // assign for gettint total consumption on that day.
                }
                else
                {
                    String timeKey = dataSnapshot.getKey();
                    String hours = getHoursFromKey(timeKey);
                    String minutes = getMinutesFromKey(timeKey);
                    long countOnTimeKey = dataSnapshot.getValue(long.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMonthlyReportForWalk(int nDays)
    {
        String year = String.valueOf(getCurrentYear());
        String month = String.valueOf(getCurrentMonth()-1);


        for(int day=1; day <= nDays; day++)
        {
            getMonthlyProgressReference(year, month, rootChal).getRef().child(String.valueOf(day)).child(childCount).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists() )
                    {
                        long consumptionCount = dataSnapshot.getValue(long.class);
                        // process data here
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
        }
    }

    public String getHoursFromKey(String key)
    {
        String[] timeUnit = key.split("_");
        return timeUnit[0];
    }

    public String getMinutesFromKey(String key)
    {
        String[] timeUnit = key.split("_");
        return timeUnit[1];
    }
}

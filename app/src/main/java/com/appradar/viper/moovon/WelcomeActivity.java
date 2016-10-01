package com.appradar.viper.moovon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.appradar.viper.moovon.User.UserProfile;
import com.shawnlin.numberpicker.NumberPicker;

import mutils.AppSharedPreferences;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
//        if (!prefManager.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.userinfo_drink_targets,
                R.layout.userinfo_movingtargets};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int currentpage = viewPager.getCurrentItem();
                NumberPicker numberPicker;
                int value;
                switch (currentpage){
                    case 0:
                        numberPicker = (NumberPicker) findViewById(R.id.number_picker_drinks);
                        value = numberPicker.getValue();

                        UserProfile.getMoveTargetReference(UserProfile.getLoggedOnUserId()).setValue(value);
                        AppSharedPreferences.getInstance().setPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ, value);
                        break;
                    case 1:
                        numberPicker = (NumberPicker) findViewById(R.id.number_picker_move);
                        value = numberPicker.getValue();

                        UserProfile.getMoveTargetReference(UserProfile.getLoggedOnUserId()).setValue(value);
                        AppSharedPreferences.getInstance().setPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ, value);
                        break;



                }
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void moveToNextPage() {
        // checking for last page
        // if last page home screen will be launched
        int current = getItem(+1);
        if (current < layouts.length) {
            // move to next screen
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }

    private void launchHomeScreen() {
//        UserProfile currentUser = new UserProfile(UserProfileNode.getLoggedOnUserDisplayName(), reasonToJoinArmy, experience, occupation);
//        UserProfileNode.getUserProfile(UserProfileNode.getLoggedOnUserId()).getRef().updateChildren(currentUser.toMap());

        AppSharedPreferences.getInstance().setPropInteger(AppSharedPreferences.SETTING_TARGET_DRINK, 6);
        AppSharedPreferences.getInstance().setPropInteger(AppSharedPreferences.SETTING_TARGET_DRINK, 2000);
        AppSharedPreferences.getInstance().setPropInteger(AppSharedPreferences.APP_LAUNCH_FLAG, 1);

        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.done));
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            if(position == 0) {
                final Switch aSwitch = (Switch) view.findViewById(R.id.drink_switch);
                final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.drink_layout_take_info);
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(linearLayout.getVisibility() == View.INVISIBLE) {
                            linearLayout.setVisibility(View.VISIBLE);

                            final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.number_picker_drinks);

                            numberPicker.setDividerColorResource(R.color.colorPrimary);

                            numberPicker.setTextColorResource(R.color.graph2);
                            numberPicker.setTextSize(getResources().getDimension(R.dimen.txt_description));
                            numberPicker.setTypeface(Typeface.DEFAULT_BOLD);



                        }else{
                            linearLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
            else if(position == 1) {
                final Switch aSwitch = (Switch) view.findViewById(R.id.move_switch);
                final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.move_layout_take_info);
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(linearLayout.getVisibility() == View.INVISIBLE) {
                            linearLayout.setVisibility(View.VISIBLE);
                            final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.number_picker_move);

                            numberPicker.setDividerColorResource(R.color.colorPrimary);
                            numberPicker.setTypeface(Typeface.DEFAULT_BOLD);

                            numberPicker.setTextColorResource(R.color.graph1);
                            numberPicker.setTextSize(getResources().getDimension(R.dimen.txt_description));

                        }else{
                            linearLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appradar.viper.moovon.R;


public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        /*ImageView iv_image_one = (ImageView) v.findViewById(R.id.iv_image_one);
        ImageView iv_image_two = (ImageView) v.findViewById(R.id.iv_image_two);

        ImageView iv_image_three = (ImageView) v.findViewById(R.id.iv_image_three);
        ImageView iv_image_four = (ImageView) v.findViewById(R.id.iv_image_four);

        Glide.with(getContext())
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .placeholder(R.drawable.default_image)
                .into(iv_image_one);

        Glide.with(getContext())
                .load("http://phandroid.s3.amazonaws.com/wp-content/uploads/2016/04/htc_10_wallpapers_00.jpg")
                .placeholder(R.drawable.default_image)
                .into(iv_image_two);

        Picasso.with(getContext())
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .placeholder(R.drawable.default_image)
                .into(iv_image_three);

        Picasso.with(getContext())
                .load("http://phandroid.s3.amazonaws.com/wp-content/uploads/2016/04/htc_10_wallpapers_00.jpg")
                .placeholder(R.drawable.default_image)
                .into(iv_image_four);*/

        return v;
    }

    public static SecondFragment newInstance(String text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
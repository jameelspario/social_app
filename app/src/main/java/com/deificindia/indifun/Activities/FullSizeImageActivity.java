package com.deificindia.indifun.Activities;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun.fragments.ImageViewFragment;
import com.deificindia.indifun.Model.ImagesParsble;
import com.deificindia.indifun.R;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullSizeImageActivity extends AppCompatActivity {

    public static final String IMG_PARAM = "imgparam";
    public static final String IMG_PARAM_LOCAL = "IMG_PARAM_LOCAL";

    ViewPager2 pager2;
    LinearLayout dotView;
    ImagesParsble  imgs;
    ArrayList<Image> imgs2; //LocalImages

    private int dotscount;
    ImageView[] dots;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_size_image);

        imgs = getIntent().getParcelableExtra("URL");
        imgs2 = getIntent().getParcelableArrayListExtra("URLLOCAL");
        int n = getIntent().getIntExtra("TAB", 0);

        pager2 = findViewById(R.id.viewpager);
        dotView = findViewById(R.id.dotView);

        findViewById(R.id.imgClose).setOnClickListener(v-> supportFinishAfterTransition());

        Fragmentdapter adapter = new Fragmentdapter(getSupportFragmentManager(), getLifecycle());

        adapter.setImages(imgs, imgs2);

        pager2.setAdapter(adapter);

        pager2.setCurrentItem(n);

        if(imgs!=null) dotView(imgs.getImages().size());
        if(imgs2!=null) dotView(imgs2.size());

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                scrollToDot(position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        scrollToDot(n);

    }

    class Fragmentdapter extends FragmentStateAdapter {

        ImagesParsble images;
        ArrayList<Image> images2;

        public Fragmentdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new ImageViewFragment();
            Bundle args = new Bundle();

            if(images!=null) args.putString(IMG_PARAM, images.getImages().get(position));
            if(images2!=null) args.putParcelable(IMG_PARAM_LOCAL, images2.get(position));

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            if(images!=null) return images.getImages().size();
            if(images2!=null) return images2.size();
            return 0;

        }

        public void setImages(ImagesParsble images, ArrayList<Image> images2) {
            this.images = images;
            this.images2 = images2;
        }
    }

    void dotView(int count){
        dotscount = count;
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            ImageView img = new ImageView(this);
            dots[i] = img;
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_activ_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            dotView.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));

    }

    void scrollToDot(int pos1){
        for(int i = 0; i< dotscount; i++){
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_activ_dots));
        }

        dots[pos1].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}

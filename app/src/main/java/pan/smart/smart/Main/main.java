package pan.smart.smart.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import pan.smart.smart.Near.nearByFragment;
import pan.smart.smart.R;
import pan.smart.smart.Search.searchFragment;
import pan.smart.smart.contacts.contactsFragment;

import static pan.smart.smart.Utills.Constant.tabSelected;


public class main extends AppCompatActivity {

    public static ViewPager viewPager;
    ImageView tab_icon;
    public SharedPreferences mySharedPreferences;
    String token;

    private int[] navIcons = {

            R.drawable.loc,
            R.drawable.search_icon,
            R.drawable.profile_icon,


    };


    private int[] navLabels = {

            R.string.near,
            R.string.search,
            R.string.profile,


    };

    public static TabLayout tabLayout;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_tab_content);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupViewPager(viewPager);




        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        LinearLayout linearLayout=(LinearLayout)tab.getCustomView();
                        ImageView v=(ImageView)linearLayout.getChildAt(0);
                        TextView txt = (TextView)linearLayout.getChildAt(1);
                        v.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);
                        txt.setTextColor(getResources().getColor(R.color.app_color));


                        break;

                    case 1:
                        LinearLayout linearLayout1=(LinearLayout)tab.getCustomView();
                        ImageView v1=(ImageView)linearLayout1.getChildAt(0);
                        TextView  txt1 = (TextView)linearLayout1.getChildAt(1);
                        v1.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);
                        txt1.setTextColor(getResources().getColor(R.color.app_color));

                        break;
                    case 2:

                        LinearLayout linearLayout2=(LinearLayout)tab.getCustomView();
                        ImageView v2=(ImageView)linearLayout2.getChildAt(0);
                        TextView  txt2 = (TextView)linearLayout2.getChildAt(1);
                        // v2.setColorFilter(Color.parseColor(String.valueOf(R.color.blue_purple)), PorterDuff.Mode.SRC_IN);
                        txt2.setTextColor(getResources().getColor(R.color.app_color));
                        v2.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);

                        // v2.setImageResource(R.mipmap.home_selected);
                        clearBackStack();





                        break;
                    case 3:

                        LinearLayout linearLayout3=(LinearLayout)tab.getCustomView();
                        ImageView v3=(ImageView)linearLayout3.getChildAt(0);
                        TextView  txt3 = (TextView)linearLayout3.getChildAt(1);
                        v3.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);
                        txt3.setTextColor(getResources().getColor(R.color.app_color));


                        break;
                    case 4:

                        LinearLayout linearLayout4=(LinearLayout)tab.getCustomView();
                        ImageView v4=(ImageView)linearLayout4.getChildAt(0);
                        TextView  txt4 = (TextView)linearLayout4.getChildAt(1);
                        v4.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);
                        txt4.setTextColor(getResources().getColor(R.color.app_color));


                        break;

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                LinearLayout linearLayout=(LinearLayout)tab.getCustomView();
                ImageView v=(ImageView)linearLayout.getChildAt(0);
                TextView  txt = (TextView)linearLayout.getChildAt(1);
                v.setColorFilter(Color.parseColor("#81949c"), PorterDuff.Mode.SRC_IN);
                txt.setTextColor(Color.parseColor("#81949c"));
//                if(tab.getPosition()==2)
//                {
//                    txt.setTextColor(getResources().getColor(R.color.blue_purple));
//                    v.setImageResource(R.mipmap.home_gry);
//                }
//                else
//                    {
//                        v.setColorFilter(Color.parseColor("#81949c"), PorterDuff.Mode.SRC_IN);
//                        txt.setTextColor(Color.parseColor("#81949c"));
//                    }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nav_tab, null);

            // get child TextView and ImageView from this layout for the icon and label
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_label.setText(getResources().getString(navLabels[i]));


            // set the home to be active at first
            if(i == 0) {
                tab_label.setTextColor(getResources().getColor(R.color.app_color));
                tab_icon.setImageResource(navIcons[i]);
                if(tabSelected.equals("")) {
                    //  tab_icon.setImageResource(R.mipmap.home_selected);
                    tab_icon.setColorFilter(Color.parseColor("#E56301"), PorterDuff.Mode.SRC_IN);
                }


            }

            else
            {
                tab_icon.setImageResource(navIcons[i]);
            }

            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }


//        if(tabSelected.equals(""))
//        {
//            tabLayout.getTabAt(2).select();
//        }
//        else
//        {
//            tabLayout.getTabAt(1).select();
//
//        }

    }
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.insertNewFragment(new nearByFragment());
        adapter.insertNewFragment(new searchFragment());
        adapter.insertNewFragment(new contactsFragment());
//        adapter.insertNewFragment(new WishListFragment());



            //Toast.makeText(getActivity(),Category_ID,Toast.LENGTH_LONG).show();



        viewPager.setAdapter(adapter);







    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        private boolean locked = false;
        private int lockedIndex;


        public void setLocked(boolean locked, int page) {
            this.locked = locked;
            lockedIndex = page;
            notifyDataSetChanged();
        }
        @Override
        public Fragment getItem(int position) {
            if (locked)
                return mFragmentList.get(lockedIndex);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (locked) return 1;
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }


    }

    public class LockableViewPager extends ViewPager {
        private boolean swipeable;
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public LockableViewPager(Context context) {
            super(context);
        }

        public LockableViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.swipeable = true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (this.swipeable) {
                return super.onTouchEvent(event);
            }
            return false;
        }

        @Override

        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (this.swipeable) {
                return super.onInterceptTouchEvent(event);
            }
            return false;
        }

        public void setSwipeable(boolean swipeable) {
            this.swipeable = swipeable;
        }
        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }
    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();

        }



    }
    @Override
    public void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

    @Override
    public void onStart() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onStart();
    }


}

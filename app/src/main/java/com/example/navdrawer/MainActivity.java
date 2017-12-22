package com.example.navdrawer;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity
{
    private IProfile profile;
    private IProfile profile2;
    private IProfile profile3;

    private static final int PROFILE_SETTING = 1;
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("School Connect");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName("Elham Ahmed").withEmail("elham.sec@dallel.com").withIcon(getResources().getDrawable(R.drawable.profile1));
        profile2 = new ProfileDrawerItem().withName("Wesam Ahmed").withEmail("wesam.pre@dallel.com").withIcon(getResources().getDrawable(R.drawable.profile2)).withIdentifier(2);
        profile3 = new ProfileDrawerItem().withName("Tarek Ahmed").withEmail("tarek.pri@dallel.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        // Create the AccountHeader
        buildHeader(false, savedInstanceState);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Alarabiya-Font.ttf");
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withDrawerGravity(GravityCompat.START)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("الرئيسية").withIcon(R.drawable.ic_home).withIconTintingEnabled(true).withTypeface(face),
                        new PrimaryDrawerItem().withName("المواد الدراسية").withIcon(R.drawable.ic_course).withIconTintingEnabled(true).withTypeface(face),
                        new PrimaryDrawerItem().withName("جدول الامتحانات").withIcon(R.drawable.ic_schedule).withIconTintingEnabled(true).withTypeface(face),
                        new PrimaryDrawerItem().withName("جدول الدراسي").withIcon(R.drawable.ic_timetable).withIconTintingEnabled(true).withTypeface(face),
                        new SectionDrawerItem().withName("عن الطالب").withTypeface(face),
                        new SecondaryDrawerItem().withName("كشف درجات").withIcon(R.drawable.ic_grades).withIconTintingEnabled(true).withTypeface(face),
                        new SecondaryDrawerItem().withName("الملف الشخصي").withIcon(R.drawable.ic_profile).withIconTintingEnabled(true).withTypeface(face),
                        new SecondaryDrawerItem().withName("المساعدة").withIcon(R.drawable.ic_help).withIconTintingEnabled(true).withTypeface(face)
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        MainActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        profile2,
                        profile3
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING)
                        {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Elham Ahmed").withEmail("elham.sec@dallel.com").withIcon(getResources().getDrawable(R.drawable.profile1));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }
}

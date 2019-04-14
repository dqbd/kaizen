package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    RelativeLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);

        addImage(getDrawable(R.drawable.avatar), 50, 140);
        addImage(getDrawable(R.drawable.avatar2), 98, 180);
        addImage(getDrawable(R.drawable.avatar3), 120, 293);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
         navigation.setOnNavigationItemSelectedListener(this);

        // Ensure correct menu item is selected (where the magic happens)
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_record: {
                startActivity(new Intent(this, DetectorActivity.class));
                break;
            }
            case R.id.action_stats: {
                break;
            }
        }
        return true;
    }

    void addImage(Drawable drawable, int top, int left) {
        CircleImageView testImage = new CircleImageView(this);
//        testImage.setBorderWidth(2);
//        testImage.setBorderColor(getResources().getColor(R.color.colorAvatarBorder));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round(convertDpToPixel(30, this)), Math.round(convertDpToPixel(30, this)));
        params.leftMargin = (int) convertDpToPixel(left, this);
        params.topMargin = (int) convertDpToPixel(top, this);

        testImage.setImageDrawable(drawable);
        map.addView(testImage, params);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

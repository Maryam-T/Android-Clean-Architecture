package com.anjeldeveloper.clipsaz.util.drawer;

import com.anjeldeveloper.clipsaz.model.Drawer;
import java.util.ArrayList;

public class DataSource {

    public static ArrayList<Drawer> getDrawers() {
        ArrayList<Drawer> drawers = new ArrayList<>();
        drawers.add(new Drawer(1, android.R.drawable.star_big_off));
        drawers.add(new Drawer(2, android.R.drawable.ic_dialog_info));
        return drawers;
    }

}

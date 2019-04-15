package org.tensorflow.lite.examples.detection.dummy;

import android.content.Context;

import org.tensorflow.lite.examples.detection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static List<DummyItem> initializeItems(Context context) {
        if (ITEMS.size() == 0) {
            addItem(new DummyItem("1", "Jana Šťastná", "Kolo", "Ujela jsem 16 km, ušetřil 1300g CO2", "Další", R.drawable.kolo, R.drawable.avatar2));
            addItem(new DummyItem("2", "Jára Cimrman", "Oblečení", "Dnešní úlovek ze sekáče, slow fashion", "Další", R.drawable.obleceni, R.drawable.avatar));
            addItem(new DummyItem("3", "Jan Novák", "Jídlo", "Nakoupil jsem na místním farmářskem trhu", "Další", R.drawable.jidlo, R.drawable.avatar3));
        }
        return ITEMS;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final int avatar;
        public final String user;
        public final String label;
        public final String content;
        public final String details;
        public final int image;

        public DummyItem(String id, String user, String label, String content, String details, int bitmap, int avatar) {
            this.id = id;
            this.label = label;
            this.content = content;
            this.details = details;
            this.image = bitmap;
            this.user = user;
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

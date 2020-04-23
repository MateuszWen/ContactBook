package com.mw.homework.tasks;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.LogPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Task - reprezentuje pojedynczy item na liście

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Contact> ITEMS = new ArrayList<Contact>(); //lista<Task> ITEMS zawira wszystko co dodaliśmy do listy "rzeczy"...
                                                                // UWAGA! Powyższa metoda nie odpowiada za wyświetlanie. Metody wyświetlające znajdują się w MyTaskRecyclerViewAdapter

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Contact> ITEM_MAP = new HashMap<String, Contact>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static void addItem(Contact item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

        for (Map.Entry<String,Contact> entry : ITEM_MAP.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().id;
            Log.d("ITEM_MAP", key +" : "+value );
        }
    }

    private static Contact createDummyItem(int position) {
        return new Contact(String.valueOf(position), "Imie"+position, "Nazwisko"+position, "01/01/2020", "111222333", "drawable "+position);
    }


    public static void removeItem(int position){
        //Get the id to locate the item in the items map
        String itemId = ITEMS.get(position).id;
        //remove the item from List
        ITEMS.remove(position);
        //remove the item from map
        ITEM_MAP.remove(itemId);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Contact implements Parcelable {
        public final String id;
        public final String name;
        public final String surname;
        public final String birthday;
        public final String phone_number;
        public final String picPath;

        public Contact(String id, String name, String surname, String birthday, String phone_number, String picPath) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.birthday = birthday;
            this.phone_number = phone_number;
            this.picPath = picPath;
        }
        public Contact() {
            this.id = ""+(ITEMS.size()+1);
            this.name = "PrzykladoweImie";
            this.surname = "PrzykladoweNazwisko";
            this.birthday = "12.12.1980";
            this.phone_number = "1234432112";
            this.picPath = "drawable 5";
        }

        protected Contact(Parcel in) {
            id = in.readString();
            name = in.readString();
            surname = in.readString();
            birthday = in.readString();
            phone_number = in.readString();
            picPath = in.readString();
        }

        public static final Creator<Contact> CREATOR = new Creator<Contact>() {
            @Override
            public Contact createFromParcel(Parcel in) {
                return new Contact(in);
            }

            @Override
            public Contact[] newArray(int size) {
                return new Contact[size];
            }
        };

        @Override
        public String toString() {
            return name + surname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(surname);
            dest.writeString(birthday);
            dest.writeString(phone_number);
            dest.writeString(picPath);
        }
    }
}

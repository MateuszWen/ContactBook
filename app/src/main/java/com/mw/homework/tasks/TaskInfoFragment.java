package com.mw.homework.tasks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.mw.homework.MainActivity;
import com.mw.homework.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_task_info, container, false);
        return v;
    }

    public void displayTask(TaskListContent.Contact contact){                             // <-- służy do wyświetlania szczegółów danego Task'a
        FragmentActivity activity = getActivity(); //get the holding Activity

        //Find the elements used to display the data of the Task
        TextView contactInfoName = activity.findViewById(R.id.contactInfoName);
        TextView contactInfoPhoneNumber = activity.findViewById(R.id.contactInfoPhoneNumber);
        ImageView contactInfoImage = activity.findViewById(R.id.contactInfoImage);
        TextView contactInfoBirthday = activity.findViewById(R.id.contactInfoBirthday);

        //Apply the data
        contactInfoName.setText(contact.name + " " + contact.surname);
        contactInfoPhoneNumber.setText(getString(R.string.phone_number_fragment) + " " + contact.phone_number);
        contactInfoBirthday.setText(getString(R.string.birthday_fragment) + " " +contact.birthday);

        if(contact.picPath != null && !contact.picPath.isEmpty()){
            //if picPath contains word "drawable" get an appropirate drawable resource
            Drawable taskDrawable;
            switch (contact.picPath){
                case "drawable 1":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_1);
                    break;
                case "drawable 2":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_2);
                    break;
                case "drawable 3":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_3);
                    break;
                case "drawable 4":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_4);
                    break;
                case "drawable 5":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_5);
                    break;
                case "drawable 6":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_6);
                    break;
                case "drawable 7":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_7);
                    break;
                case "drawable 8":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_8);
                    break;
                case "drawable 9":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_9);
                    break;
                case "drawable 10":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_10);
                    break;
                case "drawable 11":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_11);
                    break;
                case "drawable 12":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_12);
                    break;
                case "drawable 13":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_13);
                    break;
                case "drawable 14":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_14);
                    break;
                case "drawable 15":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_15);
                    break;
                case "drawable 16":
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_16);
                    break;
                default:
                    taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_1);
            }
            contactInfoImage.setImageDrawable(taskDrawable);
        }else{
            contactInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.avatar_16));
        }
    }

    //metoda służy do odczytywania danych dostarczonych z klasu MainActivity w obiekcie klasy Intent (że jak na oknie gównym wybraliśmy któryś kontakt, to metoda ta odpowiada za przesłanie informacji o tym kontakcie do kolejnego Activity)
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //Get the intent golding the Task details and display them
        Intent intent = getActivity().getIntent();
        if(intent != null){
            TaskListContent.Contact reveivedContact = intent.getParcelableExtra(MainActivity.taskExtra);
            if(reveivedContact != null){
                displayTask(reveivedContact);
            }
        }
    }
}

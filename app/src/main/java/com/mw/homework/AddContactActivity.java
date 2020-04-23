package com.mw.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mw.homework.tasks.TaskListContent;

import java.util.Random;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void onClickButtonAdd(View view) {

        EditText name = findViewById(R.id.edittext_name);
        EditText surname = findViewById(R.id.edittext_surname);
        EditText birthday = findViewById(R.id.edittext_birthday);
        EditText phone_number = findViewById(R.id.edittext_phonenumber);
        String nameString = name.getText().toString();
        String surnameString = surname.getText().toString();
        String birthdayString = birthday.getText().toString();
        String phone_numberString = phone_number.getText().toString();

        if(Validators.isThisDateValid(birthdayString, "dd/MM/yyyy") && Validators.isValidE123(phone_numberString)){
            Random rand = new Random();
            String selectImage = "drawable " + (rand.nextInt(17)+1);

            if(!(nameString.isEmpty() && surnameString.isEmpty())) {
                //Add a default Task object to myTask list if no data is input in EditTexts
                TaskListContent.addItem(new TaskListContent.Contact((""+(TaskListContent.ITEMS.size() + 1)), nameString, surnameString, birthdayString, phone_numberString, selectImage));
                //Notify the TaskFragment adapter that the dataset change

                //Automatically hide the keyboard after AddButton press
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                finish();
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Write down all obligatory data...";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Invalid birthday date or phone number...";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    @Override
    public void finish(){
        Intent returnIntent = new Intent();
        //returnIntent.putExtra("passed_item");
        // setResult(RESULT_OK);
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
    }
}

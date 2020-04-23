package com.mw.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mw.homework.tasks.TaskFragment;
import com.mw.homework.tasks.TaskInfoActivity;
import com.mw.homework.tasks.TaskInfoFragment;
import com.mw.homework.tasks.TaskListContent;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener, DeleteDialog.OnDeleteDialogInteractionListener, CallDialog.OnCallDialogInteractionListener {

    //metoda uruchamiana jest za kazdym razem kiedy tworzone jest główne Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        mFab.setOnClickListener(new View.OnClickListener() { // <-- metoda obsługująca kliknięcie FloatingButton
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, CONTEXT_INCLUDE_CODE);
            }
        });
    }

    //metoda jest wywoływana za każdym razem kiedy z innego Activity wrócimy do głównego (MainActivity) I POSIADAMY do odczytania jakieś dane
    //(W tym wypadku zawsze zwracamy z innego Activity pustkę. Metoda służy wyłącznie do odświeżenia listy kontaktów)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTEXT_INCLUDE_CODE && resultCode == RESULT_OK) {
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    //metoda dodaje nowy Task na listę jednocześnie robiąc refresh całej listy
    public void refreshList() {
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
    }

    private void displayTaskInFragment(TaskListContent.Contact contact) {
        //Find he displayFragment
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if (taskInfoFragment != null) {
            //Display the task if displayFragment exists
            taskInfoFragment.displayTask(contact);
        }
    }

    //obsługa kliknięcia na pasek z taskiem (metoda ma podział co ma się stac, gdy ekran jest pionowo lub gdy jest poziomo)
    @Override
    public void onListFragmentClickInteraction(TaskListContent.Contact contact, int position) {
        Toast.makeText(this, getString(R.string.item_selected_msg), Toast.LENGTH_SHORT).show();
        //Check the current orientation of the device
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            //The device is in landscape orientation. Display the task in a Fragment
            displayTaskInFragment(contact);
        } else {
            //The device is in portrait orientation. Display the task in a new Activity
            startSecondActivity(contact, position);
        }
    }

    private void startSecondActivity(TaskListContent.Contact contact, int position) {
        Intent intent = new Intent(this, TaskInfoActivity.class); //packageContent - przechowuje wszelkie dane z aktualnego activity (tu: MainActivity)
        intent.putExtra(taskExtra, contact); //putExtra(...) - przechowuje jakies dodatkowe dane, przez nas ustanowione
        startActivity(intent);
    } // <-- metoda wywoływana przez metodę wyżej (odpowiada za otwieranie kontaktu w nowym oknie(Activity))

    public static final String taskExtra = "taskExtra";

    //odpowiada za wykonanie określonej czynności po dłuższym przytrzymaniu palca na danym Task'u
    private static final int REQUEST_CALL = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //PONIŻSZE METODY ODNOSZĄ SIĘ TYLKO DO OBSŁUGI PRZYTRZYMANIA PALCA NA TASK'U
    //usuwanie z pomocą przycisku "Kosz"
    @Override
    public void onListFragmentLongClickInteraction(TaskListContent.Contact contact, int position) {
    showCallDialog(contact);
    }
    private void showCallDialog(TaskListContent.Contact contact){
        CallDialog.newInstance(contact).show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onCallDialogPositiveClick(DialogFragment dialog, TaskListContent.Contact contact) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contact.phone_number)));
        }
    }

    @Override
    public void onCallDialogNegativeClick(DialogFragment dialog, TaskListContent.Contact contact) {

    }

    //PONIŻSZE METODY ODNOSZĄ SIĘ TYLKO DO USUWANIA TASKÓW
    //usuwanie z pomocą przycisku "Kosz"
    @Override
    public void onListFragmentButtonClickInteraction(int position) {
        Toast.makeText(this, getString(R.string.long_click_msg), Toast.LENGTH_SHORT).show();
        //Show the delte confirmation dialog
        showDeleteDialog(); //<-- funkcja do usuwania, znajduje się na dole kodu
        //Store the position of the selected element
        currentItemPosition=position;
    } //<-- obsługa przycisku do usuwania (tu: przycisk "Kosz")

    //Usuwanie elementów
    private int currentItemPosition = -1;

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //If the user confirmed the deletion of an item
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size()){
            //If the currentItemPosition is correct. Remove the elementy from the TaskListContent list
            TaskListContent.removeItem(currentItemPosition);
            //Notify the TaskFragment adapter of the dateset change
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //If the user cancelled the deletion of the item
        View v = findViewById(R.id.floatingActionButton2);
        if(v != null){
            //show a Snackbar displaying a message and allowing the user to retry the deletion of an item
            Snackbar.make(v,getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG).setAction(getString(R.string.retry_msg), new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Yhe user pressed "Retry" button. Show the deleteDialog once again
                    showDeleteDialog();
                }
            }).show();
        }

    }

}

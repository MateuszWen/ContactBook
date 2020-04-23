package com.mw.homework;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.mw.homework.CallDialog;

import com.mw.homework.tasks.TaskListContent;

public class CallDialog extends DialogFragment {

    private CallDialog.OnCallDialogInteractionListener mListener;
    private TaskListContent.Contact contact;

    public CallDialog(TaskListContent.Contact contact) {
        this.contact = contact;
        // Required empty public constructor
    }

    public static CallDialog newInstance(TaskListContent.Contact contact){
        return new CallDialog(contact);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // AlertDialog.Builder will be used to create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the message displayed in the dialog
        builder.setMessage(getString(R.string.call_question) + " " + contact.name + "?");
        // Set the text and action for the positive button click
        builder.setPositiveButton(getString(R.string.dialog_call_yes), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnCallDialogInteractionListener interface of positive button click
                mListener.onCallDialogPositiveClick(CallDialog.this, contact);
            }
        });
        // Set the text and action for the negative button click
        builder.setNegativeButton(getString(R.string.dialog_call_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnCallDialogInteractionListener interface of negative button click
                mListener.onCallDialogNegativeClick(CallDialog.this, contact);
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallDialog.OnCallDialogInteractionListener) {
            mListener = (CallDialog.OnCallDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCallDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCallDialogInteractionListener {
        void onCallDialogPositiveClick(DialogFragment dialog, TaskListContent.Contact contact);
        void onCallDialogNegativeClick(DialogFragment dialog, TaskListContent.Contact contact);
    }
}

package com.mw.homework.tasks;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.homework.R;
import com.mw.homework.tasks.TaskFragment.OnListFragmentInteractionListener;
import com.mw.homework.tasks.TaskListContent.Contact;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Contact} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

//klasa odpowiada za zarządzanie wyświetlanymi treściami na ekranie
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Contact> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        //configureImageButton();
        return new ViewHolder(view);
    }

    //metoda odpowiada za umieszczenie odpowiednich danych w danym, jednym tasku
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Bind the ViewHolder to the element at position
        Contact contact = mValues.get(position);
        holder.mItem = contact;
        //Set the task title
        holder.mContentView.setText(contact.name + " " +contact.surname);
        //Set the task image
        final String picPath = contact.picPath;
        Context context = holder.mView.getContext(); //Retrieve the Context of the application
        if(picPath != null && !picPath.isEmpty()){
            //if picPath is set
            if(picPath.contains("drawable")){ //if picPath contain word "drqawable" get an appropirate drawale resource
                Drawable taskDrawable;
                switch(picPath){
                    case "drawable 1":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_1);
                        break;
                    case "drawable 2":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_2);
                        break;
                    case "drawable 3":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_3);
                        break;
                    case "drawable 4":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_4);
                        break;
                    case "drawable 5":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_5);
                        break;
                    case "drawable 6":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_6);
                        break;
                    case "drawable 7":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_7);
                        break;
                    case "drawable 8":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_8);
                        break;
                    case "drawable 9":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_9);
                        break;
                    case "drawable 10":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_10);
                        break;
                    case "drawable 11":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_11);
                        break;
                    case "drawable 13":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_13);
                        break;
                    case "drawable 14":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_14);
                        break;
                    case "drawable 15":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_15);
                        break;
                    case "drawable 16":
                        taskDrawable=context.getResources().getDrawable(R.drawable.avatar_16);
                        break;
                    default:
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_1);
                }
                holder.mItemImageView.setImageDrawable(taskDrawable);
            }
        }else{
            //if picpath is not set, use a default drawable
            holder.mItemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_1));
        }

        //metoda uruchamiająca metodę w MainActivity(za pośrednictwem mListener <- bo mListener w tej klasie "symbolizuje" poniekąd MainActivity)
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //<-- 'holder' - jest obiektem klasy ViewHolder która "trzyma" w sobie wszystkie wymagane dane o określonym Tasku (w tym cały widok - 'mView')
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClickInteraction(holder.mItem, position); //<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                }
            }
        });

        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentButtonClickInteraction(position);//<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                }
            }
        });

        //Set an onLongClickListener for the list element
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                mListener.onListFragmentLongClickInteraction(holder.mItem, position);//<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //ViewHolder zaawiera wszystkie elementy wyświetlane w każdym z TaskFragmentów
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mItemImageView;
        public final TextView mContentView;
        public Contact mItem;
        public final ImageButton mImageButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = (ImageView) view.findViewById(R.id.item_image);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageButton = (ImageButton) view.findViewById(R.id.bin_image_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }


    }
}

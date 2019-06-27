package com.example.news24;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.news24.MainActivity.MyPREFERENCES;

public class CommentDialog extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Bundle bundle = getArguments();
        final View v = inflater.inflate(R.layout.dialog_addcomment, null);

        ;

        final int articleId = bundle.getInt("articleID");

        builder.setView(v)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("FIRE ZE MISSILES!!!");
                        TextView content = v.findViewById(R.id.comTextContent);
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        String username = sharedPreferences.getString("username","");
                        DatabaseHelper db = new DatabaseHelper(getContext());

                        String pattern = "dd/MM/yyyy HH:mm";
                        DateFormat df = new SimpleDateFormat(pattern);
                        Date today = Calendar.getInstance().getTime();
                        String todayAsString = df.format(today);

                        Comment newComment = new Comment(1, content.getText().toString(), todayAsString, 0, 0, username, articleId);
                        db.addComment(newComment);

                        listener.onDialogPositiveClick(CommentDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("MISSION FAILED! WE'LL GET EM NEXT TIME!");
                        listener.onDialogNegativeClick(CommentDialog.this);
                    }
                });
        return builder.create();
    }
}

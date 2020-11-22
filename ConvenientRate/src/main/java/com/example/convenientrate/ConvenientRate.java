package com.example.convenientrate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class ConvenientRate {


    public interface CallBack_UserRating {
        void userRating(int rating,String msg);
    }

    private static int flagAlert_BTN_okClick=0;
    private static String SP_KEY_ALREADY_ASK="SP_KEY_ALREADY_ASK";
    private static int selectedStar = 1;
    private static String DEFAULT_TEXT_TITLE = "Rate Us";
    private static String DEFAULT_TEXT_CONTENT = "Tell others what you think about this app";
    private static String DEFAULT_TEXT_CONTINUE = "Continue";
    private static String DEFAULT_TEXT_GOOGLE_PLAY = "Please take a moment and rate us on Google Play";
    private static String DEFAULT_TEXT_CLICK_HERE = "click here";
    private static String DEFAULT_TEXT_THANKS = "Thanks for the feedback";


    public static void Rate(
            final Activity activity
            , final CallBack_UserRating callBack_userRating
    )
    {
        Rate(activity,
                ""
                , ""
                , ""
                , ""
                , ""
                , "",
                callBack_userRating);
    }

    private static boolean continueClicked = false;


    public static void Rate(
            final Activity activity
            , final String _title
            , final String _content
            , final String _continue_text
            , final String _googlePlay_text
            , final String _clickHere_text
            , final String _thanksForFeedback
            , final CallBack_UserRating callBack_userRating
    ) {

        if(getIfAlreadyAnswer(activity)==1){
            return;
        }
        final String title = (_title != null && !_title.equals("")) ? _title : DEFAULT_TEXT_TITLE;
        final String content = (_content != null && !_content.equals("")) ? _content : DEFAULT_TEXT_CONTENT;
        final String continue_text = (_continue_text != null && !_continue_text.equals("")) ? _continue_text : DEFAULT_TEXT_CONTINUE;
        final String googlePlay_text = (_googlePlay_text != null && !_googlePlay_text.equals("")) ? _googlePlay_text : DEFAULT_TEXT_GOOGLE_PLAY;
        final String clickHere_text = (_clickHere_text != null && !_clickHere_text.equals("")) ? _clickHere_text : DEFAULT_TEXT_CLICK_HERE;
        final String thanksForFeedback = (_thanksForFeedback != null && !_thanksForFeedback.equals("")) ? _thanksForFeedback : DEFAULT_TEXT_THANKS;

        continueClicked = false;


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        final Button alert_BTN_ok = dialogView.findViewById(R.id.alert_BTN_ok);
        final TextView alert_LBL_title = dialogView.findViewById(R.id.alert_LBL_title);
        final TextView alert_LBL_content = dialogView.findViewById(R.id.alert_LBL_content);
        final TextView alert_LBL_google = dialogView.findViewById(R.id.alert_LBL_google);
        final View alert_LAY_stars = dialogView.findViewById(R.id.alert_LAY_stars);
        final EditText editTextPleaseHelpUsBeBetter = dialogView.findViewById(R.id.editTextHelpUsBeBetter);
        final ImageView alert_IMG_google = dialogView.findViewById(R.id.alert_IMG_google);
        final ImageButton alert_BTN_star_1 = dialogView.findViewById(R.id.alert_BTN_star_1);
        final ImageButton alert_BTN_star_2 = dialogView.findViewById(R.id.alert_BTN_star_2);
        final ImageButton alert_BTN_star_3 = dialogView.findViewById(R.id.alert_BTN_star_3);
        final ImageButton alert_BTN_star_4 = dialogView.findViewById(R.id.alert_BTN_star_4);
        final ImageButton alert_BTN_star_5 = dialogView.findViewById(R.id.alert_BTN_star_5);
        final ImageButton[] stars = new ImageButton[]{alert_BTN_star_1, alert_BTN_star_2, alert_BTN_star_3, alert_BTN_star_4, alert_BTN_star_5};

        editTextPleaseHelpUsBeBetter.setVisibility(View.GONE);
        alert_LBL_google.setVisibility(View.GONE);
        alert_IMG_google.setVisibility(View.GONE);

        final int drawable_active = R.drawable.ic_star_active;
        final int drawable_deactive = R.drawable.ic_star_deactive;

        View.OnClickListener starsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedIndex = -1;
                for (int i = 0; i < stars.length; i++) {
                    stars[i].setImageResource(drawable_active);
                    if (stars[i].getId() == v.getId()) {
                        clickedIndex = i;
                        break;
                    }
                }
                for (int i = clickedIndex + 1; i < stars.length; i++) {
                    stars[i].setImageResource(drawable_deactive);
                }
                selectedStar = clickedIndex + 1;
                alert_BTN_ok.setText(selectedStar+"/5\n" + continue_text);

            }
        };
        for (ImageButton star : stars) {
            star.setOnClickListener(starsClickListener);
        }

        alert_LBL_title.setText(title);
        alert_LBL_content.setText(content);

        alert_BTN_ok.setText(continue_text);
        alert_BTN_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int _openStoreFrom_Stars = 4;
                if(flagAlert_BTN_okClick==1) {
                    if (selectedStar >= _openStoreFrom_Stars) {
                        launchMarket(activity);
                    }
                    if (callBack_userRating != null) {
                        callBack_userRating.userRating(selectedStar,editTextPleaseHelpUsBeBetter.getText().toString());
                    }
                    saveAlreadyAnswer(activity);
                    alertDialog.dismiss();
                }



                if(flagAlert_BTN_okClick==0) {

                    if (selectedStar >= _openStoreFrom_Stars) {
                        alert_LBL_content.setVisibility(View.GONE);
                        alert_LAY_stars.setVisibility(View.GONE);
                        alert_LBL_google.setVisibility(View.VISIBLE);
                        alert_IMG_google.setVisibility(View.VISIBLE);
                        alert_LBL_google.setText(googlePlay_text);
                        alert_BTN_ok.setText(clickHere_text);
                    } else {
                        alert_BTN_ok.setText("sent");
                        alert_LBL_title.setText("please help us to improve");
                        alert_LBL_content.setVisibility(View.GONE);
                        alert_LAY_stars.setVisibility(View.GONE);
                        editTextPleaseHelpUsBeBetter.setVisibility(View.VISIBLE);
                        Toast.makeText(activity, thanksForFeedback, Toast.LENGTH_SHORT).show();
                    }
                    flagAlert_BTN_okClick++;

                }

            }


        });

        alertDialog.show();

    }



    private static void launchMarket(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " unable to find google play app", Toast.LENGTH_LONG).show();
        }
    }

    private static long getIfAlreadyAnswer(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SP_LIBRARY_NAME", Context.MODE_PRIVATE);
        long val = sharedPreferences.getLong(SP_KEY_ALREADY_ASK, 0);
        return val;
    }

    private static void saveAlreadyAnswer(Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("SP_LIBRARY_NAME", Context.MODE_PRIVATE).edit();
        editor.putLong(SP_KEY_ALREADY_ASK, 1);
        editor.apply();
    }



}

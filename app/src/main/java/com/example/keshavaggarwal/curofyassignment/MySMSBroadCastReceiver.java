package com.example.keshavaggarwal.curofyassignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

/**
 * Created by KeshavAggarwal on 14/07/18.
 */

public class MySMSBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (Object aPdusObj : pdusObj) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    if (phoneNumber.contains("CUROFY")|| phoneNumber.contains("PLVMSG")){
                        //getting the otp code from the message
                        String message = currentMessage.getDisplayMessageBody().split(" ")[1].substring(0,4);
                        Intent myIntent = new Intent("otp");
                        myIntent.putExtra("message", message);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }

    }
}



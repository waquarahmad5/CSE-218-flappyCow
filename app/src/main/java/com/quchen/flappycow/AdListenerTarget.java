package com.quchen.flappycow;

import android.os.Message;
import com.google.android.gms.ads.AdListener;
import com.quchen.flappycow.Interfaces.MyAdListenerAdapter;

public class AdListenerTarget extends AdListener implements MyAdListenerAdapter {

    public void onAdClosed(MessageHandler msgHandler)
    {
        msgHandler.sendMessage(Message.obtain(msgHandler, MessageHandler.GAME_OVER_DIALOG));
    }
}

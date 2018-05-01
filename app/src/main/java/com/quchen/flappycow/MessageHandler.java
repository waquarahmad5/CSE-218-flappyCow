package com.quchen.flappycow;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


public class MessageHandler extends Handler {

        public static final int GAME_OVER_DIALOG = 0;
        public static final int SHOW_TOAST = 1;
        public static final int SHOW_AD = 2;

        private Game game;

        private GameView view;

        public MessageHandler(Game game, GameView view){
            this.game = game;
            this.view = view;
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case GAME_OVER_DIALOG:
                    showGameOverDialog();
                    break;
                case SHOW_TOAST:
                    Toast.makeText(game, msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_AD:
                    showAd();
                    break;
            }
        }

        private void showAd() {
            if(view.interstitial == null) {
                showGameOverDialog();
            } else {
                if(view.interstitial.isLoaded()) {
                    view.interstitial.show();
                } else {
                    showGameOverDialog();
                }
            }
        }

        private void showGameOverDialog() {
            int gc = game.getGameOverCounter();
            game.incrementGameCounter();
            assert gc + 1 == game.getGameOverCounter() : "Counter did not increase";
            game.gameOverDialog.init();
            game.gameOverDialog.show();
        }

}

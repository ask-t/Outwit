package edu.byuh.cis.cs203.preferences.activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import edu.byuh.cis.cs203.preferences.GameView;
import edu.byuh.cis.cs203.preferences.Prefs;
import edu.byuh.cis.cs203.preferences.R;

public class MainActivity extends Activity {

    private GameView gv;
    private MediaPlayer music;

    /**
     * The onCreate method is one of the first methods that gets called
     * when an Activity starts. We override this method to do any one-time
     * initialization stuff
     * @param b not used in this program. In general, the Bundle object is used
     *          to preserve data from one instance of the program to the next;
     *          for example, after a device rotation event.
     */
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        gv = new GameView(this);
        music = MediaPlayer.create(this, R.raw.back1);
        music.setLooping(true);
        music.setVolume(1,1);
        setContentView(gv);

    }

    /**
     * This method is called whenever the user presses the "back" button
     * on the device. We override it to display a dialog box asking the
     * user if they really want to quit the game.
     */

    @Override
    public void onPause(){
        super.onPause();
        gv.pauseGame();
        if (Prefs.getSoundPref(this)) {
            music.pause();
        }
    }

    /**
     * This method is called whenever the user presses the "back" button
     * on the device. We override it to display a dialog box asking the
     * user if they really want to quit the game.
     */
    @Override
    public void onResume(){
        super.onResume();
        gv.resumeGame();
        if (Prefs.getSoundPref(this)) {
            music.start();
        }
    }

    /**
     * This method is called whenever the user presses the "back" button
     * on the device. We override it to display a dialog box asking the
     * user if they really want to quit the game.
     */
    @Override
    public void onBackPressed(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Leaving so soon?")
                .setMessage("Do you REALLY want to quit? I mean, really?")
                .setPositiveButton("Yes, I'm done.", (p1,p2) -> finish())
                .setNegativeButton("No, I want to stay!", null)
                .setCancelable(true)
                .show();
    }

    /**
     * This method is called when the user presses the back button.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        music.release();
    }
}
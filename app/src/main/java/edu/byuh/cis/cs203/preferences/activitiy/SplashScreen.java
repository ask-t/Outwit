package edu.byuh.cis.cs203.preferences.activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import edu.byuh.cis.cs203.preferences.Prefs;
import edu.byuh.cis.cs203.preferences.R;
import edu.byuh.cis.cs203.preferences.activitiy.MainActivity;

public class SplashScreen extends Activity {
    private ImageView title;
    private ImageView play;
    private ImageView about;
    private ImageView settings;
    private boolean isPlay = false;

    /**
     *
     * @param b If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        title = new ImageView(this);
        title.setImageResource(R.drawable.play);
        title.setScaleType(ImageView.ScaleType.FIT_XY);
        play = new ImageView(this);
        play.setImageResource(R.drawable.play2);
        play.setScaleType(ImageView.ScaleType.FIT_XY);
        about = new ImageView(this);
        about.setImageResource(R.drawable.about);
        about.setScaleType(ImageView.ScaleType.FIT_XY);
        settings = new ImageView(this);
        settings.setImageResource(R.drawable.settings);
        settings.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(title);
    }

    /**
     * This method is called whenever the user touches the screen.
     * @param m The touch screen event being processed.
     *
     * @return True if the event was handled, false otherwise.
     */

    @Override
    public boolean onTouchEvent(MotionEvent m)
    {
        if (m.getAction() == MotionEvent.ACTION_DOWN)
        {
            float w = title.getWidth();
            float h = title.getHeight();
            float x = m.getX();
            float y = m.getY();
            if (y > h / 2*1.5) // if people touch the bottom half of the screen, it will start the game
            {
                setContentView(play);
                isPlay = true;
            }
            else if (y < h/10*2 && x < w/10*2) // if people touch the top left corner, it will show the about dialog
            {
                setContentView(about);
                showAboutDialog();
                return true;
            }
            else if (y < h/10*2 && x > w/10*8) // if people touch the top right corner, it will show the settings
            {
                setContentView(settings);
                Intent pref = new Intent(this, Prefs.class);
                startActivity(pref);
            //TODO launch preferences/settings
            }
        }
        else if(m.getAction() == MotionEvent.ACTION_UP) // if people release the screen, it will go back to the title screen, and start the game
        {
            setContentView(title);
            if(isPlay){
                Intent start = new Intent(this, MainActivity.class);
                startActivity(start);
                isPlay = false;
            }
        }
        return true;
    }

    /**
     * This method is called when the user presses the back button.
     */
    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Outwit is a strategy game played on a grid. Each player has an equal number of pieces, with the goal to trap the opponent's pieces. \n\n" +
                            "Rules:\n" +
                            "- Two players take turns to move.\n" +
                            "- Each piece can move in straight lines until blocked.\n" +
                            "- Win by trapping the opponent's pieces.\n\n" +
                            "Strategy:\n" +
                            "- Anticipate moves and control the board's center.\n" +
                            "- Sometimes, sacrifice a piece to gain an advantage.\n\n" +
                            "The game is won when a player can no longer make a move, or it can end in a draw if all pieces are blocked.\n\n"+
                            "Publisher: Parker Brothers in 1978\n" )
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }
}

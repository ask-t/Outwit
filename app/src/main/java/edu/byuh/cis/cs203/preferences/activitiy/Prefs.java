package edu.byuh.cis.cs203.preferences.activitiy;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.*;
import edu.byuh.cis.cs203.preferences.R;


/**
 * In object-oriented programming (OOP), a "façade" is a design pattern that provides a simplified interface to a complex subsystem.
 * Imagine a complex system with many moving parts and complicated procedures needed to perform certain tasks.
 * A façade takes all that complexity and hides it behind a simple, easy-to-use interface.
 */

public class Prefs extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.byuh.cis.cs203.preferences.R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(edu.byuh.cis.cs203.preferences.R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed,
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate back to the parent activity
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *For background music. Play or not play
     * @param c The context of the application
     * @return The boolean value of the music preference
     *
     */

    public static boolean getSoundPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("MUSIC_PREFERENCE_OPTION", false);
    }

    /**
     *For effect sound. Play or not play
     * @param c The context of the application
     * @return The boolean value of the sound effect preference
     *
     */
    public static boolean getSoundEffectPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("SOUND_EFFECT_PREFERENCE_OPTION", false);
    }

    /**
     * For animation. Play or not play
     * @param c The context of the application
     * @return The boolean value of the animation preference
     *
     */

    public static boolean getAnimationPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("ANIMATION_PREFERENCE_OPTION", false);
    }

    /**
     * For timer. Play or not play. Not used in this version
     * @param c
     * @return
     */

    public static boolean getTimerPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("TIMER_PREFERENCE_OPTION", false);
    }

    /**
     * For the first player. Dark or light or random
     * @param c The context of the application
     * @return The double value of the face size preference
     *
     */

    public static String getFirstPlayerPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("FIRST_PLAYER_OPTION", "random");
    }

    /**
     * For the animation speed. Slow, medium or fast
     * @param c The context of the application
     * @return The double value of the face size preference
     *
     */

    public static String getAnimationSpeedPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("ANIMATION_SPEED_OPTION", "medium");
    }

    /**
     * for the theme color. Classic or pop
     * @param c The context of the application
     * @return The double value of the face size preference
     *
     */
    public static String getThemePref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("THEME_OPTION", "classic");
    }

    /**
     * For the layout. Classic or modern. Not used in this version
     * @param c The context of the application
     * @return The double value of the face size preference
     *
     */
    public static String getLayoutPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("LAYOUT_OPTION", "classic");
    }

    public static String getModePref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("MODE_OPTION", "human");
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {

        /**
         *
         * @param b
         * @param s
         *
         */
        @Override
        public void onCreatePreferences(Bundle b, String s) {
            Context context = getPreferenceManager().getContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            //add preference widgets here

            // For background music. Play or not play
            SwitchPreference music = new SwitchPreference(context);
            music.setTitle(getString(edu.byuh.cis.cs203.preferences.R.string.music_pref_title));
            music.setSummaryOn(getString(edu.byuh.cis.cs203.preferences.R.string.music_pref_summary_on));
            music.setSummaryOff(getString(edu.byuh.cis.cs203.preferences.R.string.music_pref_summary_off));
            music.setKey("MUSIC_PREFERENCE_OPTION");
            music.setDefaultValue(false);
            screen.addPreference(music);

            // For effect sound. Play or not play
            SwitchPreference soundEffect = new SwitchPreference(context);
            soundEffect.setTitle(getString((edu.byuh.cis.cs203.preferences.R.string.sound_pref_title)));
            soundEffect.setSummaryOn(getString(edu.byuh.cis.cs203.preferences.R.string.sound_pref_summary_on));
            soundEffect.setSummaryOff(getString(edu.byuh.cis.cs203.preferences.R.string.sound_pref_summary_off));
            soundEffect.setKey("SOUND_EFFECT_PREFERENCE_OPTION");
            soundEffect.setDefaultValue(false);
            screen.addPreference(soundEffect);

            // For animation. Play or not play
            SwitchPreference Animation = new SwitchPreference(context);
            Animation.setTitle(getString(edu.byuh.cis.cs203.preferences.R.string.anime_pref_title));
            Animation.setSummaryOn(getString(edu.byuh.cis.cs203.preferences.R.string.anime_pref_summary_on));
            Animation.setSummaryOff(getString(edu.byuh.cis.cs203.preferences.R.string.anime_pref_summary_off));
            Animation.setKey("ANIMATION_PREFERENCE_OPTION");
            Animation.setDefaultValue(false);
            screen.addPreference(Animation);

            // For timer. Play or not play. Not used in this version
//            SwitchPreference timer = new SwitchPreference(context);
//            timer.setTitle("Play timer");
//            timer.setSummaryOn("Timer will play");
//            timer.setSummaryOff("Timer will not play");
//            timer.setKey("TIMER_PREFERENCE_OPTION");
//            timer.setDefaultValue(false);
//            screen.addPreference(timer);

            // Mode
            ListPreference mode = new ListPreference(context);
            mode.setTitle(getString(edu.byuh.cis.cs203.preferences.R.string.mode_pref_title));
            mode.setSummary(getString(edu.byuh.cis.cs203.preferences.R.string.mode_pref_Summary));
            mode.setKey("MODE_OPTION");
            mode.setEntries(edu.byuh.cis.cs203.preferences.R.array.mode_pref_setEntities);
            mode.setEntryValues(edu.byuh.cis.cs203.preferences.R.array.mode_pref_setEntryValues);
            mode.setDefaultValue("human");
            screen.addPreference(mode);

            // For the first player. Dark or light or random
            ListPreference firstPlayer = new ListPreference(context);
            firstPlayer.setTitle(getString(edu.byuh.cis.cs203.preferences.R.string.first_pref_title));
            firstPlayer.setSummary(getString(edu.byuh.cis.cs203.preferences.R.string.first_pref_summary));
            firstPlayer.setKey("FIRST_PLAYER_OPTION");
            firstPlayer.setEntries(edu.byuh.cis.cs203.preferences.R.array.first_pref_setEntities);
            firstPlayer.setEntryValues(edu.byuh.cis.cs203.preferences.R.array.first_pref_setEntities);
            firstPlayer.setDefaultValue("random");
            screen.addPreference(firstPlayer);

            // For the animation speed. Slow, medium or fast
            ListPreference animationSpeed = new ListPreference(context);
            animationSpeed.setTitle(getString(edu.byuh.cis.cs203.preferences.R.string.speed_pref_title));
            animationSpeed.setSummary(getString(edu.byuh.cis.cs203.preferences.R.string.speed_pref_summary));
            animationSpeed.setKey("ANIMATION_SPEED_OPTION");
            animationSpeed.setEntries(edu.byuh.cis.cs203.preferences.R.array.speed_pref_setEntities);
            animationSpeed.setEntryValues(edu.byuh.cis.cs203.preferences.R.array.speed_pref_setEntryValues);
            animationSpeed.setDefaultValue("medium");
            screen.addPreference(animationSpeed);

            // for the theme color. Classic or pop
            ListPreference themeColor = new ListPreference(context);
            themeColor.setTitle(edu.byuh.cis.cs203.preferences.R.string.theme_pref_title);
            themeColor.setSummary(edu.byuh.cis.cs203.preferences.R.string.theme_pref_summary);
            themeColor.setKey("THEME_OPTION");
            themeColor.setEntries(edu.byuh.cis.cs203.preferences.R.array.theme_pref_setEntities);
            themeColor.setEntryValues(R.array.theme_pref_setEntryValues);
            themeColor.setDefaultValue("classic");
            screen.addPreference(themeColor);

            // For the layout. Classic or modern. Not used in this version
//            ListPreference layout = new ListPcreference(context);
//            layout.setTitle("Layout");
//            layout.setSummary("Choose the layout");
//            layout.setKey("LAYOUT_OPTION");
//            layout.setEntries(new String[]{"classic", "modern"});
//            layout.setEntryValues(new String[]{"classic", "modern"});
//            layout.setDefaultValue("classic");
//            screen.addPreference(layout);

            setPreferenceScreen(screen);


        }
    }
}
package edu.byuh.cis.cs203.preferences.theme;

import android.graphics.Color;
import edu.byuh.cis.cs203.preferences.theme.Theme;

public class PopTheme implements Theme {
    @Override
    public int getLightChip() {
        return Color.rgb(247, 199, 255);
    }

    @Override
    public int getDarkChip() {
        return Color.rgb(130, 199, 255);
    }

    @Override
    public int getLigthCell() {
        return Color.rgb(255, 92, 92);
    }

    @Override
    public int getDarkCell() {
        return Color.rgb(32, 129, 247);
    }

    @Override
    public int getNeutralCell() {
        return Color.rgb(255, 231, 135);
    }

    @Override
    public int getGOLD_LEAF() {
        return Color.rgb(202,192,6);
    }

    /**
     * @return
     */
    @Override
    public int getLightChipPicture() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public int getDarkChipPicture() {
        return 0;
    }

    /**
     * This method returns the name of the light team
     *
     * @return the name of the light team
     */
    @Override
    public String getLightTeamName() {
        return "pink";
    }

    /**
     * This method returns the name of the dark team
     *
     * @return the name of the dark team
     */
    @Override
    public String getDarkTeamName() {
        return "Blue";
    }
}

package edu.byuh.cis.cs203.preferences.theme;

import android.graphics.Color;
import edu.byuh.cis.cs203.preferences.theme.Theme;

public class ClassicTheme implements Theme {


    /**
     * This method returns the color of the light chip  in the classic theme
     * @return  the color of the light chip
     */
    @Override
    public int getLightChip() {
        return Color.rgb(250,233, 188);
    }
    /**
     * This method returns the color of the dark chip  in the classic theme
     * @return  the color of the dark chip
     */
    @Override
    public int getDarkChip() {
        return Color.rgb(98,78, 26);
    }

    /**
     * This method returns the color of the light cell  in the classic theme
     * @return  the color of the light cell
     */
    @Override
    public int getLigthCell() {
        return Color.rgb(217, 198, 149);
    }

    /**
     * This method returns the color of the dark cell  in the classic theme
     * @return  the color of the dark cell
     */
    @Override
    public int getDarkCell() {
        return Color.rgb(133, 98, 6);
    }

    /**
     * This method returns the color of the neutral cell  in the classic theme
     * @return  the color of the neutral cell
     */
    @Override
    public int getNeutralCell() {
        return Color.rgb(231,175,28);
    }

    /**
     * This method returns the color of the gold leaf  in the classic theme
     * @return  the color of the gold leaf
     */
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
        return "Light Brown";
    }

    /**
     * This method returns the name of the dark team
     *
     * @return the name of the dark team
     */
    @Override
    public String getDarkTeamName() {
        return "Dark Brown";
    }
}

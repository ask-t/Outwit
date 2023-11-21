package edu.byuh.cis.cs203.preferences.theme;

import android.graphics.Color;
import edu.byuh.cis.cs203.preferences.R;

public class ThemeBit implements Theme{

    /**
     * This method returns the color of the light chip  in the classic theme
     * @return  the color of the light chip
     */
    @Override
    public int getLightChip() {
        return 0;
    }
    /**
     * This method returns the color of the dark chip  in the classic theme
     * @return  the color of the dark chip
     */
    @Override
    public int getDarkChip() {
        return 0;
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
        return Color.rgb(192,192,192);
    }

    /**
     * This method returns the color of the neutral cell  in the classic theme
     * @return  the color of the neutral cell
     */
    @Override
    public int getNeutralCell() {
        return Color.rgb(250,235,215);
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
     *if you want to use bitmap instead of color
     * @return the picture of the light chip
     */
    @Override
    public int getLightChipPicture() {
        return R.drawable.light2;
    }

    /**
     * @return the picture of the dark chip
     */
    @Override
    public int getDarkChipPicture() {
        return R.drawable.dark2;
    }

    /**
     * This method returns the name of the light team
     *
     * @return the name of the light team
     */
    @Override
    public String getLightTeamName() {
        return "White Magician";
    }

    /**
     * This method returns the name of the dark team
     *
     * @return the name of the dark team
     */
    @Override
    public String getDarkTeamName() {
        return "Black Magician";
    }
}

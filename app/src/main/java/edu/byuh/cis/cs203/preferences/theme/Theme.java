package edu.byuh.cis.cs203.preferences.theme;

import android.graphics.Paint;
import android.media.MediaPlayer;

public interface Theme {

    /**
     * This method returns the color of the light chip  in the classic theme
     * @return  the color of the light chip
     */

    int  getLightChip();
    /**
     * This method returns the color of the dark chip  in the classic theme
     * @return  the color of the dark chip
     */
    int getDarkChip();
    /**
     * This method returns the color of the light cell  in the classic theme
     * @return  the color of the light cell
     */
    int getLigthCell();
    /**
     * This method returns the color of the dark cell  in the classic theme
     * @return  the color of the dark cell
     */
    int getDarkCell();
    /**
     * This method returns the color of the neutral cell  in the classic theme
     * @return  the color of the neutral cell
     */
    int getNeutralCell();
    /**
     * This method returns the color of the gold leaf  in the classic theme
     * @return  the color of the gold leaf
     */
    int getGOLD_LEAF();
    /**
     *if you want to use bitmap instead of color
     * @return the picture of the light chip
     */
    int getLightChipPicture();
    /**
     *if you want to use bitmap instead of color
     * @return the picture of the dark chip
     */
    int getDarkChipPicture();

    /**
     * This method returns the name of the light team
     * @return  the name of the light team
     */
    String getLightTeamName();

    /**
     * This method returns the name of the dark team
     * @return  the name of the dark team
     */
    String getDarkTeamName();



}

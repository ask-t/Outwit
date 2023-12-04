package edu.byuh.cis.cs203.preferences.theme;

import android.graphics.Color;

public class Theme3 implements Theme {

    /**
     * @return
     */
    @Override
    public int getLightChip() {
        return Color.rgb(0, 206, 209);
    }

    /**
     * @return
     */
    @Override
    public int getDarkChip() {
        return Color.rgb(0, 128, 128);
    }

    /**
     * @return
     */
    @Override
    public int getLigthCell() {
        return Color.rgb(135, 206, 250);
    }

    /**
     * @return
     */
    @Override
    public int getDarkCell() {
        return Color.rgb(25, 25, 112);
    }

    /**
     * @return
     */
    @Override
    public int getNeutralCell() {
        return Color.rgb(186, 85, 211);
    }

    /**
     * @return
     */
    @Override
    public int getGOLD_LEAF() {
        return Color.rgb(218, 112, 214);
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
}


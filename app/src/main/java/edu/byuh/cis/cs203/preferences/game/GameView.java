package edu.byuh.cis.cs203.preferences.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import edu.byuh.cis.cs203.preferences.activitiy.Prefs;
import edu.byuh.cis.cs203.preferences.R;
import edu.byuh.cis.cs203.preferences.theme.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;



public class GameView extends View {

    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;
    private Paint blackLine;
    private Paint rojo;
    private Paint text;
    private Cell[][] cellz;
    private List<Chip> chipz;
    private List<Cell> legalMoves;
    private Chip selected;
    private boolean initialized = false;
    private Timer tim;
    private Bitmap undo;
    private Bitmap emoji1;
    private Bitmap emoji2;
    private Bitmap emoji3;
    private int emojiCount;
    private Stack<Move> undoStack;
    private Cell undoCell;
    private int currentPlayer;
    private AlertDialog dialog;
    private boolean isGameOver;
    private MediaPlayer effect1;
    private boolean isEffectOn;
    private boolean isAnimeOn;
    private Consumer<Chip> anime = c -> c.animate(isAnimeOn);
    private String firstPlayer;
    private float animeSpeed;
    private String lightTeamName;
    private String darkTeamName;
    private int counter;
    private class Timer extends Handler {
        private boolean paused;

        /**
         * Start the timer
         */
        public void resume() {
            paused = false;
            sendMessageDelayed(obtainMessage(), 10);
        }

        /**
         * pause the timer
         */
        public void pause() {
            paused = true;
            removeCallbacksAndMessages(null);
        }

        /**
         * Instantiate the timer and start it running
         */
        public Timer() {
            resume();
        }

        /**
         * The most important method in the Timer class.
         * Here, we put all the code that needs to happen at each clock-tick
         * @param m the Message object (unused)
         */
        @Override
        public void handleMessage(Message m) {
//            if (selected != null) {
//                selected.animate();
//            }
//            for (Chip c : chipz) {
//                c.animate();
//            }
            chipz.forEach(anime);

            invalidate();
            if (!paused) {
                sendMessageDelayed(obtainMessage(), 10);
            }
            if(!isGameOver){
                checkForWinner();
            }
        }
    }
    public static Theme theme;
    private boolean isAIOn;
    public static Team aiTeam;


    /**
     * Our constructor. This is where we initialize our fields.
     * @param c Context is the superclass of Activity. Thus, this
     *          parameter is basically a polymorphic reference to
     *          whatever Activity created this View... in this case,
     *          it's our MainActivity.
     */
    public GameView(Context c) {
        super(c);
         switch(Prefs.getThemePref(c)) {
            case "classic" -> { theme = new ClassicTheme();
                lightTeamName = getResources().getString(R.string.theme_classic_light);
                darkTeamName =getResources().getString(R.string.theme_classic_dark);}
            case "pop" -> { theme = new PopTheme();
                lightTeamName = getResources().getString(R.string.theme_pop_light);
                darkTeamName =getResources().getString(R.string.theme_pop_dark);}
            case "ice" -> {theme = new Theme3();
                lightTeamName = getResources().getString(R.string.theme_ice_light);
                darkTeamName =getResources().getString(R.string.theme_ice_dark);}
            case "bit" -> { theme = new ThemeBit();
                lightTeamName = getResources().getString(R.string.theme_magic_light);
                darkTeamName =getResources().getString(R.string.theme_magic_dark);}
            default -> {theme = new ClassicTheme();
                lightTeamName = getResources().getString(R.string.theme_classic_light);
                darkTeamName =getResources().getString(R.string.theme_classic_dark);}
        };
        switch(Prefs.getModePref(c)){
            case "human" ->{
                isAIOn = false;
            }
            case "darkAI" ->{
                isAIOn = true;
                aiTeam = Team.DARK;
            }
            case "lightAI" ->{
                isAIOn = true;
                aiTeam = Team.LIGHT;
            }

        }
//        lightTeamName = theme.getLightTeamName();
//        darkTeamName = theme.getDarkTeamName();
        lightCell = new Paint();
        lightCell.setColor(theme.getLigthCell());
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint(lightCell);
        darkCell.setColor(theme.getDarkCell());
        neutralColor = new Paint(lightCell);
        neutralColor.setColor(theme.getNeutralCell());
        blackLine = new Paint();
        blackLine.setColor(Color.BLACK);
        blackLine.setStyle(Paint.Style.STROKE);
        rojo = new Paint();
        rojo.setColor(Color.RED);
        rojo.setStyle(Paint.Style.FILL);
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(50);
        cellz = new Cell[9][10];
        chipz = new ArrayList<>();
        legalMoves = new ArrayList<>();
        selected = null;
        isGameOver = false;
        undo = BitmapFactory.decodeResource(getResources(), R.drawable.undo);
        emoji1 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji1);
        emoji2 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji2);
        emoji3 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji3);
        emojiCount = 1;
        undoStack = new Stack<>();


        dialog = new AlertDialog.Builder(c)
                .setTitle(R.string.alter_title)
                .setPositiveButton(R.string.alter_positive, (DialogInterface di, int i) -> initialized = false )
                .setNegativeButton(R.string.alter_negative, (DialogInterface di, int i) -> System.exit(0)).create();
        effect1 = MediaPlayer.create(c, R.raw.effect1);
        isEffectOn = Prefs.getSoundEffectPref(c);
        isAnimeOn = Prefs.getAnimationPref(c);
        firstPlayer = Prefs.getFirstPlayerPref(c);
        if(firstPlayer.equals("dark")){
            currentPlayer = 0;
        }
        else if(firstPlayer.equals("light")){
            currentPlayer = 1;

        }
        else if(firstPlayer.equals("random")){
            currentPlayer = (int)(Math.random() * 2) ;
        }
        else{
            Log.d("firstPlayer", "error", null);
        }
        Log.d("firstPlayer", firstPlayer, null);
        animeSpeed = Float.parseFloat(Prefs.getAnimationSpeedPref(c));
    }

    /**
     * onDraw is roughly equivalent to the paintComponent method in the
     * JPanel class from the standard Java API. Override it to perform
     * custom drawing for the user interface
     * @param c the Canvas object, which contains the methods we need for
     *          drawing basic shapes. Similar to the Graphics class in
     *          the standard Java API.
     */
    @Override
    public void onDraw(Canvas c) {
        final float w = c.getWidth();
        final float h = c.getHeight();
        final float cellSize = w/9f;
        if (! initialized) {
            isGameOver = false;
            chipz.clear();
            legalMoves.clear();
            undoStack.clear();
            blackLine.setStrokeWidth(cellSize * 0.03f);

            //create all the cells
            for (int i=0; i<10; i++) {
                for (int j=0; j<9; j++) {
                    Team color = Team.NEUTRAL;
                    if (i<3 && j>5) {
                        color = Team.LIGHT;
                    } else if (i>6 && j<3) {
                        color = Team.DARK;
                    }
                    cellz[j][i] = new Cell(j,i,color,cellSize);
                }
            }

            //create all the chips
            for (int i=0; i<9; i++) {
                Chip dark = null;
                Chip light = null;
                //power chips are at column #4;
                if (i==4) {
                    dark = Chip.power(Team.DARK);
                    light = Chip.power(Team.LIGHT);
                //everywhere else it's normal chips.
                } else {
                    dark = Chip.normal(Team.DARK);
                    light = Chip.normal(Team.LIGHT);
                }
                dark.setCell(cellz[i][i]);
                light.setCell(cellz[i][i+1]);
                chipz.add(dark);
                chipz.add(light);
            }
            tim = new Timer();
            initialized = true;
        }

        //draw the orange background
        c.drawRect(0,0,cellSize*9,cellSize*10,neutralColor);

        //draw the light brown corner
        c.drawRect(cellSize*6,0, cellSize*10, cellSize*3, lightCell);

        //draw the dark brown color
        c.drawRect(0, cellSize*7, cellSize*3, cellSize*10, darkCell);

        //draw a nice solid border around the whole thing
        c.drawRect(0,0,cellSize*9,cellSize*10, blackLine);

        //draw horizontal black lines
        for (int i=1; i<=9; i++) {
            c.drawLine(0, i*cellSize, cellSize*9, cellSize*i, blackLine);
        }
        //draw vertical black lines
        for (int i=1; i<=8; i++) {
            c.drawLine(i*cellSize, 0, i*cellSize, cellSize*10, blackLine);
        }

        //draw the chips
        for (Chip ch : chipz) {
            ch.draw(c, blackLine,getResources());
        }

        //draw the highlights
        for (Cell lm : legalMoves) {
            lm.drawHighlight(c, rojo);
        }

        undo = Bitmap.createScaledBitmap(undo, (int)cellSize, (int)cellSize, true); //use cellSize to scale the bitmap
        emoji1 = Bitmap.createScaledBitmap(emoji1, (int)cellSize, (int)cellSize, true);
        emoji2 = Bitmap.createScaledBitmap(emoji2, (int)cellSize, (int)cellSize, true);
        emoji3 = Bitmap.createScaledBitmap(emoji3, (int)cellSize, (int)cellSize, true);
        undoCell = new Cell(8, 12, cellSize);
        c.drawRect(undoCell.bounds(), blackLine);
        if(emojiCount == 1){
            c.drawBitmap(emoji1, undoCell.bounds().left, undoCell.bounds().top, null);
        } else if(emojiCount == 2){
            c.drawBitmap(emoji2, undoCell.bounds().left, undoCell.bounds().top, null);
        }
        else if(emojiCount == 3){
            c.drawBitmap(emoji3, undoCell.bounds().left, undoCell.bounds().top, null);
        } else{
            c.drawBitmap(undo, undoCell.bounds().left, undoCell.bounds().top, null);
        }


        if(currentPlayer%2 == 0){
            c.drawText(darkTeamName+" "+getResources().getString(R.string.turn_message), cellSize*3, cellSize*10.5f, text);

        }
        else{
            c.drawText(lightTeamName+" "+getResources().getString(R.string.turn_message), cellSize*3, cellSize*10.5f, text);
        }

        if(isAIOn){
            if(currentPlayer == 0 && aiTeam == Team.DARK){
                aiPlay();
            }
            else if(currentPlayer == 1 && aiTeam == Team.LIGHT){
                aiPlay();
            }
        }


    }

    /**
     * This method gets called anytime the user touches the screen
     * @param m the object that holds information about the touch event
     * @return true (to prevent the touch event from getting passed to other objects. Please refer to the Chain of Responsibility design pattern.
     */
    @Override
    public boolean onTouchEvent(MotionEvent m) {
        //Log.d("CS203","inside GameView::onTouchEvent");
        float x = m.getX();
        float y = m.getY();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            //ignore touch events while a chip is moving
            if (anyMovingChips()) {
                return true;
            }

            for (Cell cell : legalMoves) {
                if (cell.contains(x,y)) {
                    selected.setDestination(cell,animeSpeed);
                    Move move = new Move(selected.getHostCell(), cell); // Assuming tappedCell is the cell the user tapped on.
                    if (isEffectOn){
                        effect1.start();
                    }
                    undoStack.push(move);
                    selected = null;
                    currentPlayer ++; // Move to next player
                    if(isAIOn){
                        if(aiTeam == Team.DARK && currentPlayer %2 == 0){
                            aiPlay();
                        }
                        else if(aiTeam == Team.LIGHT && currentPlayer %2 == 1){
                            aiPlay();
                        }
                    }
                    break;
                }
            }

            //first, clear old selections
            for (Chip chippy : chipz) {
                chippy.unselect();
            }
            legalMoves.clear();

            //now, check which chip got tapped
            for (Chip chippy : chipz) {
                if (chippy.contains(x, y)) {
                    //if user taps the selected chip, unselect it
                    if (selected == chippy) {
                        selected.unselect();
                        selected = null;
                        legalMoves.clear();
                        break;
                    }
                    else {
                        selected = chippy;
                        chippy.select();
                        if((currentPlayer %2 == 0 && selected.getColor() == Team.DARK) || (currentPlayer %2 == 1 && selected.getColor() == Team.LIGHT)) {
                            findPossibleMoves(null);
                        }
                        break;
                    }
                }
            }
            if(undoCell.bounds().contains(x,y)){ //if the user taps the undo button
                emojiCount++;
                if(emojiCount > 3){
                    emojiCount = 1;
                }
                if(isAIOn){
                    undoLastMove();
                    undoLastMove();
                    currentPlayer -= 2;     //undo twice if AI is on
                    return true;
                }
                else {
                    if(!undoStack.isEmpty()){
                        undoLastMove();
                        currentPlayer--;
                    }
                    else{
                        Toast.makeText(getContext(), getResources().getString(R.string.undo_empty), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            invalidate();
        }

        return true;
    }


    /**
     * This method is for the AI to play
     * @return void
     */
    private void aiPlay() {
        legalMoves.clear();
        List<Chip> movableChips = new ArrayList<>();
        for (Chip chip : chipz) {
            if ((aiTeam == Team.DARK && chip.getColor() == Team.DARK) ||
                    (aiTeam == Team.LIGHT && chip.getColor() == Team.LIGHT)) {
                movableChips.add(chip);
            }
        }

        if (!movableChips.isEmpty()) {
            boolean isMoved = false;
            for (Chip chip : movableChips) {
                selected = chip; // Select the chip to find its legal moves
                findPossibleMoves(chip.getHostCell()); // Find legal moves for the selected chip
                for (Cell cell : legalMoves) {
                    if (isBetterMove(cell) && !chip.isHome()) {
                        makeMove(chip, cell);
                        isMoved = true;
                        break; // Break out of the loop once a move is made
                    }
                }
                if (isMoved) {
                    legalMoves.clear();
                    break; // Break out of the loop if a move has been made
                }
            }

            // If no "better" move is found, make a random move
            if (!isMoved) {
                Chip chipToMove = movableChips.get((int) (Math.random() * movableChips.size()));
                selected = chipToMove;
                findPossibleMoves(chipToMove.getHostCell());
                if (!legalMoves.isEmpty()) {
                    Cell destination = legalMoves.get((int) (Math.random() * legalMoves.size()));
                    makeMove(chipToMove, destination);
                    legalMoves.clear();
                }
            }
        }
    }

    /**
     * move the chip method
     * @return void
     */
    private void makeMove(Chip chip, Cell destination) {
        chip.setDestination(destination, animeSpeed);
        Move move = new Move(chip.getHostCell(), destination);
        undoStack.push(move);
        if (isEffectOn) {
            effect1.start();
        }
        selected = null;
        currentPlayer++; // Move to next player
        invalidate();
    }

    /**
     * Check if the regal position is belong better way.
     * @return boolean
     */
    private boolean isBetterMove(Cell cell) {
        if(aiTeam == Team.LIGHT){
            if(5 < cell.x() && 3 < cell.y()){
                return true;
            }
        }
        else if (aiTeam == Team.DARK){
            if(cell.x() < 3 && 6 < cell.y()){
                return true;
            }
        }
        return false;
    }



    //this is a private method, so no Javadoc needed.
    private void findPossibleMoves(Cell c) {
        legalMoves.clear();
        int newX, newY;
        final Cell currentCell;
        if(c == null) {
            currentCell = selected.getHostCell();
        }
        else{
            currentCell = c;
        }
        if (selected.isPowerChip()) {
            //can we go right?
            for (newX = currentCell.x()+1; newX < 9; newX++) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go left?
            for (newX = currentCell.x()-1; newX >= 0; newX--) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go up?
            for (newY = currentCell.y()-1; newY >= 0; newY--) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go down?
            for (newY = currentCell.y()+1; newY < 10; newY++) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go up/right diagonal?
            newX = currentCell.x()+1;
            newY = currentCell.y()-1;
            while (newX < 9 && newY >= 0) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                    newX++;
                    newY--;
                } else {
                    break;
                }
            }
            //can we go up/left diagonal?
            newX = currentCell.x()-1;
            newY = currentCell.y()-1;
            while (newX >= 0 && newY >= 0) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                    newX--;
                    newY--;
                } else {
                    break;
                }
            }
            //can we go down/right diagonal?
            newX = currentCell.x()+1;
            newY = currentCell.y()+1;
            while (newX < 9 && newY < 10) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                    newX++;
                    newY++;
                } else {
                    break;
                }
            }
            //can we go down/left diagonal?
            newX = currentCell.x()-1;
            newY = currentCell.y()+1;
            while (newX >= 0 && newY < 10) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(selected)) {
                    legalMoves.add(candidate);
                    newX--;
                    newY++;
                } else {
                    break;
                }
            }

            //REGULAR CHIPS (not power chips)
        } else {
            //can we go right?
            Cell vettedCandidate = null;
            for (newX = currentCell.x()+1; newX < 9; newX++) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(selected)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }
            //can we go left?
            vettedCandidate = null;
            for (newX = currentCell.x()-1; newX >= 0; newX--) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(selected)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }

            //can we go up?
            vettedCandidate = null;
            for (newY = currentCell.y()-1; newY >= 0; newY--) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(selected)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }

            //can we go down?
            vettedCandidate = null;
            for (newY = currentCell.y()+1; newY < 10; newY++) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(selected)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }
        }
    }

    //true if a chip is currently moving; false otherwise.
    private boolean anyMovingChips() {
        boolean result = false;
        for (Chip c : chipz) {
            if (c.isMoving()) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * The function is for undoing the last move
     * @return void
     */
    private void undoLastMove() {
        // Unselect the currently selected chip
        if (selected != null) {
            selected.unselect();
            selected = null;
        }

        // Clear the list of legal moves
        legalMoves.clear();

        // Check if undo stack is empty
        if (undoStack.empty()) {
            Toast.makeText(getContext(), R.string.undo_empty, Toast.LENGTH_SHORT).show();

            return;
        }

        // Pop the last move
        Move lastMove = undoStack.pop();
        Cell source = lastMove.getSource();
        Cell destination = lastMove.getDestination();

        // Find the chip at the destination and move it to the source
        for (Chip chip : chipz) {
            if (chip.getHostCell() == destination) {
                chip.setCell(source);
                break;
            }
        }
//        currentPlayer --;
        invalidate(); // Redraw the view
    }


    /**
     * This method checks for a winner
     * @return void
     */
    private void checkForWinner(){
        int darkCount = 0; // counter how many chips are in the dark brown corner
        int lightCount = 0; // counter how many chips are in the light brown corner

        for(Chip chip : chipz){
            if(chip.isHome() &&chip.getColor() == Team.DARK){ // if the chip is in the dark brown corner
                darkCount++;
                Log.d("Chip Home", "Dark chip in. Now Dark chip is "+ darkCount);
            }
            else if(chip.isHome() &&chip.getColor() == Team.LIGHT){ // if the chip is in the light brown corner
                lightCount++;
                Log.d("Chip Home", "Light chip in. Now Light chip is "+ lightCount);
            }
        }
        if(darkCount == 9){ // if all the dark chips are in the dark brown corner
            Log.d("winner", "Dark Brown Wins!");
            dialog.setMessage(darkTeamName+" "+getResources().getString(R.string.win_message));
            dialog.show();
            isGameOver = true; // game is over
        }
        else if(lightCount == 9){ //if all the light chips are in the light brown corner
            Log.d("winner", "Light Brown Wins!");
            dialog.setMessage(lightTeamName+" "+getResources().getString(R.string.win_message));
            dialog.show();
            isGameOver = true;

        }
        invalidate();
    }

    /**
     * This method is called when the user presses the back button.
     */
    public void pauseGame(){
        if(tim != null){
            tim.pause();
        }
    }

    /**
     * This method is called when the user presses the back button.
     */
    public void resumeGame(){
        if (tim != null) {
            tim.resume();
        }
    }

}

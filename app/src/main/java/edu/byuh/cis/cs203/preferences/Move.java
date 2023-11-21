package edu.byuh.cis.cs203.preferences;

public class Move {
    Cell source; // from
    Cell destination; //To


    /**
     * Constructor for the Move class
     * @param source
     * @param destination
     */
    public Move(Cell source, Cell destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Getter for source
     * @return source
     */
    public Cell getSource(){
        return source;
    }

    /**
     * Getter for destination
     * @return destination
     */
    public Cell getDestination(){
        return destination;
    }
}

package bot;

import java.util.ArrayList;

import game.history.Move;

/**
 * A class representing the nodes in the minimax tree.
 * Implementation for both V1 (full tree structure) and V2 (minimal recursive callback)
 * @author Peter Szczuczko
 * @version 1.0
 * @since 2017-02-28 9:17 PM
 */
public class Edge {
    // -- Private Variables --
	
	// The calculated heuristic value of this node.
	// If there is a parent node, we will have to inform the parents of our value.
	float value;
	
	// The agreed upon integer list for undoing moves. (3,2,2,0,1).
    ArrayList<Integer> delta;
    
    // List of children nodes, as this is not a binary tree.
    ArrayList<Edge> children;
    
    enum type {MIN, MAX};
    // What type of node is this, one for min player or max?
    type NodeType;
    
    //Piece.Type[][] board;
    /**
     * Constructor class for each edge in the minimax tree.
     */
    public Edge() {
    	this.delta = new ArrayList<Integer> ();
    }

    /**
     * Constructor class for each edge in the minimax tree.
     * @param row Integer 1
     * @param column Integer 2
     */
    public Edge(Integer row, Integer column) {
    	this.delta = new ArrayList<Integer> ();
    	this.delta.add(row);
    	this.delta.add(column);
    }
    /**
     * Constructor class for each edge in the minimax tree.
     * @param row Integer 1
     * @param column Integer 2
     * @param nodeType An enumerated list for declaring what player is associated with this node (min or max).
     */
    public Edge (Integer row, Integer column, type nodeType) {
    	this.delta = new ArrayList<Integer> ();
    	this.delta.add(row);
    	this.delta.add(column);
    	this.NodeType = nodeType;
    }
    
    /**
     * Set the value of the edge.
     * @param value
     */
    public void setvalue (float value) {
    	this.value = value;
    }
    
    /**
     * Set the type of edge.
     * @param Type The player either MIN or MAX.
     */
    public void settype (type Type) {
    	this.NodeType = Type;
    }
    
    /**
     * Append a move to the delta list.
     * @param x First value (Column)
     * @param y Second value (row)
     */
    public void appenddelta (int x, int y) {
    	delta.add(x);
    	delta.add(y);
    }
    
    /**
     * Remove a pair of integers that represent a move in the delta list.
     */
    public void removedelta () {
    	delta.remove(delta.size()-1);
    	delta.remove(delta.size()-1);
    }
    
    /**
     * Initialize the delta list if it wasn't done before.
     */
    public void initdelta () {
    	this.delta = new ArrayList<Integer> ();
    }
    
    /**
     * Append a full move to the delta list, start to end cords.
     * @param x0 Starting x position (starting column)
     * @param y0 Starting y position (starting row)
     * @param x Ending x position (ending column)
     * @param y Ending y position (ending row)
     */
    public void appendmove (int y0, int x0, int y, int x) {
    	appenddelta (y0, x0);
    	appenddelta (y, x);
    }
    
    /**
     * Remove two pairs of integers in the delta list representing a move.
     */
    public void removemove () {
    	removedelta ();
    	removedelta ();
    }
    
    /**
     * Format the first 4 values in the delta list as a move.
     * @return start and end coordinates in the delta list.
     */
    public Move getMove () {
		return new Move(delta.get(0), delta.get(1), delta.get(2), delta.get(3));
    }
    
    /**
     * Append a child to the list of children.
     * @param child
     */
    public void addchild (Edge child) {
    	this.children.add(child);
    }
}

package chess;
import java.util.ArrayList;

/**
* This class contains the basic needs for any piece
* on the board.
*
* @author  Rohit Mahankali and Michael Belmont
*/

public abstract class Piece {
	String ID;
	String currentSquare;
	String Color;
	
	
	public Piece() {
		ID = "";
		currentSquare = "";
	}
		
	/**
	* This method returns the possible moves for the particular piece.
	* @return ArrayList An array list of all the possible moves.
	*/
	
	public abstract ArrayList<String> getMoveSet();//to find out which moves can be made by piece
	
	
}
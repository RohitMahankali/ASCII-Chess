package chess;
import java.util.ArrayList;

/**
* This class contains the necessary specific implementation
* for the piece Bishop only.
*
* @author  Rohit Mahankali and Michael Belmont
*/

public class Bishop extends Piece {
	public Bishop() {
		super();
	}
	
	/**
	* This method returns the possible moves for the particular bishop.
	* @return ArrayList An array list of all the possible moves.
	*/
	
	public ArrayList<String> getMoveSet(){
		char file = currentSquare.charAt(0);
		int rank = Integer.parseInt(currentSquare.substring(1));
		
		ArrayList<String> moveSet = new ArrayList<String>();
		
		while ((file+1)<= 'h' && (rank+1) <=8) {//checking right,up-diagonal
			moveSet.add((char)(file+1)+""+(rank+1));
			file++;
			rank++;
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((file-1)>= 'a' && (rank+1) <=8) {//checking left,up-diagonal
			moveSet.add((char)(file-1)+""+(rank+1));
			file--;
			rank++;
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((file+1)<= 'h' && (rank-1) >=1) {//checking right,down-diagonal
			moveSet.add((char)(file+1)+""+(rank-1));
			file++;
			rank--;
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((file-1)>= 'a' && (rank-1) >=1) {//checking left,down-diagonal
			moveSet.add((char)(file-1)+""+(rank-1));
			file--;
			rank--;
		}
		
		return moveSet;
	}
}
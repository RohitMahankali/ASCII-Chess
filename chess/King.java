package chess;
import java.util.ArrayList;

/**
* This class contains the necessary specific implementation
* for the piece King only.
*
* @author  Rohit Mahankali and Michael Belmont 
*/

public class King extends Piece{
	boolean hasMoved = false;
	public King() {
		super();
	}
	
	/**
	* This method returns the possible moves for the particular king.
	* @return ArrayList An array list of all the possible moves.
	*/
	
	public ArrayList<String> getMoveSet(){
		char file = currentSquare.charAt(0);
		int rank = Integer.parseInt(currentSquare.substring(1));
		
		ArrayList<String> moveSet = new ArrayList<String>();
		
		if ((file-1)>='a' && (rank-1)>=1) {//bottom left corner
			moveSet.add((char)(file-1)+""+(rank-1));
		}
		if ((file-1)>='a') {//left
			moveSet.add((char)(file-1)+""+rank);
		}
		if ((file-1)>='a' && (rank+1)<=8) {//top left corner
			moveSet.add((char)(file-1)+""+(rank+1));
		}
		if ((rank+1)<=8) {//top middle
			moveSet.add(file+""+(rank+1));
		}
		if ((file+1)<='h' && (rank+1)<=8) {//top right corner
			moveSet.add((char)(file+1)+""+(rank+1));
		}
		if ((file+1)<='h') {//right
			moveSet.add((char)(file+1)+""+rank);
		}
		if ((file+1)<='h' && (rank-1)>=1) {//bottom right corner
			moveSet.add((char)(file+1)+""+(rank-1));
		}
		if ((rank-1)>=1) {//bottom middle
			moveSet.add(file+""+(rank-1));
		}
		
		return moveSet;
	}
		
}
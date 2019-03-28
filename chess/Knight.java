package chess;
import java.util.ArrayList;

/**
* This class contains the necessary specific implementation
* for the piece Knight only.
*
* @author  Rohit Mahankali and Michael Belmont
*/

public class Knight extends Piece {
	public Knight() {
		super();
	}
	
	/**
	* This method returns the possible moves for the particular knight.
	* @return ArrayList An array list of all the possible moves.
	*/
	
	public ArrayList<String> getMoveSet(){
		char file = currentSquare.charAt(0);
		int rank = Integer.parseInt(currentSquare.substring(1));
		
		ArrayList<String> moveSet = new ArrayList<String>();
		
		if ((file-1)>='a' && (rank+2)<=8) {//top left
			moveSet.add((char)(file-1)+""+(rank+2));
		}
		if ((file-2)>='a' && (rank+1)<=8) {//top left
			moveSet.add((char)(file-2)+""+(rank+1));
		}
		if ((file-2)>='a' && (rank-1)>=1) {//bottom left corner
			moveSet.add((char)(file-2)+""+(rank-1));
		}
		if ((file-1)>='a' && (rank-2)>=1) {//bottom left corner
			moveSet.add((char)(file-1)+""+(rank-2));
		}
		if ((file+1)<='h' && (rank-2)>=1) {//bottom right corner
			moveSet.add((char)(file+1)+""+(rank-2));
		}
		if ((file+2)<='h' && (rank-1)>=1) {//bottom right corner
			moveSet.add((char)(file+2)+""+(rank-1));
		}
		if ((file+2)<='h' && (rank+1)<=8) {//bottom right corner
			moveSet.add((char)(file+2)+""+(rank+1));
		}
		if ((file+1)<='h' && (rank+2)<=8) {//bottom right corner
			moveSet.add((char)(file+1)+""+(rank+2));
		}
		
		return moveSet;
	}
		
}
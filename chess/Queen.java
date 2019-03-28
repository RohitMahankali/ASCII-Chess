package chess;
import java.util.ArrayList;

/**
* This class contains the necessary specific implementation
* for the piece Queen only.
*
* @author  Rohit Mahankali and Michael Belmont
*/

public class Queen extends Piece{
	public Queen() {
		super();
	}
	
	/**
	* This method returns the possible moves for the particular queen.
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
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		
		while ((rank+1) <=8) {//checking up
			moveSet.add(file+""+(rank+1));
			rank++;
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((rank-1) >=1) {//checking down
			moveSet.add(file+""+(rank-1));
			rank--;
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((file-1)>='a') {//checking left
			moveSet.add((char)(file-1)+""+rank);
			file--;
			
		}
		
		file = currentSquare.charAt(0);
		rank = Integer.parseInt(currentSquare.substring(1));
		
		while ((file+1)<= 'h') {//checking right
			moveSet.add((char)(file+1)+""+rank);
			file++;
			
		}
		
		return moveSet;
	}
		
}
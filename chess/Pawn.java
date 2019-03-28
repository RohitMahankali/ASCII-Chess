package chess;
import java.util.ArrayList;

/**
 * This class contains the necessary specific implementation
* for the piece Pawn only.
 * 
 * @author Rohit Mahankali and Michael Belmont
 *
 */
public class Pawn extends Piece {
	boolean hasMoved;
	boolean doublePush;
	boolean canCaptureLeft;//whether or not a certain diagonal capture can be made
	boolean canCaptureRight;
	boolean firstMove;
	boolean epLeft;
	boolean epRight;
	
	public Pawn() {
		super();
		hasMoved = false;
		canCaptureLeft = false;
		canCaptureRight = false;
		doublePush = false;
		firstMove = false;
		epLeft = false;
		epRight = false;
		
		/*if (ID.charAt(0) == 'w') {
			Color = "white";
		}
		else {
			Color = "black";
		}*/
		
	}
	
	/**
	* This method returns the possible moves for the particular pawn.
	* @return ArrayList An array list of all the possible moves.
	*/
	
	
	
	public ArrayList<String> getMoveSet(){
		char file = currentSquare.charAt(0);
		int rank = Integer.parseInt(currentSquare.substring(1));
		ArrayList<String> moveSet = new ArrayList<String>();
		if (this.ID.charAt(0) == 'w') {
			String ahead = file+""+(rank+1);
			moveSet.add(ahead);
			if (rank == 2) {
				String ahead2 = file+""+(rank+2);
				moveSet.add(ahead2);
			}
		}
		else {
			String ahead = file+""+(rank-1);
			moveSet.add(ahead);
			if (rank == 7) {
				String ahead2 = file+""+(rank-2);
				moveSet.add(ahead2);
			}
		}
		ArrayList<String>captures = Chess.getPawnCaptures(this);
		for (String c : captures) {
			moveSet.add(c);
		}
		return moveSet;
 	}
	/*
	public ArrayList<String> getMoveSet(){
		char file = currentSquare.charAt(0);
		int rank = Integer.parseInt(currentSquare.substring(1));
		
		ArrayList<String> moveSet = new ArrayList<String>();
		
		if (Color == "white") {
			if (this.hasMoved == false) {
				moveSet.add(file+""+4);
			}
			
			if ((rank+1)<=8) {//can always move pawn up one legally if space is there
				moveSet.add(file+""+(rank+1));
			}
			if (this.canCaptureLeft == true && (rank+1)<=8 && (file-1)>='a') {
				moveSet.add((char)(file-1)+""+(rank+1));
			}
			if (this.canCaptureRight == true && (rank+1)<=8 && (file+1)<='h') {
				moveSet.add((char)(file+1)+""+(rank+1));
			}
		}
		else {
			if (this.hasMoved == false) {
				moveSet.add(file+""+5);
			}
			if ((rank-1)>=1) {
				moveSet.add(file+""+(rank-1));
			}
			if (this.canCaptureLeft == true && (rank-1)>=1 && (file-1)>='a') {
				moveSet.add((char)(file-1)+""+(rank-1));
			}
			if (this.canCaptureRight == true && (rank-1)>=1 && (file+1)<='h') {
				moveSet.add((char)(file+1)+""+(rank-1));
			}
		}
		return moveSet;
		
	}
	*/
	
	}
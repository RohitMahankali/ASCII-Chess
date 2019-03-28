package chess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* <h1> 2-Player Chess Assignment (Group 81) </h1>
* 
* This Chess program will simulate a chess game with a
* drawn up board. It will prompt the user to enter in 
* their next move and will only complete that move if
* legal. The program will check if a certain king is 
* in check or checkmate and will display that to the 
* user. The user can either resign or input "draw" to
* end the game, otherwise they can continue to play until
* checkmate occurs and either black or white will win.
*
* @author  Rohit Mahankali and Michael Belmont 
*/

public class Chess {


	private static String [][] board = new String[9][9];
	private static ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	private static ArrayList<Piece> blackPieces = new ArrayList<Piece>();
	static String [] files = {"a","b","c","d","e","f","g","h"};
	
	//will keep track of who's turn it is
	public static boolean whiteTurn = true;
	
	//global king locations
	public static String wK_Loc;
	public static String bK_Loc;

	//for file with moves included
	//private static final String FILENAME = "moves.txt";
	//public static File file = new File(FILENAME);
	
	public static boolean inCheck = false;
	
	public static boolean checkMate = false;
	
	public static boolean staleMate = false;
	
	public static boolean enPass = false;
	
	
	
	/**
	* This method draws the chess board after every move
	* in the game.
	*/
	
	public static void drawBoard() {
		for (Piece wP : whitePieces) {
			String s = wP.currentSquare;
			
			char file = s.charAt(0);
			int rank = Integer.parseInt(s.substring(1));
			
			int xCor = 8-rank;
			int yCor = file-97;
			
			board[xCor][yCor] = wP.ID;
		}
		for (Piece bP : blackPieces) {
			String s = bP.currentSquare;
			
			char file = s.charAt(0);
			int rank = Integer.parseInt(s.substring(1));
			
			int xCor = 8-rank;
			int yCor = file-97;
			
			board[xCor][yCor] = bP.ID;
		}
		for (int i=0; i<8;i++) {
			board[8][i] = " "+files[i];
		}
		
		for (int i =0; i<8; i++) {
			for (int j =0; j<8; j++) {
				String chk = (char)(j+97)+""+(8-i);

				if (retrievePiece(chk) == null && (i+j)%2 == 1) {
					//System.out.println(board[i][j]);
					board[i][j] = "##";
				}
				else if (retrievePiece(chk) == null && (i+j)%2 == 0) {
					board[i][j] = "";
				}
			}
		}
		//color();
		for (int i =0; i<9; i++) {
			for (int j =0; j<8; j++) {
				if (board[i][j] == null) {
					board[i][j] = "";
					System.out.print("   ");//3 spaces
					
				}
				else if (board[i][j] == "") {
					System.out.print("   ");//3 spaces
					
				}
				else {
					System.out.print(board[i][j]+" ");
				}
			}
			if ((8-i)>0)
				System.out.print(8-i);
			System.out.println();
		}
		
	}
	
	/**
	* This method colors the board to make sure
	* it has black spaces where needed.
	*/
	
	public static void color() {
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				if (board[i][j] == null && (i+j)%2 == 1) {
					board[i][j] = "##";
				}
			}
		}
	}
	
	/**
	* This method adds each of the pieces in the game
	* to their respective lists to keep track of them.
	*/
	
	public static void setUpLists() {
		for (int i=0; i<8;i++) {//loop to add all pawns to their respective lists
			//System.out.print("dog");
			whitePieces.add(new Pawn());
			//System.out.println("cat");
			whitePieces.get(i).currentSquare = files[i]+""+2;
			whitePieces.get(i).ID = "wp";
			whitePieces.get(i).Color = "white";
			blackPieces.add(new Pawn());
			blackPieces.get(i).currentSquare = files[i]+""+7;
			blackPieces.get(i).ID = "bp";
			blackPieces.get(i).Color = "black";
		}
		//Creating all rooks below
		Piece wQueenRook = new Rook();
		wQueenRook.currentSquare = "a1";
		wQueenRook.ID = "wR";
		
		Piece wKingRook = new Rook();
		wKingRook.currentSquare = "h1";
		wKingRook.ID = "wR";
		
		Piece bQueenRook = new Rook();
		bQueenRook.currentSquare = "a8";
		bQueenRook.ID = "bR";
		
		Piece bKingRook = new Rook();
		bKingRook.currentSquare = "h8";
		bKingRook.ID = "bR";
		//adding all rooks to their respective lists
		whitePieces.add(wQueenRook);
		whitePieces.add(wKingRook);
		blackPieces.add(bQueenRook);
		blackPieces.add(bKingRook);
		
		//Creating all knights below
		Piece wQueenKnight = new Knight();
		wQueenKnight.currentSquare = "b1";
		wQueenKnight.ID = "wN";
		
		Piece wKingKnight = new Knight();
		wKingKnight.currentSquare = "g1";
		wKingKnight.ID = "wN";
		
		Piece bQueenKnight = new Knight();
		bQueenKnight.currentSquare = "b8";
		bQueenKnight.ID = "bN";
		
		Piece bKingKnight = new Knight();
		bKingKnight.currentSquare = "g8";
		bKingKnight.ID = "bN";
		
		//adding all knights to their respective lists
		whitePieces.add(wQueenKnight);
		whitePieces.add(wKingKnight);
		blackPieces.add(bQueenKnight);
		blackPieces.add(bKingKnight);
				
		//Creating all bishops below
		Piece wQueenBishop = new Bishop();
		wQueenBishop.currentSquare = "c1";
		wQueenBishop.ID = "wB";
		
		Piece wKingBishop = new Bishop();
		wKingBishop.currentSquare = "f1";
		wKingBishop.ID = "wB";
		
		Piece bQueenBishop = new Bishop();
		bQueenBishop.currentSquare = "c8";
		bQueenBishop.ID = "bB";
		
		Piece bKingBishop = new Bishop();
		bKingBishop.currentSquare = "f8";
		bKingBishop.ID = "bB"; //adding all bishops to their respective lists
		whitePieces.add(wQueenBishop);
		whitePieces.add(wKingBishop);
		blackPieces.add(bQueenBishop);
		blackPieces.add(bKingBishop);
		
		//Creating both Queens
		Piece wQueen = new Queen();
		wQueen.currentSquare = "d1";
		wQueen.ID = "wQ";
		
		Piece bQueen = new Queen();
		bQueen.currentSquare = "d8";
		bQueen.ID = "bQ";
		//adding both queens to their respective lists
		whitePieces.add(wQueen);
		blackPieces.add(bQueen);
		
		//Creating both Kings
		Piece wKing = new King();
		wKing.currentSquare = "e1";
		wK_Loc = wKing.currentSquare;
		wKing.ID = "wK";
		
		Piece bKing = new King();
		bKing.currentSquare = "e8";
		bK_Loc = bKing.currentSquare;
		bKing.ID = "bK";
		//adding both kings to their respective lists
		whitePieces.add(wKing);
		blackPieces.add(bKing);
	}
	
	/**
	* This method finds if a piece exists at a certain
	* location that was input by the user.
	*
	* @param square This is the location that will be checked to see if there is a piece.
	* @return Piece Will return the piece that is at that location if there is one, otherwise null.
	*/ 
	
	public static Piece retrievePiece(String square) {//Find the piece (if it exists) at the input square
		for (Piece p: whitePieces) {
			if (p.currentSquare.equals(square)) {
				return p;
			}
		}
		for (Piece p: blackPieces) {
			if (p.currentSquare.equals(square)) {
				return p;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param p Selected piece, or pawn, which users wants to search adjacent captures for
	 * @return ArrayList returns a set of Strings, or coordinates, of squares that can be captured upon. Empty ArrayList means no captures are possible for the pawn p
	 * 
	 */
	
	public static ArrayList<String> getPawnCaptures(Piece p){
		ArrayList<String>captures = new ArrayList<String>();
		Pawn p1 = (Pawn)p;
		String curr = p1.currentSquare;
		char file = curr.charAt(0);
		int rank = Integer.parseInt(curr.substring(1));
		char color = p1.ID.charAt(0);
		String leftDiag = "";
		String rightDiag = "";
		boolean hasLeft = false;
		boolean hasRight = false;
		

		if (color == 'w') {
			if (file == 'a') {
				rightDiag = 'b'+""+(rank+1);
				if (retrievePiece(rightDiag) != null && retrievePiece(rightDiag).ID.charAt(0) == 'b') {
					hasRight = true;
				}
			}
			else if (file == 'h') {
				leftDiag = 'g'+""+(rank+1);
				if (retrievePiece(leftDiag) != null && retrievePiece(leftDiag).ID.charAt(0) == 'b') {
					hasLeft = true;
				}
			}
			else {
				leftDiag = (char)(file-1)+""+(rank+1);
				rightDiag = (char)(file+1)+""+(rank+1);
				if (retrievePiece(rightDiag) != null && retrievePiece(rightDiag).ID.charAt(0) == 'b') {
					hasRight = true;
				}
				if (retrievePiece(leftDiag) != null && retrievePiece(leftDiag).ID.charAt(0) == 'b') {
					hasLeft = true;
				}
			}
		}
		else {
			if (file == 'a') {
				rightDiag = 'b'+""+(rank-1);
				if (retrievePiece(rightDiag) != null && retrievePiece(rightDiag).ID.charAt(0) == 'w') {
					hasRight = true;
				}
			}
			else if (file == 'h') {
				leftDiag = 'g'+""+(rank-1);
				if (retrievePiece(leftDiag) != null && retrievePiece(leftDiag).ID.charAt(0) == 'w') {
					hasLeft = true;
				}
			}
			else {
				leftDiag = (char)(file-1)+""+(rank-1);
				rightDiag = (char)(file+1)+""+(rank-1);
				if (retrievePiece(rightDiag) != null && retrievePiece(rightDiag).ID.charAt(0) == 'w') {
					hasRight = true;
				}
				if (retrievePiece(leftDiag) != null && retrievePiece(leftDiag).ID.charAt(0) == 'w') {
					hasLeft = true;
				}
			}
		}
		if (hasLeft) {
			captures.add(leftDiag);
		}
		if (hasRight) {
			captures.add(rightDiag);
		}
		return captures;
	}
	
	
	/**
	 * 
	 * @param p Piece that is being checked if it can move to desired square
	 * @param destSquare Coordinate of desired square
	 * @return boolean Determining whether a move can be made for piece to desired square
	 */
public static boolean canMove(Piece p, String destSquare) {//check if it's possible to make it to destination square
		
		boolean flag = true;
		String s = checkObstructions(p,destSquare);
		if (s == null) {
			return flag;
		}
		else {
			Piece piece;
			//char col;
			//System.out.println(s);
			if (!s.equals(destSquare)) {
				//System.out.println("Illegal move, try again");
				flag = false;
				return flag;
			}
			else {
				piece = retrievePiece(destSquare);
				if (piece != null) {
					//col = piece.ID.charAt(0);
				
					if (p.ID.charAt(0) == piece.ID.charAt(0)) {
						//System.out.println("Illegal move, try again");
						flag = false;
						return flag;
					}
					else {
						if (p instanceof Pawn) {
							if (p.currentSquare.charAt(0) == piece.currentSquare.charAt(0)) {
								//System.out.println("Illegal move, try again");
								flag = false;
								return flag;
							}
							
						}

					}
				}
			}
		}
		return flag;
}
	
	
	
	
	/**
	* This method checks to see if there are any pieces in the way
	* of the piece that is about to be moved.
	*
	* @param p Piece that the user wants to move.
	* @param destSquare The location that the user wants to move the piece to.
	* @return String returns null if there is no obstruction and a coordinate if there is one, to indicate at which point
	*/
	
	public static String checkObstructions(Piece p, String destSquare) {//Needed for every piece besides knight
		
		String currSquare = p.currentSquare;
		String test = null;
		if (p instanceof Knight) {
			if (retrievePiece(destSquare) == null) {
				test = null;
			}
			else {
				test = destSquare;
			}
		}
		else if (p instanceof Bishop) {
			int directionB = 0;//could be one of 4 possibilities
			if (currSquare.charAt(0) > destSquare.charAt(0) && currSquare.charAt(1) < destSquare.charAt(1)) {
				directionB = 1;//up,left diagonal
			}
			else if (currSquare.charAt(0) > destSquare.charAt(0) && currSquare.charAt(1) > destSquare.charAt(1)) {
				directionB = 2;//down,left diagonal
			}
			else if (currSquare.charAt(0) < destSquare.charAt(0) && currSquare.charAt(1) > destSquare.charAt(1)) {
				directionB = 3;//down,right diagonal
			}
			else if (currSquare.charAt(0) < destSquare.charAt(0) && currSquare.charAt(1) < destSquare.charAt(1)) {
				directionB = 4;//up,right diagonal
			}
			
			int diff = currSquare.charAt(0) - destSquare.charAt(0);
			int diff2 = destSquare.charAt(0) - currSquare.charAt(0);
			if (directionB == 1) {
				
				for (int i = diff; i>=0; i--) {
					if (i == diff) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)+i)+""+(char)(destSquare.charAt(1)-i);
						//System.out.println(test);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected" + " 309");
						return test;
					}
					else {  
						continue;
					}
				}
				return null;
			}
			else if (directionB == 2) {
				for (int i = diff; i>=0; i--) {
					if (i == diff) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)+i)+""+(char)(destSquare.charAt(1)+i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			else if (directionB == 3) {
				
				for (int i = diff2; i>=0; i--) {
					if (i == diff2) {
						continue;
					}
					else {
						
						test = (char)(destSquare.charAt(0)-i)+""+(char)(destSquare.charAt(1)+i);
						//System.out.println(test);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			if (directionB == 4) {
				for (int i = diff2; i>=0; i--) {
					if (i == diff2) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)-i)+""+(char)(destSquare.charAt(1)-i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			return test;
		}
		else if (p instanceof Rook) {
			//vertical part
			int start = Integer.parseInt(currSquare.substring(1));
			int end = Integer.parseInt(destSquare.substring(1));
			
			
			
			char file = currSquare.charAt(0);
			
			//below info for horizontal case
			int rank = Integer.parseInt(currSquare.substring(1));
			
			char beg = currSquare.charAt(0);
			char fin = destSquare.charAt(0);
			
			int vDirection = 0;
			int hDirection = 0;
			if (beg == fin) {
				if (start > end) {
					vDirection = -1;
				}
				else if (start < end) {
					vDirection = 1;
				}
				//else if (start == end) {
					//System.out.println("Illegal Move");
				//}
			}
			else {
				if (beg > fin) {
					hDirection = -1;
				}
				else if (beg < fin) {
					hDirection = 1;
				}
				//else if (beg == fin) {
					//System.out.println("Illegal Move");
				//}
			}
			
			if (vDirection == 1) {
				for (int i = start; i<=end;i++) {
					if (i == start)
						continue;
					else {
						test = (file+""+i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
						
					}
				}
				return null;
			}
			else if (vDirection == -1) {
				for (int i = start; i>=end;i--) {
					if (i == start)
						continue;
					else {
						test = (file+""+i);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			if (hDirection == 1) {
				for (char i = beg; i<=fin;i++) {
					if (i == beg)
						continue;
					else {
						test = (i+""+rank);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			else if (hDirection == -1) {
				for (char i = beg; i>=fin;i--) {
					if (i == beg)
						continue;
					else {
						test = (i+""+rank);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			
			return null;
		}
		else if (p instanceof Queen) {
			int directionB = 0;//could be one of 4 possibilities
			if (currSquare.charAt(0) > destSquare.charAt(0) && currSquare.charAt(1) < destSquare.charAt(1)) {
				directionB = 1;//up,left diagonal
			}
			else if (currSquare.charAt(0) > destSquare.charAt(0) && currSquare.charAt(1) > destSquare.charAt(1)) {
				directionB = 2;//down,left diagonal
			}
			else if (currSquare.charAt(0) < destSquare.charAt(0) && currSquare.charAt(1) > destSquare.charAt(1)) {
				directionB = 3;//down,right diagonal
			}
			else if (currSquare.charAt(0) < destSquare.charAt(0) && currSquare.charAt(1) < destSquare.charAt(1)) {
				directionB = 4;//up,right diagonal
			}
			
			int diff = currSquare.charAt(0) - destSquare.charAt(0);
			int diff2 = destSquare.charAt(0) - currSquare.charAt(0);
			if (directionB == 1) {
				
				for (int i = diff; i>=0; i--) {
					if (i == diff) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)+i)+""+(char)(destSquare.charAt(1)-i);
						//System.out.println(test);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			else if (directionB == 2) {
				for (int i = diff; i>=0; i--) {
					if (i == diff) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)+i)+""+(char)(destSquare.charAt(1)+i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			else if (directionB == 3) {
				
				for (int i = diff2; i>=0; i--) {
					if (i == diff2) {
						continue;
					}
					else {
						
						test = (char)(destSquare.charAt(0)-i)+""+(char)(destSquare.charAt(1)+i);
						//System.out.println(test);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			if (directionB == 4) {
				for (int i = diff2; i>=0; i--) {
					if (i == diff2) {
						continue;
					}
					else {
						test = (char)(destSquare.charAt(0)-i)+""+(char)(destSquare.charAt(1)-i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
					}
					else {
						continue;
					}
				}
				return null;
			}
			
			//Rook part for queen:
			/*
			* 
			* 
			* 
			*/
			//vertical part
			int start = Integer.parseInt(currSquare.substring(1));
			int end = Integer.parseInt(destSquare.substring(1));
			
			
			
			char file = currSquare.charAt(0);
			
			//below info for horizontal case
			int rank = Integer.parseInt(currSquare.substring(1));
			
			char beg = currSquare.charAt(0);
			char fin = destSquare.charAt(0);
			
			int vDirection = 0;
			int hDirection = 0;
			if (beg == fin) {
				if (start > end) {
					vDirection = -1;
				}
				else if (start < end) {
					vDirection = 1;
				}
				//else if (start == end) {
				//	System.out.println("Illegal Move");
				//}
			}
			else {
				if (beg > fin) {
					hDirection = -1;
				}
				else if (beg < fin) {
					hDirection = 1;
				}
				//else if (beg == fin) {
				//	System.out.println("Illegal Move");
				//}
			}
			
			if (vDirection == 1) {
				for (int i = start; i<=end;i++) {
					if (i == start)
						continue;
					else {
						test = (file+""+i);
					}
					if (retrievePiece(test) != null) {
						//System.out.println("Obstruction detected");
						return test;
						
					}
				}
				return null;
			}
			else if (vDirection == -1) {
				for (int i = start; i>=end;i--) {
					if (i == start)
						continue;
					else {
						test = (file+""+i);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			if (hDirection == 1) {
				for (char i = beg; i<=fin;i++) {
					if (i == beg)
						continue;
					else {
						test = (i+""+rank);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			else if (hDirection == -1) {
				for (char i = beg; i>=fin;i--) {
					if (i == beg)
						continue;
					else {
						test = (i+""+rank);
					}
					if (retrievePiece(test) != null) {
						return test;
						
					}
				}
				return null;
			}
			
			return null;
		}
		else if (p instanceof King) {
			if (retrievePiece(destSquare) != null) {
				test = destSquare;
			}
			else {
				test = null;
			}
			return test;
		}
		else if (p instanceof Pawn) {
			if (retrievePiece(destSquare) != null) {
				test = destSquare;
			}
			else {
				test = null;
			}
			/*
			int rankDiff = destSquare.charAt(1)-currSquare.charAt(1);
			if (rankDiff == 2) {
				if (retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1)) == null && retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+2)) == null) {
					test = null;
				}
				else if (retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1)) == null && retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+2)) != null) {
					test = currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+2);
				}
				else if (retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1)) != null){
					test = currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1);
				}
				
			}
			else if (rankDiff == 1){
				if (retrievePiece(currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1)) == null) {
					test = null;
					//return test;
				}
				else {
					test = currSquare.charAt(0)+""+(char)(currSquare.charAt(1)+1);
				}
			}
			*/
			return test;
		}
		return test;
	}
	
	/**
	* This method moves a piece to a location both desired by the player.
	*
	* @param p Piece that the user wants to move.
	* @param destSquare The location that the user wants to move the piece to.
	* @return boolean Whether the selected move is legitimate given the piece and to/from squares in question
	* 
	*/
	
public static boolean makeMove(Piece p, String destSquare) {//check if it's possible to make it to destination square
		
		boolean flag = true;
		String s = checkObstructions(p,destSquare);
		if (s == null) {
			if (!enPass) {
				//System.out.println("cheese");
				p.currentSquare = destSquare;
			}
			else {
				//System.out.println("Kevin Durant");
				char color = p.ID.charAt(0);
				if (color == 'w') {
					p.currentSquare = destSquare;
					blackPieces.remove(retrievePiece(destSquare.charAt(0)+""+(char)(destSquare.charAt(1)-1)));
					return flag;
				}
				else {
					p.currentSquare = destSquare;
					whitePieces.remove(retrievePiece(destSquare.charAt(0)+""+(char)(destSquare.charAt(1)+1)));
					return flag;
				}
			}
		}
		else {
			Piece piece;
			char col;
			//System.out.println(s);
			if (!s.equals(destSquare)) {
				System.out.println("Illegal move, try again");
				flag = false;
				return flag;
			}
			else {
				piece = retrievePiece(destSquare);
				if (piece != null) {
					col = piece.ID.charAt(0);
				
					if (p.ID.charAt(0) == piece.ID.charAt(0)) {
						System.out.println("Illegal move, try again");
						flag = false;
						return flag;
					}
					else {
						if (p instanceof Pawn) {
							if (p.currentSquare.charAt(0) == piece.currentSquare.charAt(0)) {
								System.out.println("Illegal move, try again");
								flag = false;
								return flag;
							}
							
						}
						if (col == 'w') {
							//System.out.println("Romana Pizza");
							whitePieces.remove(piece);
							p.currentSquare = destSquare;
						}
						else if (col == 'b') {
							//System.out.println("Venezzia Pasta");
							blackPieces.remove(piece);
							//System.out.println("blood has been spilled");
							p.currentSquare = destSquare;
						}
					}
				}
			}
		}
		if (p instanceof King) {
			((King) p).hasMoved = true;
			if (p.ID == "wK") {
				wK_Loc = p.currentSquare;
			}
			else if (p.ID == "bK") {
				bK_Loc = p.currentSquare;
			}
		}
		else if (p instanceof Rook) {
			((Rook) p).hasMoved = true;
		}
		/*
		else if (p instanceof Pawn) {
			boolean comp = !((Pawn) p).firstMove;
			if(p.ID == "wp" && p.currentSquare.charAt(1) == '4') {
				((Pawn) p).firstMove = comp;
			}
			else if(p.ID == "bp" && p.currentSquare.charAt(1) == '5') {
				((Pawn) p).firstMove = comp;
			}
			((Pawn) p).firstMove = false;
			
			Piece m = retrievePiece((char)(p.currentSquare.charAt(0) - 1) + "" + (char)(p.currentSquare.charAt(1) + 1));
			Piece left = retrievePiece((char)(p.currentSquare.charAt(0) - 1) + "" + p.currentSquare.charAt(1));
		}
		*/
		return flag;
	}

	/**
	* This method looks to see if the respective king is in check
	* @param p The piece that was just moved before checking
	* @return boolean whether either king is in a state of check
	*/

	public static boolean detectCheck(Piece p) {
		ArrayList<String> set = p.getMoveSet();
		if (p.ID.charAt(0) == 'w') {//then chasing the black king
			if (set.contains(bK_Loc)) {
				String block = checkObstructions(p,bK_Loc);
				if (block == null || block.equals(bK_Loc)) {
					System.out.println();
					System.out.println("Check");
					return true;
				}
			}
		}
		else if (p.ID.charAt(0) == 'b') {//then chasing the white king
			if (set.contains(wK_Loc)) {
				String block = checkObstructions(p,wK_Loc);
				if (block == null || block.equals(wK_Loc)) {
					System.out.println();
					System.out.println("Check");
					return true;
				}
		}
	}
		return false;
}
		/* call checkObstructions on p to see if the move is feasible.
		* If there is an obstructing piece, set movable to false
		* 
		* Finally, if movable is false, print "Illegal move...", 
		* If movable is true, set currentSquare for the piece to destSquare, and update "board"
		* ,by moving string at starting square to destSquare
		*/
	//}
	//Need functionality to detect capture and remove captured piece from its arrayList
	//convert square alphanumberic to indexed location in board:
		//if it is "" at that index combo, then simply move piece
		//if there is some string like "bp", then retrieve that corresponding piece, remove it from its arrayList, and then make the move for the intended piece
	
	
	
	//ADD JAVA DOCTAGS TO BELOW
	
	//Method only for the king

	/**
	 * 
	 * @return boolean To see if the king can escape on his own
	 */
	public static boolean kingCanEscape() {
		boolean defended = false;
		ArrayList<ArrayList<String>> paths = getPathsToKing();
		if (whiteTurn) {//chasing black king
			Piece bKing = retrievePiece(bK_Loc);
			ArrayList<String> options = bKing.getMoveSet();
			ArrayList<String> blocks = new ArrayList<String>();
			ArrayList<String> valids = new ArrayList<String>();
			for (String c : options) {
				if (checkObstructions(bKing,c) == null) {
					valids.add(c);
				}
				else {
					blocks.add(c);
				}
			}
			for (String b : blocks) {
				if (retrievePiece(b).ID.charAt(0) == 'w') {
					for (Piece wP : whitePieces) {
						if (checkObstructions(wP,b) != null && checkObstructions(wP,b).equals(b)) {
							defended = true;
							break;
						}
					}
					if (!defended) {
						valids.add(b);
					}
				}
			}
			/*
			for (String v : valids) {
				System.out.println("DOGGOOOOOO");
				System.out.print(v+" ");
			}
			*/
			for (ArrayList<String>a : paths) {
				for (String c : a) {
					if (valids.contains(c)) {
						valids.remove(c);
					}
				}
			}
			//for (String v : valids) {
			//	System.out.println(v);
			//}
			if (valids.isEmpty()) {
				return false;
			}
			else {
				return true;
			}
			
		}
		else {
			Piece wKing = retrievePiece(wK_Loc);
			ArrayList<String> options = wKing.getMoveSet();
			ArrayList<String> blocks = new ArrayList<String>();
			ArrayList<String> valids = new ArrayList<String>();
			for (String c : options) {
				if (checkObstructions(wKing,c) == null) {
					valids.add(c);
				}
				else {
					blocks.add(c);
				}
			}
			for (String b : blocks) {
				if (retrievePiece(b).ID.charAt(0) == 'b') {
					for (Piece bP : blackPieces) {
						if (checkObstructions(bP,b) != null && checkObstructions(bP,b).equals(b)) {
							defended = true;
							break;
						}
					}
					if (!defended) {
						valids.add(b);
					}
				}
			}
			for (ArrayList<String>a : paths) {
				for (String c : a) {
					if (valids.contains(c)) {
						valids.remove(c);
					}
				}
			}
			if (valids.isEmpty()) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	/**
	 * 
	 * @return boolean To check whether any friendly pieces can obstruct an attack path to protect their king
	 */
	
	public static boolean canCoverKing() {
		boolean canBlock = false;
		ArrayList<ArrayList<String>> paths = getPathsToKing(); //to call this method, we know length of path is 1
		ArrayList<String> path = paths.get(0);
		if (whiteTurn) {//chasing black king
			for (String c : path) {
				Piece attacker = null;
				if (c.equals(bK_Loc)) {
					continue;
				}
				if (retrievePiece(c) != null) {
					attacker = retrievePiece(c);
				}
				for (Piece bP : blackPieces) {
					if (bP.ID.equals("bK")) {
						continue;
					}
					if (bP instanceof Pawn && attacker != null) {
						if (bP.currentSquare.charAt(0) == attacker.currentSquare.charAt(0)) {
							break;
						}
					}
					boolean fairMove = false;
					if (bP.getMoveSet().contains(c)) {
						fairMove = true;
					}
					if (!fairMove) {
						continue;
					}
					if (checkObstructions(bP,c) == null || retrievePiece(checkObstructions(bP,c)).ID.charAt(0) == 'w') {
						//System.out.println("YOOOOOO:"+bP.currentSquare);
						canBlock = true;//black piece can come block
						break;
					}
				}
				if (canBlock) {
					break;
				}
			}
			return canBlock;
		}
		else {
			for (String c : path) {
				Piece attacker = null;
				if (c.equals(wK_Loc)) {
					continue;
				}
				if (retrievePiece(c) != null) {
					attacker = retrievePiece(c);
				}
				for (Piece wP : whitePieces) {
					if (wP.ID.equals("wK")) {
						continue;
					}
					if (wP instanceof Pawn && attacker != null) {
						if (wP.currentSquare.charAt(0) == attacker.currentSquare.charAt(0)) {
							break;
						}
					}
					boolean fairMove = false;
					if (wP.getMoveSet().contains(c)) {
						fairMove = true;
					}
					if (!fairMove) {
						continue;
					}
					if (checkObstructions(wP,c) == null || retrievePiece(checkObstructions(wP,c)).ID.charAt(0) == 'b') {
						canBlock = true;//black piece can come block
						break;
					}
				}
				if (canBlock) {
					break;
				}
			}
			return canBlock;
		}
	}
	
	/**
	 * 
	 * @return ArrayList of squares covered by the enemy camp
	 */
	public static ArrayList<String> adversarials(){
		ArrayList<String> union = new ArrayList<String>();
		if (whiteTurn) {
			for (Piece bP : blackPieces) {
				for (String square : bP.getMoveSet()) {
					if (!union.contains(square))  {
						union.add(square);
					}
				}
			}
		}
		else {
			for (Piece wP : whitePieces) {
				for (String square : wP.getMoveSet()) {
					if (!union.contains(square)) {
						union.add(square); 
					}
				}
			}
		}
		return union;
	}
	
	
	/*
	
	public static ArrayList<String> attacks(){
		ArrayList<String> attacks = new ArrayList<String>();
		if (whiteTurn) {//WHITE JUST MOVED AND IS ATTACKING BLACK KING
			//Piece bKing = retrievePiece(bK_Loc);
			//ArrayList<String> possibilities = bKing.getMoveSet();
			for (Piece wP : whitePieces) {
				for (String square : wP.getMoveSet()) {
					String obstructions = checkObstructions(wP,square);
					if (obstructions == null || obstructions.equals(bK_Loc)) {
						attacks.add(square);
					}
				}
			}
		}
		else {
			//Piece wKing = retrievePiece(wK_Loc);
			//ArrayList<String> possibilities = wKing.getMoveSet();
			for (Piece bP : blackPieces) {
				for (String square : bP.getMoveSet()) {
					String obstructions = checkObstructions(bP,square);
					if (obstructions == null || obstructions.equals(wK_Loc)) {
						attacks.add(square);
					}
				}
			}
		}
		return attacks;
	}
	*/
	
	//Excludes destSquare
	
	/**
	 * 
	 * @param p The piece that is being selected for movement by the user; is it pinned?
	 * @return boolean Test to determine whether or not piece is pinned to it's king
	 */
	
	public static boolean isPinned(Piece p) {
		String currSquare = p.currentSquare;
		boolean pinned = false;
		if (whiteTurn) {
			for (Piece bP : blackPieces) {
				if (!bP.getMoveSet().contains(wK_Loc)) {
					continue;
				}
				if (checkObstructions(bP,wK_Loc)!= null && checkObstructions(bP,wK_Loc).equals(p.currentSquare)) {
					p.currentSquare = "";
					if (checkObstructions(bP,wK_Loc)!=null && checkObstructions(bP,wK_Loc).equals(wK_Loc)) {
						pinned = true;
						break;
					}
				}
			}
		}
		else {
			for (Piece wP : whitePieces) {
				if (!wP.getMoveSet().contains(bK_Loc)) {
					continue;
				}
				if (checkObstructions(wP,bK_Loc)!= null && checkObstructions(wP,bK_Loc).equals(p.currentSquare)) {
					p.currentSquare = "";
					if (checkObstructions(wP,bK_Loc)!=null && checkObstructions(wP,bK_Loc).equals(bK_Loc)) {
						pinned = true;
						//System.out.println(wP.currentSquare);
						break;
					}
				}
			}
		}
		p.currentSquare = currSquare;
		return pinned;
	}
	
	
	/**
	 * 
	 * @return boolean whether there exist legal moves to be made
	 */
	
	public static boolean areLegalMoves() {
		//ArrayList<String> legals = new ArrayList<String>();
		if (whiteTurn) {
			for (Piece bP : blackPieces) {
				ArrayList<String> poss = bP.getMoveSet();
				for (String move : poss) {
					if (canMove(bP,move)) {
						return true;
					}
				}
			}
		}
		else {
			for (Piece wP : whitePieces) {
				ArrayList<String> poss = wP.getMoveSet();
				for (String move : poss) {
					if (canMove(wP,move)) {
						return true;
					}
				}
			}
		}
		staleMate = true;
		return false;
	}
	
	
	
	
	

	
	
	/**
	 * 
	 * @return An ArrayList of ArrayLists of Strings, which contains the various paths of all pieces, directed at enemy king
	 */
	public static ArrayList<ArrayList<String>> getPathsToKing(){
		ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();
		String blockage = "";
		if (whiteTurn) {
			for (Piece wP : whitePieces) {
				boolean fairMove = false;
				if (wP.getMoveSet().contains(bK_Loc)) {
					fairMove = true;
				}
				if (fairMove) {
					blockage = checkObstructions(wP,bK_Loc);
				}
				else {
					continue;
				}
				
				if (blockage.equals(bK_Loc)) {
					ArrayList<String>path = new ArrayList<String>();
					String curr = wP.currentSquare;
					String dest = bK_Loc;
					//bishop f1 to c4
					int minFile = Math.min(curr.charAt(0), dest.charAt(0)); //c
					int maxFile = Math.max(curr.charAt(0), dest.charAt(0)); //f
					int minRank = Math.min(curr.charAt(1), dest.charAt(1)); //1
					int maxRank = Math.max(curr.charAt(1), dest.charAt(1)); //4
					if (wP instanceof Bishop) {
						if (curr.charAt(1) > dest.charAt(1)) {//c4 to b3
							if (curr.charAt(0) > dest.charAt(0)) {
								int tempRank = (minRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank++;
								}
							}
							else {//b5 to c4
								int tempRank = (maxRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank--;
								}
							}
						}
						else if (curr.charAt(1) < dest.charAt(1)) {//f1 to b5
							if (curr.charAt(0) > dest.charAt(0)) {
								int tempRank = (maxRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank--;
								}
							}
							else {//c4 to f7
								int tempRank = (minRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank++;
								}
							}
						}
					}
					else if (wP instanceof Rook) {
						if (minRank == maxRank) {
							for (int i =minFile; i <maxFile; i++) {
								String sq = ((char)i)+""+(minRank-'0');
								//System.out.println(sq);
								path.add(sq);
							}
						}
						else if (minFile == maxFile) {
							for (int i =minRank; i <maxRank; i++) {
								String sq = ((char)minFile)+""+(i-'0');
								//System.out.println(sq);
								path.add(sq);
							}
						}
					}
					else if (wP instanceof Queen) {
						if (minRank == maxRank) {
							for (int i =minFile; i <maxFile; i++) {
								String sq = ((char)i)+""+(minRank-'0');
								//System.out.println(sq);
								path.add(sq);
							}
						}
						else if (minFile == maxFile) {
							for (int i =minRank; i <maxRank; i++) {
								String sq = ((char)minFile)+""+(i-'0');
								path.add(sq);
							}
						}
						else {
							if (curr.charAt(1) > dest.charAt(1)) {//c4 to b3
								if (curr.charAt(0) > dest.charAt(0)) {
									int tempRank = (minRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank++;
									}
								}
								else {//b5 to c4
									int tempRank = (maxRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank--;
									}
								}
							}
							else if (curr.charAt(1) < dest.charAt(1)) {//f1 to b5
								if (curr.charAt(0) > dest.charAt(0)) {
									int tempRank = (maxRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank--;
									}
								}
								else {//c4 to f7
									int tempRank = (minRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank++;
									}
								}
							}
						}
					}

					all.add(path);
				}
				//all.add(path);
			}
		}
		else {
			for (Piece bP : blackPieces) {
				boolean fairMove = false;
				if (bP.getMoveSet().contains(wK_Loc)) {
					fairMove = true;
				}
				if (fairMove) {
					blockage = checkObstructions(bP,wK_Loc);
				}
				else {
					continue;
				}
				//String blockage = checkObstructions(bP,wK_Loc);
				if (blockage.equals(wK_Loc)) {
					ArrayList<String>path = new ArrayList<String>();
					String curr = bP.currentSquare;
					String dest = wK_Loc;
					//bishop f1 to c4
					int minFile = Math.min(curr.charAt(0), dest.charAt(0)); //c
					int maxFile = Math.max(curr.charAt(0), dest.charAt(0)); //f
					int minRank = Math.min(curr.charAt(1), dest.charAt(1)); //1
					int maxRank = Math.max(curr.charAt(1), dest.charAt(1)); //4
					if (bP instanceof Bishop) {
						if (curr.charAt(1) > dest.charAt(1)) {//c4 to b3
							if (curr.charAt(0) > dest.charAt(0)) {
								int tempRank = (minRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank++;
								}
							}
							else {//b5 to c4
								int tempRank = (maxRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank--;
								}
							}
						}
						else if (curr.charAt(1) < dest.charAt(1)) {//f1 to b5
							if (curr.charAt(0) > dest.charAt(0)) {
								int tempRank = (maxRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank--;
								}
							}
							else {//c4 to f7
								int tempRank = (minRank-'0');
								for (int i = minFile; i<=maxFile;i++) {
									char f = (char)i;
									String sq = f+""+tempRank;
									path.add(sq);
									tempRank++;
								}
							}
						}
					}
					else if (bP instanceof Rook) {
						if (minRank == maxRank) {
							for (int i =minFile; i <maxFile; i++) {
								String sq = ((char)i)+""+(minRank-'0');
								path.add(sq);
							}
						}
						else if (minFile == maxFile) {
							for (int i =minRank; i <maxRank; i++) {
								String sq = ((char)minFile)+""+(i-'0');
								path.add(sq);
							}
						}
					}
					else if (bP instanceof Queen) {
						if (minRank == maxRank) {
							for (int i =minFile; i <maxFile; i++) {
								String sq = ((char)i)+""+(minRank-'0');
								path.add(sq);
							}
						}
						else if (minFile == maxFile) {
							for (int i =minRank; i <maxRank; i++) {
								String sq = ((char)minFile)+""+(i-'0');
								path.add(sq);
							}
						}
						else {
							if (curr.charAt(1) > dest.charAt(1)) {//c4 to b3
								if (curr.charAt(0) > dest.charAt(0)) {
									int tempRank = (minRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank++;
									}
								}
								else {//b5 to c4
									int tempRank = (maxRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank--;
									}
								}
							}
							else if (curr.charAt(1) < dest.charAt(1)) {//f1 to b5
								if (curr.charAt(0) > dest.charAt(0)) {
									int tempRank = (maxRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank--;
									}
								}
								else {//c4 to f7
									int tempRank = (minRank-'0');
									for (int i = minFile; i<=maxFile;i++) {
										char f = (char)i;
										String sq = f+""+tempRank;
										path.add(sq);
										tempRank++;
									}
								}
							}
						}
					}

					all.add(path);
				}
				//all.add(path);
			}
			
		}
		/*
		for (ArrayList<String>a : all) {
			System.out.print("{ ");
			for (String c : a) {
				System.out.print(c+" ");
			}
			System.out.print(" }");
		}
		*/
		return all;
	}
	
	/**
	 * 
	 * @return Piece Attacker of the turn player's king
	 */
	public static Piece attacker() {
		if (whiteTurn) {//Chasing black king
			for (Piece bP : blackPieces) {
				if (!bP.getMoveSet().contains(wK_Loc)) {
					continue;
				}
				else {
					if (checkObstructions(bP,wK_Loc) != null && checkObstructions(bP,wK_Loc).equals(wK_Loc)) {
						return bP;
					}
				}
			}
		}
		else {
			for (Piece wP : whitePieces) {
				if (!wP.getMoveSet().contains(bK_Loc)) {
					continue;
				}
				else {
					if (checkObstructions(wP,bK_Loc) != null && checkObstructions(wP,bK_Loc).equals(bK_Loc)) {
						return wP;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param p Piece in question to move
	 * @param destSquare String Coordinate to move to
	 * @return boolean Whether after moving the piece, check can be blocked
	 */
	
	public static boolean blocked(Piece p, String destSquare) {
		Piece att = attacker();
		String currSquare = p.currentSquare;
		boolean possible = canMove(p,destSquare);
		if (whiteTurn) {
			if (possible) {
				p.currentSquare = destSquare;
			}
			//String s = "";
			if (checkObstructions(att,bK_Loc) != null && !checkObstructions(att,bK_Loc).equals(bK_Loc)) {
				p.currentSquare = currSquare;
				return true;
			}
		}
		else {
			if (possible) {
				p.currentSquare = destSquare;
			}
			//String s = "";
			if (checkObstructions(att,bK_Loc) != null && !checkObstructions(att,bK_Loc).equals(bK_Loc)) {
				p.currentSquare = currSquare;
				return true;
			}
		}
		return false;
	}
	/**
	* The main method runs the chess game and also takes care of pawn promotions and castling
	* 
	*/
	
	
	public static void main (String []args) {
		
		//INITIAL SET UP LOGIC; LISTS OF PIECES AND INITIAL BOARD STATE
		setUpLists();
		drawBoard();
		String lastMove = "";
		
		//GAME STARTUP LOOP BELOW
		
		while(true){
			
			System.out.println(); 

			enPass = false;
			//TURN WRITING
			if(whiteTurn) {
				System.out.println("White's Turn!");
			}else {
				System.out.println("Black's Turn!");
			}
			
			/*
			for (Piece wP : whitePieces) {
				System.out.print(wP.currentSquare+" ");
				
			}
			System.out.println();
			for (Piece bP : blackPieces) {
				System.out.print(bP.currentSquare+" ");
				
			}
			*/
				
			//USER-INPUTTED MOVE PARSING
			System.out.println("Input Move:");
			Scanner s = new Scanner(System.in);
			String line = s.nextLine();
			
			//INITIALIZATION OF 1. CURRENT SQUARE, 2. DESTINATION SQUARE, 3. PROMOTION STRING VALUE (IF APPLICABLE ELSE NULL)
			String currSquare = "";
			String destSquare = "";
			char promo = (char) 0;
			
			
			//RESIGN LOGIC BELOW
				if(line.equals("resign")) {
					if(whiteTurn) {
						System.out.println();
						System.out.println("Black wins");
						System.exit(0);
					}
					if(!whiteTurn) {
						System.out.println();
						System.out.println("White wins");
						System.exit(0);
					}
				}
				
				//CASTLING LOGIC BELOW
				if(line.equals("e1 g1") && (retrievePiece("e1") instanceof King)) {	//king side castle for white
					Piece p1 = retrievePiece("e1");
					Piece p2 = retrievePiece("h1");
					if(((King) p1).hasMoved == false && ((Rook) p2).hasMoved == false) {
						p1.currentSquare = "g1";
						p2.currentSquare = "f1";
					}
					System.out.println();
					System.out.println("White's move: " + line);
					System.out.println();
					drawBoard();
					System.out.println();
					continue;
				}
				if(line.equals("e1 c1") && (retrievePiece("e1") instanceof King)) {	//queen side castle for white
					Piece p1 = retrievePiece("e1");
					Piece p2 = retrievePiece("a1");
					if(((King) p1).hasMoved == false && ((Rook) p2).hasMoved == false) {
						p1.currentSquare = "c1";
						p2.currentSquare = "d1";
					}
					System.out.println();
					System.out.println("White's move: " + line);
					System.out.println();
					drawBoard();
					System.out.println();
					continue;
				}
				if(line.equals("e8 g8") && (retrievePiece("e8") instanceof King)) {	//king side castle for black
					Piece p1 = retrievePiece("e8");
					Piece p2 = retrievePiece("h8");
					if(((King) p1).hasMoved == false && ((Rook) p2).hasMoved == false) {
						p1.currentSquare = "g8";
						p2.currentSquare = "f8";
					}
					System.out.println();
					System.out.println("Black's move: " + line);
					System.out.println();
					drawBoard();
					System.out.println();
					continue;
				}
				if(line.equals("e8 c8") && (retrievePiece("e8") instanceof King)) {	//queen side castle for black
					Piece p1 = retrievePiece("e8");
					Piece p2 = retrievePiece("a8");
					if(((King) p1).hasMoved == false && ((Rook) p2).hasMoved == false) {
						p1.currentSquare = "c8";
						p2.currentSquare = "d8";
					}
					System.out.println();
					System.out.println("Black's move: " + line);
					System.out.println();
					drawBoard();
					System.out.println();
					continue;
				}
				
				
				//DRAW LOGIC BELOW
				if(line.length() == 11 && (line.charAt(6) == 'd' || line.charAt(6) == 'D')){
					//System.out.println("Does opposing player accept draw?");
					line = s.nextLine();
					//line = line.substring(6);
					if(line.equals("draw")) {
						System.out.println();
						System.out.println("draw");
						System.exit(0);
					}else {
						continue;
					}
				}
				
				//ASSIGNMENT OF ACTUAL 1. CURRENT SQUARE AND 2. DESTINATION SQUARE VALUES FOR VARIABLES
				if(line.length() == 5) {
					currSquare = line.substring(0,2);
					destSquare = line.substring(3,5);
				}
				
				if(line.length() == 7) {
					currSquare = line.substring(0,2);
					destSquare = line.substring(3,5);
					promo = line.charAt(6);
					//System.out.println(promo);
				}
				
				
				
				
				Piece p = retrievePiece(currSquare);//WHAT PIECE DO WE HAVE AT THE CURRENT SQUARE??
				//System.out.println(p.ID);//SHOW ID OF PIECE
				boolean pin = isPinned(p);
				if (pin) {
					System.out.println();
					System.out.println("Illegal move, try again");
					continue;
				}
				
				ArrayList<String> enPassants = new ArrayList<String>();
				Piece prev = null;
				//System.out.println("LAST MOVE WAS: "+lastMove);
				if (!lastMove.equals("")) {
					prev = retrievePiece(lastMove.substring(2));
					//System.out.println("LAST PIECE WAS: "+prev.ID);
				}
				if (prev instanceof Pawn && p instanceof Pawn) {
					if (prev.ID.charAt(0) != p.ID.charAt(0)) {
						if (whiteTurn) {
							//System.out.println((char)(lastMove.charAt(0)+1));
							//System.out.println(currSquare.charAt(0));
							if ((lastMove.charAt(1)+""+lastMove.charAt(3)).equals("75") && (char)(lastMove.charAt(0)+1) == currSquare.charAt(0)) {
								enPassants.add(lastMove.charAt(0)+""+"6");
							}
							else if ((lastMove.charAt(1)+""+lastMove.charAt(3)).equals("75") && (char)(lastMove.charAt(0)-1) == currSquare.charAt(0)) {
								//System.out.println("for teh boyz");
								enPassants.add(lastMove.charAt(0)+""+"6");
							}
						}
						else {
							//System.out.println((char)(lastMove.charAt(0)+1));
							//System.out.println(currSquare.charAt(0));
							if ((lastMove.charAt(1)+""+lastMove.charAt(3)).equals("24") && (char)(lastMove.charAt(0)+1) == currSquare.charAt(0)) {
								enPassants.add(lastMove.charAt(0)+""+"3");
							}
							else if ((lastMove.charAt(1)+""+lastMove.charAt(3)).equals("24") && (char)(lastMove.charAt(0)-1) == currSquare.charAt(0)) {
								enPassants.add(lastMove.charAt(0)+""+"3");
							}
						}
					}
				}
				//System.out.println("{");
				//for (String ep : enPassants) {
				//	System.out.print(ep+" ");
				//}
				//System.out.println("}");
				
				if (!enPassants.isEmpty()) {
					enPass = true;
				}
				lastMove = currSquare+destSquare;
				
				
				//CANNOT MOVE ANOTHER PIECE WHEN IN CHECK!!!
				
				/*
				if(p.ID.charAt(1) != 'K' && inCheck == true) {
					if (blocked(p,destSquare)) {
						System.out.println();
						System.out.println("Illegal move, try again");
						continue;
					}
				}
				*/
				
				
				
				
				//CANNOT MOVE KING WHEN IN CHECK, INTO CHECK!!!
				if (p.ID.charAt(1) == 'K' && inCheck == true){
					ArrayList<String> restricted = adversarials();
					if (restricted.contains(destSquare)) {
						System.out.println();
						System.out.println("Illegal move, try again");
						continue;
					}
				}
				
				
				
				
				//WHAT MOVES ARE POSSIBLE FOR OUR FOUND PIECE, p??
				
				ArrayList <String> arr = p.getMoveSet();
				
				if (!enPassants.isEmpty()) {
					for (String ep : enPassants) {
						arr.add(ep);
					}
				}
				
				//System.out.print("{ ");
				///for (String a : arr) {
				//	System.out.print(a+" ");
				//}
				//System.out.println("}");
				
				
				//IF THE FOUND PIECE CANNOT MOVE IN THE STATED WAY, PRINT ILLEGAL MOVE NOTICE, AND CONTINUE LOOP TO TRY AGAIN
				if(!arr.contains(destSquare)) {
					System.out.println();
					System.out.println("Illegal move, try again");
					continue;
				}
				
				//MAKE MOVEEEEE FOR PIECE HERE
				//System.out.println("Sausage");
				boolean f = makeMove(p, destSquare);
				//System.out.println("Bacon");
				if (!f) continue;
				
				
				//PROMOTION LOGIC
				if (p instanceof Pawn) {
					
					if(line.charAt(4) == '8') { //white pawn going to be promoted
						if(p.ID == "wp") {
							switch(promo) {
								case 'N':
									Piece wKnight = new Knight();
									wKnight.currentSquare = destSquare;
									wKnight.ID = "wN";
									whitePieces.add(wKnight);
									break;
								case 'B':
									Piece wBishop = new Bishop();
									wBishop.currentSquare = destSquare;
									wBishop.ID = "wB";
									whitePieces.add(wBishop);
									break;
								case 'R':
									Piece wRook = new Rook();
									wRook.currentSquare = destSquare;
									wRook.ID = "wR";
									whitePieces.add(wRook);
									break;
								default:
									Piece wQueen2 = new Queen();
									wQueen2.currentSquare = destSquare;
									wQueen2.ID = "wQ";
									whitePieces.add(wQueen2);
									break;
							}
						}
						whitePieces.remove(p);
					}
					else if(line.charAt(4) == '1') { //black pawn going to be promoted
						if(p.ID == "bp") {
							switch(promo) {
								case 'N':
									Piece bKnight = new Knight();
									bKnight.currentSquare = destSquare;
									bKnight.ID = "bN";
									blackPieces.add(bKnight);
									break;
								case 'K':
									Piece bKing2 = new King();
									bKing2.currentSquare = destSquare;
									bKing2.ID = "bK";
									blackPieces.add(bKing2);
									break;
								case 'B':
									Piece bBishop = new Bishop();
									bBishop.currentSquare = destSquare;
									bBishop.ID = "bB";
									blackPieces.add(bBishop);
									break;
								case 'R':
									Piece bRook = new Rook();
									bRook.currentSquare = destSquare;
									bRook.ID = "bR";
									blackPieces.add(bRook);
									break;
								default:
									Piece bQueen2 = new Queen();
									bQueen2.currentSquare = destSquare;
									bQueen2.ID = "bQ";
									blackPieces.add(bQueen2);
									break;
							}
						}
						blackPieces.remove(p);
					}
				}
				
				
				//MOVE WRITING
				if(whiteTurn) {
					System.out.println();
					System.out.println("White's move: " + currSquare + " " + destSquare + " " + promo);
					System.out.println();
				}else {
					System.out.println();
					System.out.println("Black's move: " + currSquare + " " + destSquare + " " + promo);
					System.out.println();
				}
				
				//UPDATED BOARD DRAWING
				drawBoard(); 
				
				//1. DO checkmate with blocking as the option
				ArrayList<ArrayList<String>> paths = getPathsToKing();
				if (whiteTurn) {
					boolean checkmate = false;
					if (paths.size() == 2) {
						if (!kingCanEscape()) {
							checkmate = true;
						}
					}
					else if (paths.size() == 1){
						//System.out.println("CHOCOLATE");
						if (!kingCanEscape() && !canCoverKing()) {
							checkmate = true;
						}
					}
					if (checkmate) {
						System.out.println();
						System.out.println("Checkmate");
						System.out.println("White wins");
						System.exit(0);
					}
				}
				else {
					boolean checkmate = false;
					if (paths.size() == 2) {
						if (!kingCanEscape()) {
							checkmate = true;
						}
					}
					else if (paths.size() == 1){
						//System.out.println("CHOCOLATE");
						if (!kingCanEscape() && !canCoverKing()) {
							checkmate = true;
						}
					}
					if (checkmate) {
						System.out.println();
						System.out.println("Checkmate");
						System.out.println("Black wins");
						System.exit(0);
					}
				}
				areLegalMoves();
				if (staleMate) {
					System.out.println();
					System.out.println("Stalemate");
					System.out.println("draw");

				}
				//2. DO checkmate with King moving as the option
				/*
				if (whiteTurn) {
					Piece bKing = retrievePiece(bK_Loc);
					ArrayList<String> kingSquares = bKing.getMoveSet();
					ArrayList<String> actualMoves = new ArrayList<String>();
					
					for (String sq : kingSquares) {
						if (checkObstructions(bKing,sq) == null){
							actualMoves.add(sq);
						}
					}
					for (String sA : actualMoves) {
						System.out.print(sA+" ");
					}
					ArrayList<String> restricted = attacks();
					if (actualMoves.isEmpty() && restricted.contains(bK_Loc)) {
						System.out.println("Checkmate");
						System.out.println("White wins");
						System.exit(0);
					}
					for (String square : actualMoves) {
						if (!restricted.contains(square)) {
							checkMate = false;
							break;
						}
						checkMate = true;
					}
					if (checkMate) {
						System.out.println("Checkmate");
						System.out.println("White wins");
						System.exit(0);
					}
				}
				else {
					ArrayList<String> kingSquares = retrievePiece(wK_Loc).getMoveSet();
					ArrayList<String> restricted = attacks();
					for (String square : kingSquares) {
						if (!restricted.contains(square)) {
							checkMate = false;
							break;
						}
						checkMate = true;
					}
					if (checkMate) {
						System.out.println("Checkmate");
						System.out.println("Black wins");
						System.exit(0);
					}
				}
				*/
				inCheck = detectCheck(p);
				whiteTurn = !whiteTurn;
				
			
		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}		
	}
}




package strategies;

import java.util.ArrayList;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.Controller;

public class MinimaxStrategy extends Strategy{

	public int depth = 5;
	

	public MinimaxStrategy(Controller controller){
		super(controller);
	}

	/**
	 * Does a "move" on a Board. Which "move" is done depends on Minimax strategy
	 * @param board		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Board move(Board board){
		Board successorBoard = minimax(board, 0, depth);
		
		/* Execute the command */
		Command command = getCommand(board, successorBoard);
		commandManager.executeCommand(command);
		
		view.updateView();
		return board;
	}
	
	/**
	 * Recursive function. Uses Minimax strategy to determine the next move
	 * @param board			The Board to do a "move" on.
	 * @param currLevel		The current depth we've searched to in game tree (where 0 corresponds to current state of Board)
	 * @param maxDepth		The number of levels deep we should search the game tree
	 * @return				The Board after the "move" is performed.
	 */
	public Board minimax(Board board, int currLevel, int maxDepth){
		if (board.gameEnded || (currLevel == maxDepth))
			return board;
		Utility utility;
		
		ArrayList<Board> successorBoards = getAdjacentBoards(board);
		Board bestBoard = null;
		
		if (board.playerTurn == Color.BLACK){
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < successorBoards.size(); i++){
				Board successor = successorBoards.get(i);
				Board lookaheadBoard = minimax(successor, currLevel + 1, maxDepth);
				utility = new Utility(lookaheadBoard);
				utility.utilityCorners();
				if (utility.value > max){
					max = utility.value;
					bestBoard = successor;
				}
			}
		}
		else{
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < successorBoards.size(); i++){
				Board successor = successorBoards.get(i);
				Board lookaheadBoard = minimax(successor, currLevel + 1, maxDepth);
				utility = new Utility(lookaheadBoard);
				utility.utilityCorners();
				if (utility.value < min){
					min = utility.value;
					bestBoard = successor;
				}
			}
		}
		return bestBoard;
	}
}
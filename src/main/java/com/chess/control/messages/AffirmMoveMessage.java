package com.chess.control.messages;

import com.chess.model.Identifier;

/**
 * A message sent out to represent that a move has been validated and executed.
 * This message is sent by the server.
 */
public class AffirmMoveMessage extends MovePieceMessage {

	/**
	 * Constructor for affirm move message.
	 * @param fromRow The row from which the move is made.
	 * @param fromCol The column from which the move is made.
	 * @param toRow The row to which the move is made.
	 * @param toCol The column to which the move is made.
	 * @param isElimination Identifies whether the move implies the elimination of another piece.
	 * @param pieceType The identifier for the type of the piece performing the move.
	 */
	public AffirmMoveMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination, Identifier pieceType) {
		super(fromRow, fromCol, toRow, toCol, isElimination, pieceType);
	}
    
}

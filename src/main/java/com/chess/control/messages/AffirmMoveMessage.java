package com.chess.control.messages;

import com.chess.model.Identifier;

public class AffirmMoveMessage extends MovePieceMessage {

	public AffirmMoveMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination, Identifier pieceType) {
		super(fromRow, fromCol, toRow, toCol, isElimination, pieceType);
	}
    
}

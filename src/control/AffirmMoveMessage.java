package control;

public class AffirmMoveMessage extends MovePieceMessage {

	public AffirmMoveMessage(int fromRow, int fromCol, int toRow, int toCol, boolean isElimination) {
		super(fromRow, fromCol, toRow, toCol, isElimination);
	}
    
}

package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BoardGridPanel extends JPanel {
    private BoardCell[][] _board;
    private int _size;
    private ChessView _view;

    private BoardCell _selectedCell;
    private ArrayList<BoardCell> _highlightedCells;

    public BoardGridPanel(ChessView view, int size) {
        super(new GridLayout(size, size));
        _board = new BoardCell[size][size];
        _size = size;
        _view = view;
        _highlightedCells = new ArrayList<BoardCell>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                BoardCell button = new BoardCell(row, col);

                button.setMinimumSize(ChessView.CELL_MIN_SIZE);
                button.setPreferredSize(ChessView.CELL_IDEAL_SIZE);

                this.add(button);

                button.addActionListener((e) -> {
                    handleClick(button);
                });

                Color color = row % 2 == col % 2 ? ChessView.PRIMARY_COLOR : ChessView.SECONDARY_COLOR;

                // Set the background color of the button, balck or white
                button.setBackground(color);

                button.setHoverBackgroundColor(color);
                button.setPressedBackgroundColor(color);

                _board[row][col] = button;
            }
        }
    }

    private void handleClick(BoardCell boardCell) {
        ChessModel model = _view.getModel();
        Board board = model.getBoard();

        // If the cell was highlighted, move the selected piece to the cell
        if (_highlightedCells.contains(boardCell)) {
            Piece piece = board.getCell(_selectedCell.getRow(), _selectedCell.getCol()).getPiece();

            piece.move(board.getCell(boardCell.getRow(), boardCell.getCol()));

            Team otherTeam = model.getOtherTeam(model.getCurrentTeam());

            // Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.
            boolean halfMove = piece.getPieceType() != PieceType.PAWN && !boardCell.isElimination();

            model.registerMove(halfMove);

            _view.updateModel();
            
            otherTeam.clearEnPassant();
        }

        if (_selectedCell != null) {
            _selectedCell.unhighlight();
        }

        for (BoardCell cell : _highlightedCells) {
            cell.unhighlight();
            cell.setElimination(false);
        }

        _highlightedCells.clear();

        Piece piece = model.getBoard().getCell(boardCell.getRow(), boardCell.getCol()).getPiece();

        if (piece == null) {
            return;
        }

        if (piece.getTeam() != model.getCurrentTeam()) {
            return;
        }

        _selectedCell = boardCell;

        _selectedCell.highlight(ChessView.HIGHLIGHT_COLOR_PIECE);

        Iterator<Move> moves = piece.getPossibleMoves();
        while (moves.hasNext()) {
            Move move = moves.next();

            Cell cell = move.getCell();

            BoardCell possibleMove = _board[cell.getRow()][cell.getCol()];

            possibleMove.highlight(move.isEliminatable() ? ChessView.HIGHLIGHT_COLOR_ATTACK : ChessView.HIGHLIGHT_COLOR_MOVE);
            possibleMove.setElimination(move.isEliminatable());

            _highlightedCells.add(possibleMove);
        }
    }

    public void updateModel(ChessModel m) {
        model.Board b= m.getBoard();

        for(int row=0;row<_size;row++){
            for(int col=0;col<_size;col++){
                Piece p = b.getCell(row,col).getPiece();
                if(p == null){
                    _board[row][col].setIcon(null);
                }
                else{
                    _board[row][col].setIcon(new ImageIcon("res/"+p.getPieceType().getFilePrefix()+p.getTeam().getFileSuffix()+".png"));
                }
            }
        }
    }
}

package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLoop {
    static int ROW = 6;
    static int COL = 7;
    private static int WINNING = 100;
    private static int THREE_LINE = 5;
    private static int TWO_LINE = 2;
    private static int DEC = 60;
    Player player;
    Player ai;
    Piece[][] board = new Piece[ROW][COL];
    GameLoop(){
        player = new Player(Role.PLAYER);
        ai = new Player(Role.AI);
        initiateBoard(board);
    }
    private void initiateBoard(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = Piece.GAP;
        }
    }
    public boolean isValidLoc(Piece[][] board, int col){
        return board[ROW -1][col] == Piece.GAP;
    }
    public List<Integer> getValidLoc(Piece[][] board){
        List<Integer> validLoc = new ArrayList<>();
        for (int i = 0; i < COL; i++) {
            if (isValidLoc(board, i))
                validLoc.add(i);
        }
        return validLoc;
    } //return all the valid locations a piece can be placed at
    public int passToNextGap(Piece[][] board, int col){
        for (int i = 0; i < ROW; i++) {
            if (board[i][col] == Piece.GAP)
                return i;
        }
        return -1;
    } //fall
    public void dropPiece(Piece[][] board, int row, int col, Piece piece){
        board[row][col] = piece;
    }
    public void dropPiece(Piece[][] board, int col, Piece piece){
        Piece[][] aux = board;
        int row = passToNextGap(board, col);
        dropPiece(aux, row, col, piece);
    } //drop a piece into a column
    public int checkWinningMove(Piece[][] board, Piece piece){
        if (piece == Piece.RED)
            player.setScore(0);
        else if (piece == Piece.YELLOW)
            ai.setScore(0);
        //if horizontal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 0; i < ROW; i++) {
                if (
                        board[i][j] == piece
                                && board[i][j+1] == piece
                                && board[i][j+2] == piece
                                && board[i][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player.incrementScore();
                    } else {
                        ai.incrementScore();
                    }
                }
            }
        }
        //vertical
        for (int j = 0; j < COL; j++) {
            for (int i = 0; i < ROW-3; i++) {
                if (
                        board[i][j] == piece
                                && board[i+1][j] == piece
                                && board[i+2][j] == piece
                                && board[i+3][j] == piece
                ){
                    if (piece == Piece.RED){
                        player.incrementScore();
                    } else {
                        ai.incrementScore();
                    }
                }
            }
        }
        //diagonal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 0; i < ROW-3; i++) {
                if (
                        board[i][j] == piece
                                && board[i+1][j+1] == piece
                                && board[i+2][j+2] == piece
                                && board[i+3][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player.incrementScore();
                    } else {
                        ai.incrementScore();
                    }
                }
            }
        }
        //reversed diagonal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 3; i < ROW; i++) {
                if (
                        board[i][j] == piece
                                && board[i-1][j+1] == piece
                                && board[i-2][j+2] == piece
                                && board[i-3][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player.incrementScore();
                    } else {
                        ai.incrementScore();
                    }
                }
            }
        }
        if (piece == Piece.RED){
            return player.getScore();
        } else {
            return ai.getScore();
        }
    } //every move check for scores
    public int countWinningMove(Piece[][] board, Piece piece){
        int player = 0;
        int ai = 0;
        //if horizontal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 0; i < ROW; i++) {
                if (
                        board[i][j] == piece
                                && board[i][j+1] == piece
                                && board[i][j+2] == piece
                                && board[i][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player++;
                    } else {
                        ai++;
                    }
                }
            }
        }
        //vertical
        for (int j = 0; j < COL; j++) {
            for (int i = 0; i < ROW-3; i++) {
                if (
                        board[i][j] == piece
                                && board[i+1][j] == piece
                                && board[i+2][j] == piece
                                && board[i+3][j] == piece
                ){
                    if (piece == Piece.RED){
                        player++;
                    } else {
                        ai++;
                    }
                }
            }
        }
        //diagonal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 0; i < ROW-3; i++) {
                if (
                        board[i][j] == piece
                                && board[i+1][j+1] == piece
                                && board[i+2][j+2] == piece
                                && board[i+3][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player++;
                    } else {
                        ai++;
                    }
                }
            }
        }
        //reversed diagonal
        for (int j = 0; j < COL-3; j++) {
            for (int i = 3; i < ROW; i++) {
                if (
                        board[i][j] == piece
                                && board[i-1][j+1] == piece
                                && board[i-2][j+2] == piece
                                && board[i-3][j+3] == piece
                ){
                    if (piece == Piece.RED){
                        player++;
                    } else {
                        ai++;
                    }
                }
            }
        }
        if (piece == Piece.RED){
            return player;
        } else {
            return ai;
        }
    } // just count the score for each player --> no update
    public int calcScore(Piece[][] tempBoard, Piece piece) {
        int heuristicScore = 0;
        //center
        Piece[] cLine = new Piece[ROW];
        for (int i = 0; i < ROW; i++) {
            cLine[i] = board[i][COL/2];
        }
        heuristicScore += count(cLine, piece) * 3;

        //horizontal
        for (int i = 0; i < ROW; i++) {
            Piece[] hLine = new Piece[COL];
            for (int j = 0; j < COL; j++) {
                hLine[j] = tempBoard[i][j];
            }
            for (int j = 0; j < COL-3; j++) {
                Piece[] window = Arrays.copyOfRange(hLine, j, j + 4);
                heuristicScore += evaluateWindowScore(window, piece);
            }
        }
        //vertical
        for (int i = 0; i < COL; i++) {
            Piece[] vLine = new Piece[COL];
            for (int k = 0; k < ROW; k++) {
                vLine[k] = tempBoard[k][i];
            }
            for (int j = 0; j < ROW-3; j++) {
                Piece[] window = Arrays.copyOfRange(vLine, j, j + 4);
                heuristicScore += evaluateWindowScore(window, piece);
            }
        }
        //diagonal
        for (int i = 0; i < ROW-3; i++) {
            for (int j = 0; j < COL-3; j++) {
                Piece[] window = new Piece[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = tempBoard[i+k][j+k];
                }
                heuristicScore += evaluateWindowScore(window, piece);
            }
        }
        //reversed diagonal
        for (int i = 0; i < ROW-3; i++) {
            for (int j = 0; j < COL-3; j++) {
                Piece[] window = new Piece[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = tempBoard[i+3-k][j+k];
                }
                heuristicScore += evaluateWindowScore(window, piece);
            }
        }
        return heuristicScore;
    } //calculate heuristic function
    private int evaluateWindowScore(Piece[] window, Piece piece) {
        int heuristicScore = 0;
        Piece opponent = Piece.RED;
        if (piece == opponent){
            opponent = Piece.YELLOW;
        }
        if (count(window, piece) == 4){
            heuristicScore += WINNING;
        } else if (count(window, piece) == 3 && count(window, Piece.GAP) == 1 ){
            heuristicScore += THREE_LINE;
        } else if (count(window, piece) == 2 && count(window, Piece.GAP) == 2){
            heuristicScore += TWO_LINE;
        }
        if (count(window, opponent) == 3 && count(window, Piece.GAP) == 1){
            heuristicScore -= DEC;
        } else if (count(window, opponent) == 4){
            heuristicScore -= WINNING;
        }  else if (count(window, opponent) == 2 && count(window, piece) == 2){
            heuristicScore -= TWO_LINE;
        }
        return heuristicScore;
    } //helper to calculate score
    private int count(Piece[] window, Piece piece) {
        int count = 0;
        for (Piece p: window) {
            if (p == piece)
                count++;
        }
        return count;
    } //count the occurances of a piece within fixed window
    public void changeMemLoc(Piece[][] tempBoard, Piece[][] board){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
    }
    public void printBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int p;
                if (board[board.length - i-1][j] == Piece.RED)
                    p = 1;
                else if(board[board.length - i-1][j] == Piece.YELLOW)
                    p = 2;
                else
                    p = 0;
                System.out.print(p + ", ");
            }
            System.out.println();
        }
    }
    public int getValidMoves(Piece[][] board){
        int moves = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Piece.GAP)
                    moves++;
            }
        }
        return moves;
    }
}

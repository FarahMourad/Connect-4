package main;

import java.util.List;

public class Facade {
    Node root;
    Solution solution;
    Algorithm algorithm;
    GameLoop gameLoop;
    Piece[][] board;
    int k;

    public Facade(){
        solution = new Solution();
        gameLoop = solution.getGameLoop();
        board = solution.getBoard();
    }
    public void dropPlayerPiece(int col){
        if (getValidLoc().isEmpty()){
            return;
        }
        gameLoop.dropPiece(board, col, Piece.RED);
        gameLoop.checkWinningMove(board, Piece.RED);
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public int[] dropAiPiece(){
        root = new Node();
        int col;
        if (algorithm == Algorithm.MIN_MAX)
            col = solution.minimax(board, root, k,true).get(0);
        else
            col = solution.alphaBeta(board, root, k, Integer.MIN_VALUE, Integer.MAX_VALUE,true).get(0);
        int row = gameLoop.passToNextGap(board, col);
        gameLoop.dropPiece(board, row, col, Piece.YELLOW);
        gameLoop.checkWinningMove(board, Piece.YELLOW);
        return new int[]{row, col};
    }
    public int getPlayerScore(){
        return solution.getPlayerScore();
    }
    public int getAiScore(){
        return solution.getAiScore();
    }
    public List<Integer> getValidLoc(){
        return gameLoop.getValidLoc(board);
    }
    public void printTree(){
        root.printTree();
    }
    public void printBoard(){
        gameLoop.printBoard();
    }
    public void setK(String depth){
        this.k = Integer.parseInt(depth);
    }
}

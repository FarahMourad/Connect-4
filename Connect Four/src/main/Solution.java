package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.GameLoop.COL;
import static main.GameLoop.ROW;


public class Solution {
    private GameLoop gameLoop;
    Solution(){
        gameLoop = new GameLoop();
    }
    public List<Integer> minimax(Piece[][] board, Node node, int depth, boolean maximizerPlayer){
        List<Integer> validLoc = gameLoop.getValidLoc(board);
        Random random = new Random();
        int value;
        int bestCol;
        List<Integer> output = new ArrayList<>();
        if (depth > gameLoop.getValidMoves(board)){
            depth = gameLoop.getValidMoves(board);
        }
        if (depth == 0 || isTerminalNode(board)){
            output.add(null);
            output.add(gameLoop.calcScore(board, Piece.YELLOW));
            return output;
        }
        if (maximizerPlayer){
            value = Integer.MIN_VALUE;
            bestCol = validLoc.get(random.nextInt(validLoc.size()));
            for (int col: validLoc) {
                Piece[][] tempBoard = new Piece[ROW][COL];
                gameLoop.changeMemLoc(tempBoard, board);
                Node child = new Node();
                child.setCol(col);
                gameLoop.dropPiece(tempBoard, col, Piece.YELLOW);
                int temp = minimax(tempBoard, child, depth -1, false).get(1);
                child.setValue(temp);
                node.addChild(child);
                if (value < temp ){
                    value = temp;
                    bestCol = col;
                }
            }
        }
        else {
            value = Integer.MAX_VALUE;
            bestCol = validLoc.get(random.nextInt(validLoc.size()));
            for (int col: validLoc) {
                Piece[][] tempBoard = new Piece[ROW][COL];
                gameLoop.changeMemLoc(tempBoard, board);
                Node child = new Node();
                child.setCol(col);
                gameLoop.dropPiece(tempBoard, col, Piece.RED);
                int temp = minimax(tempBoard, child, depth -1, true).get(1);
                child.setValue(temp);
                node.addChild(child);
                if (value > temp ){
                    value = temp;
                    bestCol = col;
                }
            }
        }
        output.add(0, bestCol);
        output.add(1, value);
        return output;
    }
    public List<Integer> alphaBeta(Piece[][] board, Node node, int depth, int alpha, int beta, boolean maximizerPlayer){
        List<Integer> validLoc = gameLoop.getValidLoc(board);
        Random random = new Random();
        int value;
        int bestCol;
        List<Integer> output = new ArrayList<>();
        if (depth > gameLoop.getValidMoves(board)){
            depth = gameLoop.getValidMoves(board);
        }
        if (depth == 0 || isTerminalNode(board)){
            output.add(null);
            output.add(gameLoop.calcScore(board, Piece.YELLOW));
            return output;
        }
        if (maximizerPlayer){
            value = Integer.MIN_VALUE;
            bestCol = validLoc.get(random.nextInt(validLoc.size()));
            for (int col: validLoc) {
                Piece[][] tempBoard = new Piece[ROW][COL];
                gameLoop.changeMemLoc(tempBoard, board);
                Node child = new Node();
                child.setCol(col);
                gameLoop.dropPiece(tempBoard, col, Piece.YELLOW);
                int temp = alphaBeta(tempBoard, child, depth -1, alpha, beta, false).get(1);
                child.setValue(temp);
                node.addChild(child);
                if (value < temp ){
                    value = temp;
                    bestCol = col;

                }
                alpha = Math.max(alpha, value);
                if (alpha >= beta)
                    break;
            }
        } else {
            value = Integer.MAX_VALUE;
            bestCol = validLoc.get(random.nextInt(validLoc.size()));
            for (int col: validLoc) {
                Piece[][] tempBoard = new Piece[ROW][COL];
                gameLoop.changeMemLoc(tempBoard, board);
                Node child = new Node();
                child.setCol(col);
                gameLoop.dropPiece(tempBoard, col, Piece.RED);
                int temp = alphaBeta(tempBoard, child, depth -1, alpha, beta, true).get(1);
                child.setValue(temp);
                node.addChild(child);
                if (value > temp ){
                    value = temp;
                    bestCol = col;
                }
                beta = Math.min(beta, value);
                if (alpha >= beta)
                    break;
            }
        }
        output.add(0, bestCol);
        output.add(1, value);
        return output;
    }
    //alpha/beta -inf, +inf
    private boolean isTerminalNode(Piece[][] board) {
        return gameLoop.getValidLoc(board).size() == 0;
    }
    public GameLoop getGameLoop() {
        return gameLoop;
    }
    public int getAiScore() {
        return gameLoop.ai.getScore();
    }
    public int getPlayerScore() {
        return gameLoop.player.getScore();
    }
    public Piece[][] getBoard() {
        return gameLoop.board;
    }
}

package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Facade f = new Facade();
        f.setAlgorithm(Algorithm.ALPHA_BETA);
        f.setK("10");
        Scanner scanner = new Scanner(System.in);
        while (!f.getValidLoc().isEmpty()){
            System.out.println("Enter the column ");
            int column = scanner.nextInt();
            f.dropPlayerPiece(column);
            f.dropAiPiece();
            f.printBoard();
            System.out.println(f.getAiScore());
            System.out.println(f.getPlayerScore());
//            f.printTree();
        }
    }
}

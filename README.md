# Connect-4 Game

Connect-4 is a classic two-player game that involves dropping colored discs into a grid. The objective of the game is to connect four of one's own discs of the same color vertically, horizontally, or diagonally. In this implementation, you can play against a computer opponent that uses the Minimax algorithm with or without alpha-beta pruning.

## Getting Started

To run the Connect-4 game, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Run the Main.java file.
4. The Connect-4 game GUI will appear.
5. Select the algorithm you want the computer opponent to use.
6. Play the game against the computer opponent.

## Game Description

This implementation of Connect-4 is played on a grid with a width of at least 7 and a length of at least 6. Players take turns dropping their colored discs into the grid from the top. The discs fall straight down and occupy the next available space within the column. The game continues until the board is full or one player connects four discs of their color. The winner is the player with the greater number of connected fours.

### Game GUI

The game features a graphical user interface (GUI) that allows you to play against a computer opponent in human vs. computer mode.

### Algorithms

The game features two options for the computer opponent's algorithm:

- Minimax without alpha-beta pruning
- Minimax with alpha-beta pruning

In each turn, the computer opponent will run the selected algorithm, and the minimax tree will be printed to the console in a readable format that is easy to trace.

### Heuristic Function

The heuristic function calculates the gain or loss that results from each move and gives an advantage to moves that are likely to result in a win. It takes into account factors such as whether a move results in a winning move, whether it creates three consecutive pieces with a fourth available position, and whether it creates two consecutive pieces with two available positions. Conversely, it takes credit from moves that are likely to result in a loss, such as those that lead to the opponent's win or that create three consecutive pieces with a fourth available position for the opponent.

### Minimax Algorithm

To save time, the available locations are checked to determine the depth of the algorithm. If the number of available locations is less than the input depth, the number of available locations is used instead. A Node class is created to store each move's evaluation value, column, and list of children, and the tree is filled during the recursive call of the two algorithms. Each parent is assigned to its children.

### Tree of Expanded Nodes

To keep track of every move, a Node class is created to store its evaluation value, column, and its list of children. The tree is filled during the recursive call of the two algorithms, and each parent is assigned to its children.

### Comparison between the two Algorithms  

The number of nodes expanded is less in the case of alpha-beta pruning compared to minimax without alpha-beta pruning. Time taken increases with K. However, in the case of alpha-beta pruning, it is less than without alpha-beta pruning, and the function is more stable.

## Data Structures Used

The following data structures are used in this implementation:

- Queue: Used in level order traversal to print the tree.
- ArrayList: Used to store available locations on the board.
- 2D Array: Used to represent the game board.

## Implementation Details

This implementation is written in Java and uses JavaFX for the GUI. The code is organized into several classes, including Board, Minimax, AlphaBeta, Node, and Main.

## Demos

You can view the demo of the game by clicking the following links:

- [Minimax](https://drive.google.com/file/d/13gI0aKmKoeNkvjrLakh7_fUxF5pbDZfB/preview) without alpha-beta pruning

- [Minimax with alpha-beta pruning](https://drive.google.com/file/d/1EYJIUUV209L2e8FpuTqX5OUAM1-tboP1/preview)

## Contributors
We would like to thank Dr. Marwan Torki for their guidance and support throughout the development of this project.

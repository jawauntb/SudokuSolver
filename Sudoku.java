// ===================================================================
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// ===================================================================


//@author Jawaun Brown
// ===================================================================
class Sudoku {
    // ===================================================================
    
    
    
    // ===============================================================
    public static void main (String[] args) {
        
        if (args.length != 1) {
            System.err.println("USAGE: java Sudoku <initial board filename>");
            System.exit(1);
        }
        
        String boardPath = args[0];
        
        // Initialize the board.  It is a 2-D board where each
        // position contains either its proper value or a 0 to
        // indicate a blank.
        int[][] board = new int[9][9];
        
        // Read the initial values into the board.
        readInitial(board, boardPath);
        System.out.println("Initial board:");
        printBoard(board);
        
        // Solve, if possible...
        boolean isSolved = solve(board);
        if (isSolved) {
            System.out.println("Solved board:");
            printBoard(board);
        } else {
            System.out.println("Couldn't solve it!");
        }
        
    } // main
    // ===============================================================
    
    
    
    // ===============================================================
    private static void readInitial (int[][] board, String pathname) {
        
        // Open the file with the initial board.
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pathname));
        } catch (IOException e) {
            System.err.println("ERROR: Could not open " + pathname);
        }
        
        // Read each line of the board.
        for (int x = 0; x < board.length; x += 1) {
            
            // Try to read a line.
            String line = null;
            boolean failedRead = false;
            try {
                line = br.readLine();
            } catch (IOException e) {
                failedRead = true;
            }
            if ((failedRead) ||
                (line == null) ||
                (line.length() == 0)) {
                System.err.println("ERROR: Failure reading line " + x);
                System.exit(1);
            }
            
            // Break the line into its pieces, one for each cell in
            // that row.
            String[] splitLine = line.split(" ");
            if (splitLine.length != 9) {
                System.err.println("ERROR: Malformed line" + x +
                                   ": " + line);
                System.exit(1);
            }
            
            // Convert the values for each cell into integers.
            for (int y = 0; y < board[0].length; y += 1) {
                try {
                    board[x][y] = Integer.parseInt(splitLine[y]);
                } catch (NumberFormatException e) {
                    System.err.println("ERROR: Could not convert " +
                                       " value at (" + x + ", " + y +
                                       ") to an int");
                    System.exit(1);
                }
                if ((board[x][y] < 0) || (board[x][y] > 9)) {
                    System.err.println("ERROR: Value at (" + x + ", " +
                                       y + ") = " + board[x][y] +
                                       " is out of bounds");
                    System.exit(1);
                }
            }
            
        }
        
    } // readInitial
    // ===============================================================
    
    
    
    // ===============================================================
    private static void printBoard (int[][] board) {
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        
    } // printBoard
    // ===============================================================
    
    
    
    // ===============================================================
    private static boolean solve (int[][] board) {
        
        BoardPosition xy = new BoardPosition(board);
        
        if (!xy.onBoard()) {
            return true;
        }
        
        for (int v = 1; v <= 9; v += 1) {
            
            xy.set(v);
            // System.out.println("Trying: " + xy);
            if (xy.isLegal() && solve(board)) {
                return true;
            }
            
        }
        
        xy.set(0);
        // System.out.println("Failing: " + xy);
        return false;
        
    } // solve
    // ===============================================================
    
    
    
    // ===================================================================
} // class Sudoku
// ===================================================================

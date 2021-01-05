package ca.TicTacToe.game;

import java.util.SortedSet;

/**
 * Stores and sets up variables in the game board and calculates a winner
 */
public class gameBoard {

    private int winner = 0;
    private int endGameCounter = 0;

    private int[][] board = new int[4][4];
    //0 = empty, 1 = x, 2 = o

    public gameBoard(){
        emptyBoard();
    }

    /**
     * Fills the 4 by 4 game board with 0's
     * (0 stand for empty)
     */
    public void emptyBoard(){
        for(int i =0; i<4; i++){
            for(int y =0; y<4; y++){
                board[i][y] = 0;
            }
        }
    }

    /**
     * Checks if board location has not been taken by a player.
     * @post returns true if its empty
     * @post returns false if its not empty
     * @param row
     * @param column
     * @return
     */
    public boolean slotAvailable(int row, int column){
        if(board[row][column] == 0){
            return true;
        }else{
            return false;
        }
    }

    public void resetCounter(){
        endGameCounter = 0;
    }

    public void resetWinner(){
        winner = 0;
    }

    public int getWinner(){
        return winner;
    }

    /**
     * Checks if player has a 4 in a row.
     * @post Checks for column, row, diagonal, and anti-diagonal win or a draw.
     * @param player
     * @param row
     * @param column
     */
    public void checkGameStatus(int player, int row, int column){
        endGameCounter++;

        //check column win
        for(int i =0; i< 4; i++){
            if(board[row][i] != player){
                break;
            }
            if(i == 3){
                winner = player;
            }
        }

        //check row win
        for(int i =0; i<4; i++){
            if(board[i][column] != player){
                break;
            }
            if(i == 3){
                winner = player;
            }
        }

        //check diagonal win
        if(row == column){
            for(int i=0; i<4; i++){
                if(board[i][i] != player){
                    break;
                }
                if(i == 3){
                    winner = player;
                }
            }
        }

        //check anti-diagonal win
        if((row+column) == 3){
            for(int i=0; i<4; i++){
                if(board[i][3-i] != player){
                    break;
                }
                if(i == 3){
                    winner = player;
                }
            }
        }

        //draw
        if(endGameCounter == 16 && winner == 0){
            winner = 3;
        }
    }

    /**
     * Updates internal game board
     * @param value
     * @param row
     * @param column
     */
    public void updateBoard(int value, int row, int column){
        board[row][column] = value;
    }

}

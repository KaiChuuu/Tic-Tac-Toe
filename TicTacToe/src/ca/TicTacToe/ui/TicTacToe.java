package ca.TicTacToe.ui;

import ca.TicTacToe.game.gameBoard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Creates UI for the game Tic Tac Toe.
 * Obtains user input for turns and updates UI features.
 */
public class TicTacToe extends Application {

    private GridPane gridpane;

    private int turn = 1; //x starts

    private Image blueX = new Image("file:img/x.png");
    private Image yellowO = new Image("file:img/o.png");
    private Image empty = new Image("file:img/blank.png");

    private Button endButton = new Button();
    private Label endLabel = new Label();
    private gameBoard board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        endButton.setVisible(false);
        endLabel.setVisible(false);

        board = new gameBoard();
        primaryStage.setTitle("TicTacToe");

        gridpane = new GridPane();
        gridpane.setHgap(20);
        gridpane.setVgap(20);

        gridpane.setAlignment(Pos.TOP_CENTER);

        initializeBoard();

        gridpane.addEventHandler(MouseEvent.MOUSE_CLICKED, new Clicker());

        //resets game
        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initializeBoard();
                board.emptyBoard();
                board.resetWinner();
                board.resetCounter();
                endButton.setVisible(false);
                endLabel.setVisible(false);
                turn = 1;
            }
        });


        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(gridpane, endButton, endLabel);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 700, 700);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * Fills Grid Pane with empty image tiles
     */
    public void initializeBoard(){
        for(int y=0;y<4;y++){
            for(int i=0;i<4;i++){
                ImageView tile = new ImageView(empty);
                gridpane.add(tile, y, i);
            }
        }
    }

    /**
     * Triggers from user click on Grid Pane.
     * @post Obtains which node was selected from Grid Pane
     * @post Obtains row and column of node
     * @post checks if slot on Grid Pane has not been taken by user
     * @post updates image of node.
     * @post checks if game has ended and will show end button and text.
     * @post catches clicks outside of Grid Pane with Null Pointer Exception.
     */
    class Clicker implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event){
            try{
                if(board.getWinner() == 0) {
                    //Obtain specific node from Grid Pane
                    Node clickNode = event.getPickResult().getIntersectedNode();

                    //Obtain column and row for specific node
                    int columnInd = gridpane.getColumnIndex(clickNode);
                    int rowInd = gridpane.getRowIndex(clickNode);

                    if (board.slotAvailable(rowInd, columnInd)) {

                        ImageView tile;
                        tile = new ImageView(blueX);

                        if (turn == 1) {
                            board.updateBoard(1, rowInd, columnInd);
                            board.checkGameStatus(1, rowInd, columnInd);
                            tile = new ImageView(blueX);
                            turn = 2;
                        } else {
                            board.updateBoard(2, rowInd, columnInd);
                            board.checkGameStatus(2, rowInd, columnInd);
                            tile = new ImageView(yellowO);
                            turn = 1;
                        }
                        gridpane.add(tile, columnInd, rowInd);

                    }
                    if(board.getWinner() != 0){
                        endButton.setText("New Game");
                        endButton.setStyle("-fx-font: 15 arial;");
                        endButton.setVisible(true);
                        if(board.getWinner() == 1){
                            endLabel.setText("Game over, player X has won.");
                        }
                        if(board.getWinner() == 2){
                            endLabel.setText("Game over, player O has won.");
                        }
                        if(board.getWinner() == 3){
                            endLabel.setText("Game over, game ended in a draw.");
                        }
                        endLabel.setStyle("-fx-font: 20 arial;");
                        endLabel.setVisible(true);
                    }
                }
            }catch(NullPointerException e){
            }
        }
    }

}

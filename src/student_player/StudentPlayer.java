package student_player;

import boardgame.Board;
import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutMove;
import tablut.TablutPlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {
    private Random rand = new Random(1848);
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260584769");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */

    @Override
    public Move chooseMove(TablutBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        MyTools.getSomething();

        // Is random the best you can do?
        Move myMove = boardState.getRandomMove();

        myMove=greedy_decision(boardState);

        if(boardState.getOpponentPieceCoordinates().size()<=3) {
            System.out.println("----------------------------------------im here");
            myMove = minimax_decision(boardState);
        }

        //----------------------my code------------------------


        //----------------------end of my code------------------

        // Return your move to be processed by the server.
        return myMove;
    }

    public Move greedy_decision(TablutBoardState boardState){
        Move myMove = boardState.getRandomMove();
        List<TablutMove> options = boardState.getAllLegalMoves();
        int opponent = boardState.getOpponent();
        int minNumberOfOpponentPieces = boardState.getNumberPlayerPieces(opponent);
        boolean moveCaptures = false;

        for (TablutMove move : options) {
            // To evaluate a move, clone the boardState so that we can do modifications on
            // it.
            TablutBoardState cloneBS = (TablutBoardState) boardState.clone();

            // Process that move, as if we actually made it happen.
            cloneBS.processMove(move);

            // Check how many opponent pieces there are now, maybe we captured some!
            int newNumberOfOpponentPieces = cloneBS.getNumberPlayerPieces(opponent);

            // If this move caused some capturing to happen, then do it! Greedy!
            if (newNumberOfOpponentPieces < minNumberOfOpponentPieces) {
                myMove = move;
                minNumberOfOpponentPieces = newNumberOfOpponentPieces;
                moveCaptures = true;
            }
        }
        return myMove;
    }

    public int minimax(TablutBoardState BS, int depth, boolean maximizingPlayer){
        int bestValue=0, value;

        ArrayList<Move> options = new ArrayList<Move>();
        options.add(greedy_decision(BS));


        //if depth = 0 or node is a terminal node
        if(depth==0||BS.getWinner()!= Board.NOBODY){
            return heuristic_function(BS);
        }

        if(maximizingPlayer){
            bestValue = -2000;
            for(Move move:options){
                TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                cloneBS.processMove((TablutMove) move);
                value = minimax(cloneBS, depth-1, false);
                bestValue = Math.max(bestValue,value);
            }
            return bestValue;
        }else{
            bestValue = 2000;
            for(Move move:options){
                TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                cloneBS.processMove((TablutMove) move);
                value = minimax(cloneBS, depth-1, true);
                bestValue = Math.min(bestValue,value);
            }
        }

        return bestValue;
    }

    //heuristic of the end node
    public int heuristic_function(TablutBoardState BS){
        int heuristicValue=0;


        if(BS.getOpponentPieceCoordinates().size()<=7){
            return 1000;
        }
        else if(BS.getWinner()!=player_id && BS.getWinner()!=Board.NOBODY){
            //if opponent win

            return -1000;
        }




        return heuristicValue;
    }

    public Move minimax_decision(TablutBoardState bs){
        List<TablutMove> options = bs.getAllLegalMoves();

        // Set an initial move as some random one.
        TablutMove bestMove = options.get(rand.nextInt(options.size()));
        int maxValue=-5000;
        int newValue=0;

        // Iterate over move options and evaluate them.
        for (TablutMove move : options) {

            // To evaluate a move, clone the boardState so that we can do modifications on
            // it.
            TablutBoardState cloneBS = (TablutBoardState) bs.clone();

            // Process that move, as if we actually made it happen.
            cloneBS.processMove(move);
            newValue = minimax(cloneBS,1,false);

            if (newValue > maxValue) {
                bestMove = move;
                maxValue = newValue;

            }
        }
        return bestMove;
    }
}
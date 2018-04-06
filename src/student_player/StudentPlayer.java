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

        //using minimax to get a move
        myMove = minimax_decision(boardState);

        // Return your move to be processed by the server.
        return myMove;
    }

   /* public Move minimax_decision(TablutBoardState bs){
        List<TablutMove> options = bs.getAllLegalMoves();

        // Set an initial move as some random one.
        TablutMove bestMove = options.get(rand.nextInt(options.size()));
        int maxValue=Integer.MIN_VALUE;
        int newValue;

        // Iterate over move options and evaluate them.
        for (TablutMove move : options) {

            // To evaluate a move, clone the boardState so that we can do modifications on it
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

    public int minimax(TablutBoardState BS, int depth, boolean maximizingPlayer){
        int bestValue=0, value;

        List<TablutMove> options = BS.getAllLegalMoves();

        if(BS.getWinner()!=Board.NOBODY){
            if(BS.getWinner()==player_id){ // if i win
                return Integer.MAX_VALUE;
            }else{  //if opponent wins
                return Integer.MIN_VALUE;
            }
        }
        else if(depth==0){//if depth = 0 evaluate moves
            return evaluation(BS);
        }

        if(maximizingPlayer){
            bestValue = Integer.MIN_VALUE;
            for(Move move:options){
                TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                cloneBS.processMove((TablutMove) move);
                value = minimax(cloneBS, depth-1, false);
                bestValue = Math.max(bestValue,value);
            }
            return bestValue;
        }else{
            bestValue = Integer.MAX_VALUE;
            for(Move move:options){
                TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                cloneBS.processMove((TablutMove) move);
                value = minimax(cloneBS, depth-1, true);
                bestValue = Math.min(bestValue,value);
            }
        }

        return bestValue;
    }
*/
     public Move minimax_decision(TablutBoardState bs){
         List<TablutMove> options = bs.getAllLegalMoves();

         // Set an initial move as some random one.
         TablutMove bestMove = options.get(rand.nextInt(options.size()));
         int maxValue=Integer.MIN_VALUE;
         int newValue;

         // Iterate over move options and evaluate them.
         for (TablutMove move : options) {

             // To evaluate a move, clone the boardState so that we can do modifications on it
             TablutBoardState cloneBS = (TablutBoardState) bs.clone();

             // Process that move, as if we actually made it happen.
             cloneBS.processMove(move);

             newValue = minimax(cloneBS,2,false, Integer.MIN_VALUE, Integer.MAX_VALUE);

             if (newValue > maxValue) {
                 bestMove = move;
                 maxValue = newValue;

             }
         }
         return bestMove;
     }

     public int minimax(TablutBoardState BS, int depth, boolean maximizingPlayer, int alpha, int beta){
         int bestValue=0, value;

         List<TablutMove> options = BS.getAllLegalMoves();

         if(BS.getWinner()!=Board.NOBODY){
             if(BS.getWinner()==player_id){ // if i win
                 return Integer.MAX_VALUE;
             }else{  //if opponent wins
                 return Integer.MIN_VALUE;
             }
         }
         else if(depth==0){//if depth = 0 evaluate moves
             return evaluation(BS);
         }

         if(maximizingPlayer){
             bestValue = Integer.MIN_VALUE;
             for(Move move:options){
                 TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                 cloneBS.processMove((TablutMove) move);
                 bestValue = Math.max(bestValue,minimax(cloneBS, depth-1, false, alpha, beta));
                 alpha = Math.max(alpha,bestValue);
                 if(beta <= alpha) break;
             }
             return bestValue;
         }else{
             bestValue = Integer.MAX_VALUE;
             for(Move move:options){
                 TablutBoardState cloneBS = (TablutBoardState) BS.clone();
                 cloneBS.processMove((TablutMove) move);
                 bestValue = Math.min(bestValue,minimax(cloneBS, depth-1, true, alpha,beta));
                 beta = Math.min(beta,bestValue);
                 if(beta<=alpha)break;
             }
         }

         return bestValue;
     }

    //heuristic of the end node
    public int evaluation(TablutBoardState BS){
        int score=0;

        //check capture
        //myself : player_id

        if(player_id == TablutBoardState.SWEDE){ //if i am SWEDE==1, the white, i check black pieces
            score += (16 - BS.getNumberPlayerPieces(TablutBoardState.MUSCOVITE))*100;
        }else if(player_id == TablutBoardState.MUSCOVITE){ //if i am Muscovite == 0, the black, i check white pieces
            score += (9 - BS.getNumberPlayerPieces(TablutBoardState.SWEDE))*100;
        }

        return score;
    }


}
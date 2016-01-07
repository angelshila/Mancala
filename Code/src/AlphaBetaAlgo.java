

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AlphaBetaAlgo {
	
	int TOTAL_PITS, PLAYER, DEPTH, MIN_SCORE_PIT, MAX_SCORE_PIT,CHOICE=-1,TrackDepth=-1;
	static int PLAY_PITS;
	boolean FLAG_ET, FLAG_GAMEOver;
	printBoard pB;
	ArrayList<Integer>board =new ArrayList<Integer>();
	public PriorityQueue<rootChildren> Immediatemoves = new PriorityQueue<rootChildren>(			        
			new Comparator<rootChildren>( ) {

		@Override
		public int compare(rootChildren o1, rootChildren o2) {
			return o2.MancalaScore-o1.MancalaScore;
			}
		});
	
	public AlphaBetaAlgo(int player, ArrayList<Integer> b,int totPits,int playPits, int mancala1, int mancala2, int cutOff){
		this.board=b;
		this.PLAYER=player;
		if(PLAYER==1){
			
			this.MAX_SCORE_PIT = mancala1;
			this.MIN_SCORE_PIT = mancala2;
		}
		else if(PLAYER==2){
			this.MAX_SCORE_PIT = mancala2;
			this.MIN_SCORE_PIT = mancala1;
		}
		this.TOTAL_PITS=totPits;
		PLAY_PITS=playPits;
		this.DEPTH=cutOff;
		this.pB= new printBoard();
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void alphaBeta(){
		pB.PrintBoardAlphaBetaTraverseLogStart("Node","Depth","Value","Alpha","Beta");
		
		//If PLAYER can't move, flush the stones on the other side
		if(cannotMove(PLAYER,board)){
			printAlphaBeta(0, 0, Integer.MIN_VALUE,PLAYER,Integer.MIN_VALUE, Integer.MAX_VALUE);
			board=flushStones(otherPlayer(PLAYER),board);
			printAlphaBeta(0, 0, Eval(board,MAX_SCORE_PIT,MIN_SCORE_PIT),PLAYER,Integer.MIN_VALUE, Integer.MAX_VALUE);
			pB.printFinalBoard(board);
			
		}
		else if(cannotMove(otherPlayer(PLAYER),board)){
			printAlphaBeta(0, 0, Integer.MIN_VALUE,PLAYER,Integer.MIN_VALUE, Integer.MAX_VALUE);
			board=flushStones(PLAYER,board);
			printAlphaBeta(0, 0, Eval(board,MAX_SCORE_PIT,MIN_SCORE_PIT),PLAYER,Integer.MIN_VALUE, Integer.MAX_VALUE);
			pB.printFinalBoard(board);
		}
		
		else{
		
			ArrayList<Integer> boardTemp;
			//Initial call for alphaBeta occurs here
			boardTemp=(ArrayList<Integer>) board.clone();
			ArrayList<Integer> maxScore=chooseMove(boardTemp, 0, PLAYER,0,PLAYER,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
			pB.printFinalBoard(Immediatemoves.peek().Move);
//			System.out.println(Immediatemoves.peek().Move);
//			
//			for(rootChildren a:Immediatemoves){
//				System.out.println(a.Move);
//			}
//			System.out.println("blah");
//			while(!Immediatemoves.isEmpty()){
//				rootChildren a = Immediatemoves.poll();
//				System.out.println("Hey"+a.Move);
//			}
//			
//			System.out.println(maxScore);
		}
		
	}

	
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> chooseMove(ArrayList<Integer> b, int d, int seedPlayer, int ParentChoice, int ParentTurn, int CurrentDepth,
			int A, int B) {
		
//		System.out.println("d"+d);
//		System.out.println("CurrentDepth"+CurrentDepth);
		
		//GameOver. Hence, flush stones
		if(cannotMove(seedPlayer,b)){
			
			//At maxDepth. Therefore, only print Eval value in log
			if(d>=DEPTH){
				FLAG_GAMEOver=true;
				b=flushStones(otherPlayer(seedPlayer),b);
//				pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),ParentTurn);
				printAlphaBeta(ParentChoice, CurrentDepth, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT), ParentTurn,A, B);
				return b;
			}
			else{ //Not at maxDepth, therefore, print 2 logs for each move
				FLAG_GAMEOver=true;
				if(seedPlayer == ParentTurn){ //MAX calls MAX or Min calls Min
					if(seedPlayer == PLAYER)
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MIN_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MIN_VALUE, ParentTurn,A, B);

					else
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MAX_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MAX_VALUE, ParentTurn,A, B);

				}
				else{ //Normal MinMax call
					if(seedPlayer!=PLAYER)
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MAX_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MAX_VALUE, ParentTurn,A, B);

					else
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MIN_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MIN_VALUE, ParentTurn,A, B);

				}
				b=flushStones(otherPlayer(seedPlayer),b);
//				pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),ParentTurn);
				printAlphaBeta(ParentChoice, CurrentDepth, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT), ParentTurn,A, B);
				return b;
			}
		}
		
		//GameOver. Hence, flush stones
		else if(cannotMove(otherPlayer(seedPlayer),b)){
			
			//At maxDepth. Therefore, only print Eval value in log
			if(d>=DEPTH){
				FLAG_GAMEOver=true;
				b=flushStones(seedPlayer,b);
//				pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),ParentTurn);
				printAlphaBeta(ParentChoice, CurrentDepth, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT), ParentTurn,A, B);
				return b;
			}
			else{//Not at maxDepth, therefore, print 2 logs for each move
				FLAG_GAMEOver=true;
				if(seedPlayer == ParentTurn){ //MAX calls MAX or Min calls Min
					if(seedPlayer == PLAYER)
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MIN_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MIN_VALUE, ParentTurn,A, B);

					else
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MAX_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MAX_VALUE, ParentTurn,A, B);

				}
				else{//Normal MinMax call
					if(seedPlayer!=PLAYER)
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MAX_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MAX_VALUE, ParentTurn,A, B);

					else
//						pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Integer.MIN_VALUE,ParentTurn);
						printAlphaBeta(ParentChoice, CurrentDepth, Integer.MIN_VALUE, ParentTurn,A, B);
				}
				b=flushStones(seedPlayer,b);
//				pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),ParentTurn);
				printAlphaBeta(ParentChoice, CurrentDepth,Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT), ParentTurn,A, B);
				return b;
			}
		}
		else if(d >= DEPTH){
//			System.out.println("Hey");
			return b;
		}
		
		else {
			
			if(seedPlayer == PLAYER){//Max's Turn
				
//				System.out.println("d"+d);			
				
				if(PLAYER==1){
					boolean Flag_Turn=false;
					ArrayList<Integer> FinalScores =new ArrayList<Integer>();
					int TempMax=Integer.MIN_VALUE;
					ArrayList<Integer> TempboardBest = null;
					printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn,A, B);
					
					
					for(int i=0;i<MAX_SCORE_PIT;i++){
						ArrayList<Integer> boardClone=(ArrayList<Integer>) b.clone();
						if(boardClone.get(i)==0)
							continue;
						ArrayList<Integer> Temp = takemove(boardClone, i,seedPlayer, d+1,A,B);
						ArrayList<Integer> IntermediateChild=(ArrayList<Integer>) Temp.clone();
//						System.out.println("New State "+Temp);
//						System.out.println("Max is calling Min with opponent");
						if(cannotMove(seedPlayer,IntermediateChild)){
							IntermediateChild = flushStones(otherPlayer(seedPlayer),IntermediateChild);
						}
						else if(cannotMove(otherPlayer(seedPlayer),IntermediateChild)){
							IntermediateChild = flushStones(seedPlayer,IntermediateChild);
						}
						
						if(FLAG_ET == false){
							if(CurrentDepth == 0 || CurrentDepth == 1)
								Flag_Turn=false;
							Temp =chooseMove(Temp,d+1,otherPlayer(seedPlayer),i,seedPlayer,d+1,A,B);
						}
						else{
							FLAG_ET=false;
							if(CurrentDepth == 0 || CurrentDepth == 1)
								Flag_Turn=true;
							Temp =chooseMove(Temp,d,seedPlayer,i,seedPlayer,d+1,A,B);
							if(FLAG_GAMEOver == true){
								Flag_Turn = false;
								FLAG_GAMEOver = false;
							}
						}

						int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
						FinalScores.add(evalValue);
						int maxValue=Collections.max(FinalScores);
						if(TempMax<maxValue){
							TempMax=maxValue;
							TempboardBest = new ArrayList<Integer>(Temp);
							if(CurrentDepth==0 && Flag_Turn==false){
								Immediatemoves.add(new rootChildren(IntermediateChild,TempMax));
							}
							else if(CurrentDepth==1 && Flag_Turn == false){
								Immediatemoves.add(new rootChildren(IntermediateChild,TempMax));
							}
						}
						if(TempMax>=B){
							printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn, A, B);
							break;
						}
							
						
						A=Math.max(TempMax, A);
						
						printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn, A, B);
						
					}
					
					return TempboardBest;
				}
				
				else{//player 2
					boolean Flag_Turn=false;
					int TempMax=Integer.MIN_VALUE;
					ArrayList<Integer> TempboardBest = null;
					ArrayList<Integer> FinalScores =new ArrayList<Integer>();
					printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn, A,B);
					for(int i=board.size()-2;i>=board.size()/2;i--){
						ArrayList<Integer> boardClone=(ArrayList<Integer>) b.clone();
						
						if(boardClone.get(i)==0)
							continue;
						
						ArrayList<Integer> Temp = takemove(boardClone, i,seedPlayer, d+1,A,B);
						ArrayList<Integer> IntermediateChild=(ArrayList<Integer>) Temp.clone();
//						System.out.println("New State "+Temp);
//						System.out.println("Max is calling Min with opponent");
	
						if(cannotMove(seedPlayer,IntermediateChild)){
							IntermediateChild = flushStones(otherPlayer(seedPlayer),IntermediateChild);
						}
						else if(cannotMove(otherPlayer(seedPlayer),IntermediateChild)){
							IntermediateChild = flushStones(seedPlayer,IntermediateChild);
						}		
						
						if(FLAG_ET == false){
							if(CurrentDepth == 0 || CurrentDepth == 1)
								Flag_Turn=false;
							Temp =chooseMove(Temp,d+1,otherPlayer(seedPlayer),i,seedPlayer,d+1,A,B);
						}
						else{
							FLAG_ET=false;
							if(CurrentDepth == 0 || CurrentDepth == 1)
								Flag_Turn=true;
							Temp =chooseMove(Temp,d,seedPlayer,i,seedPlayer,d+1,A,B);
							if(FLAG_GAMEOver == true){
								Flag_Turn = false;
								FLAG_GAMEOver = false;
							}
						}
						int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
						FinalScores.add(evalValue);
						int maxValue=Collections.max(FinalScores);
						if(TempMax<maxValue){
							TempMax=maxValue;
//							pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,TempMax,ParentTurn);
							TempboardBest = new ArrayList<Integer>(Temp);
							if(CurrentDepth==0 && Flag_Turn==false){
								Immediatemoves.add(new rootChildren(IntermediateChild,TempMax));
							}
							else if(CurrentDepth==1 && Flag_Turn == false){
								Immediatemoves.add(new rootChildren(IntermediateChild,TempMax));
							}
						}
						
						if(TempMax>=B){
							printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn,A,B);
							break;
						}
							
						
						A=Math.max(TempMax, A);
						printAlphaBeta(ParentChoice, CurrentDepth, TempMax, ParentTurn,A,B);
						
					}
					
					return TempboardBest;
				}
				
				
			}
			
			else { //Min's turn
//				System.out.println("d"+d);

				if(seedPlayer==1){
					int TempMin=Integer.MAX_VALUE;
					
					ArrayList<Integer> TempboardBest = null;
					ArrayList<Integer> FinalScores =new ArrayList<Integer>();
					printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B);

					for(int i=0;i<board.size()/2-1;i++){

						ArrayList<Integer> boardClone=(ArrayList<Integer>) b.clone();
								
						if(boardClone.get(i)==0)
							continue;
						
						ArrayList<Integer> Temp = takemove(boardClone, i,seedPlayer,d+1,A,B);
//						System.out.println("New State "+Temp);
//
//						System.out.println("Min is calling Max");

						if(FLAG_ET == false){
							Temp =chooseMove(Temp,d+1,otherPlayer(seedPlayer),i,seedPlayer,d+1,A,B);
						}
						else{
							FLAG_ET=false;
							Temp =chooseMove(Temp,d,seedPlayer,i,seedPlayer,d+1,A,B);
						}
						int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
						FinalScores.add(evalValue);
						int minValue=Collections.min(FinalScores);
						if(TempMin>minValue){
							TempMin=minValue;
//							pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,TempMin,ParentTurn);
							TempboardBest = new ArrayList<Integer>(Temp);
						}
//						else{
//							pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,TempMin,ParentTurn);
//						}
						
						if(TempMin<=A){
							printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B );
							break;
						}
							
						
						B=Math.min(TempMin, B);
						printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B);

											
					}
					
					return TempboardBest;
				}			
				else{//player 2
//					int alpha=Integer.MIN_VALUE;
//					int beta=Integer.MAX_VALUE;
					ArrayList<Integer> FinalScores =new ArrayList<Integer>();
					int TempMin=Integer.MAX_VALUE;
					ArrayList<Integer> TempboardBest = null;
					printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B);
					for(int i=board.size()-2;i>=board.size()/2;i--){
						

						ArrayList<Integer> boardClone=(ArrayList<Integer>) b.clone();
					
						if(boardClone.get(i)==0)
							continue;
						
						ArrayList<Integer> Temp = takemove(boardClone, i,seedPlayer,d+1,A,B);
//						System.out.println("New State "+Temp);
//						System.out.println("Min is calling max");
						if(FLAG_ET == false){
							Temp =chooseMove(Temp,d+1,otherPlayer(seedPlayer),i,seedPlayer,d+1,A,B);
						}
						else{
							FLAG_ET=false;
							Temp =chooseMove(Temp,d,seedPlayer,i,seedPlayer,d+1,A,B);
						}
						int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
						FinalScores.add(evalValue);
						
						int minValue=Collections.min(FinalScores);
						if(TempMin>minValue){
							TempMin=minValue;
//							pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,TempMin,ParentTurn);
							TempboardBest = new ArrayList<Integer>(Temp);
						}
//						else{
//							pB.PrintBoardMiniMaxTraverseLog(ParentChoice,CurrentDepth,TempMin,ParentTurn);
//						}
						
						if(TempMin<=A){
							printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B );
							break;
						}
						
						B=Math.min(TempMin, B);
						printAlphaBeta(ParentChoice, CurrentDepth, TempMin, ParentTurn, A,B );

					}
					
					return TempboardBest;

				}
				
				
			} //end of Min's Turn
			
		} // end of else

		
		
		
		
	}

	private void printAlphaBeta(int parentChoice, int currentDepth, int value, int parentTurn, int alpha, int beta) {
		
		if(alpha==Integer.MIN_VALUE && beta == Integer.MAX_VALUE)
			pB.PrintBoardAlphaBetaTraverseLog(parentChoice,currentDepth,value,parentTurn,"-Infinity","Infinity");
		else if(alpha!=Integer.MIN_VALUE && beta == Integer.MAX_VALUE)
			pB.PrintBoardAlphaBetaTraverseLog(parentChoice,currentDepth,value,parentTurn,String.valueOf(alpha),"Infinity");
		else if(alpha==Integer.MIN_VALUE && beta!=Integer.MAX_VALUE)
			pB.PrintBoardAlphaBetaTraverseLog(parentChoice,currentDepth,value,parentTurn,"-Infinity",Integer.toString(beta));
		else
			pB.PrintBoardAlphaBetaTraverseLog(parentChoice,currentDepth,value,parentTurn,String.valueOf(alpha),String.valueOf(beta));
	}

	
private int Eval(ArrayList<Integer> maxScore, int mAX_SCORE_PIT2, int mIN_SCORE_PIT2) {
		
		return (maxScore.get( mAX_SCORE_PIT2)-maxScore.get(mIN_SCORE_PIT2));
	}
	
	private void stealStones(int nextPit,ArrayList<Integer> boardState, int turn) {
		int across=findacross(nextPit);
		if (turn ==1){
            boardState.set(board.size()/2-1, boardState.get(board.size()/2-1)+1+boardState.get(across)); // take the piece just landed
            boardState.set(nextPit,0);//empty bin on human side
            boardState.set(across, 0) ;
		}
		
		else if(turn==2){
			 boardState.set(board.size()-1, boardState.get(board.size()-1)+1+boardState.get(across)); // take the piece just landed
	         boardState.set(nextPit,0);//empty bin on human side
	         boardState.set(across, 0) ;
		}
		
	}
		
		public ArrayList<Integer> flushStones (int player, ArrayList<Integer> b) {
			
			if(player == 1){
				for (int i = 0; i <board.size()/2-1; i++) {
					b.set(board.size()/2-1, b.get(board.size()/2-1)+b.get(i));
					b.set(i, 0);
					
				}
				
			}
			else{
				
				for (int i = board.size()/2; i <board.size()-1; i++) {
					b.set(board.size()-1, b.get(board.size()-1)+b.get(i));
					b.set(i, 0);
					
				}
			}
			return b;
			
		}
		
		public boolean cannotMove(int player,ArrayList<Integer> b) {
			// Returns true if the currentPlayer cannot move.
			if(player == 1){
				for (int i = 0; i < board.size()/2-1; i++) {
					if (b.get(i) > 0) {  
					// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + false);
					return false;
					}
				}
			}
			
			else{
				for (int i = board.size()/2; i < board.size()-1; i++) {
					if (b.get(i) > 0) {  
					// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + false);
					return false;
					}
				}
			}
			
			// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + true);
			return true;
		}

		private int findacross(int ending) {
			int across = -555;
		    across = Math.abs(ending-PLAY_PITS);   
		    return across;

		}
		
		
		private ArrayList<Integer> takemove(ArrayList<Integer> b, int choice, int turn, int d, int Alpha, int Beta) {
//			System.out.println("d"+d);
//			System.out.println("Here is CurrentDepth "+CURRENT_DEPTH);
			int avoidMancala;
			int stones=b.get(choice);
			int nextPit=choice+1;
//			int alpha=Integer.MIN_VALUE;
//			int beta=Integer.MAX_VALUE;
			
			if(turn == 1){
				avoidMancala=b.size()-1;
			}
			else{
				avoidMancala=b.size()/2-1;
				
			}
			
			//Set nextPit to point to play pits
			if(nextPit==avoidMancala) {
				if (avoidMancala == b.size()-1){
					nextPit = 0; //avoid comp i.e. player 2 mancala for human
				}
				else if( avoidMancala == b.size()/2-1){
					nextPit = b.size()/2;//avoid human i.e. player 1 mancala for comp
				}
			}
			
			//Empty selected pit
			b.set(choice, 0);
			
			while(stones>0){
				
				//function to steal stones
				//function to avoid mancala of opponent 
				//function to change player
				
				if(nextPit==avoidMancala) {
					if (avoidMancala == b.size()-1){
						nextPit = 0; //avoid comp i.e. player 2 mancala for human
					}
					else if( avoidMancala == b.size()/2-1){
						nextPit = b.size()/2;//avoid human i.e. player 1 mancala for comp
					}
				}
				else if(nextPit == board.size()){
					nextPit = 0;
				}
				
				if(stones==1 && turn ==1 && b.get(nextPit) == 0 && nextPit!=board.size()/2-1 && (nextPit>-1 && nextPit<board.size()/2-1)){
//					b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
					stones--;
					stealStones(nextPit, b,turn);
					
				}
				
				else if(stones==1 && turn ==2 && b.get(nextPit) == 0 && nextPit!=board.size()-1 && (nextPit>board.size()/2-1 && nextPit<board.size()-1)){
//					b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
					stones--;
					stealStones(nextPit, b,turn);
				}
				
				
				//Landed in one's own Mancala. Return something  so that turns are not changed 
				else if(stones == 1 && turn == 1 &&  nextPit == b.size()/2-1){
					FLAG_ET=true;
					stones--;
					b.set(board.size()/2-1, b.get(board.size()/2-1)+1);
					return b;

				}
				
				//Landed in one's own Mancala. Return something  so that turns are not changed 
				else if(stones == 1 && turn==2 && nextPit == b.size()-1){
					FLAG_ET=true;
					stones--;
					b.set(board.size()-1, b.get(board.size()-1)+1);
					return b;
				}
				
				//Normal Move
				else{
					int currentInNext=b.get(nextPit);
					b.set(nextPit, currentInNext+1); //Incrementing value in next pits
					stones--; //Decrementing Stones
					nextPit++;
				}
			}
			
		//Print only if at maxDepth and Game
			if(d>=DEPTH && !cannotMove(turn,b) && !cannotMove(otherPlayer(turn),b)){
//				System.out.println(b);
				printAlphaBeta(choice, d, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),turn, Alpha, Beta);			
//				pB.PrintBoardMiniMaxTraverseLog(choice, d, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT), turn);
			}
//			if(d>=DEPTH)
//				printAlphaBeta(choice, d, Eval(b,MAX_SCORE_PIT,MIN_SCORE_PIT),turn, Alpha, Beta);			
			return b;
		}
		
		private int otherPlayer(int pLAYERturn) {
			if(pLAYERturn==1){
				return 2;
			}
			else{
				return 1;
			}
			
		}


}

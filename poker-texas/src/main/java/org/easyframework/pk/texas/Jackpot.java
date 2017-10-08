package org.easyframework.pk.texas;

import java.util.List;

import org.easyframework.pk.texas.exception.TexasException;

/**
 * 将池
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年9月10日 下午5:36:25
 */
public class Jackpot {
	
	private List<List<Long>>[][] betList=null;
	private Long[] allBets=null;  //每人加注总额
	private Long[] currentBet=null;  //本轮加注总额
	private int maxBetSeatNo;//本轮最先加注最高者坐位；
	private int sendCardIndex;//第几轮发牌
	private int wantBetSeatNo;//等待操作者编号
	
	public Jackpot(int playerNum){
		if(playerNum>0){
			betList=new  List[4][playerNum];
		}else{
			throw new TexasException(101,"玩家坐位数量无效",null);
		}
	}
	public void bet(int index,int seatNo,Long betValue){
		
	}
	/**
	 * 在第Ｎ轮下注中，在最大下注后，所有人表态完毕
	 * @param index
	 * @return
	 */
	public boolean haveAllBet(int index){
		return true;
	}
	/**
	 * 玩家在第Ｎ轮是否有不加记录
	 * @param index  第几轮，
	 * @param seatNo 第几坐位的玩家。
	 * @return
	 */
	public boolean haveCheck(int index,int seatNo){
		return true;
	}
	
	public void initBet(){
		for(int row=0;row<betList.length;row++){
			for(int column=0;column<betList[row].length;column++){
				betList[row][column]=null;
			}
		}
	}

}

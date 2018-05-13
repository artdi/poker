package org.easyframework.pk.texas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 下注及分配功能<br>记录玩下注情况，完成分配奖金任务。不关注状态
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年9月10日 下午5:36:25
 */
public class Jackpot  {
	/**
	 * 用二维数组记录下注情况
	 * betRecords[坐位号][发牌轮次]
	 */
	private long betRecords[][];
	/**
	 * 用BetSeatNoAndSendCardIndex数据结构记录下注过程
	 */
	private List<BetSeatNoAndSendCardIndex> betOrderRecords;
	/**
	 * 创建下注记录器
	 * @param seatNum 坐位数
	 */
	public Jackpot(int seatNum) {
		this.betRecords=new long[seatNum][4];
		this.initBetRecord();
	}
	/**
	 * 游戏开始时，清空所有下注记录
	 */
	public void initBetRecord(){
		for(int i=0;i<betRecords.length;i++){
			for(int j=0;j<betRecords[i].length;j++){
				betRecords[i][j]=0;
			}
		}
		this.betOrderRecords=new LinkedList<BetSeatNoAndSendCardIndex>();
	}
	/**
	 * 记录下注
	 * @param seatNo  坐位号，坐0开始
	 * @param sendCardIndex  第几轮下注 只可0,1,2,3
	 * @param betNum   下注额
	 */
	public void addBet(int seatNo,int sendCardIndex,  long betNum){
		this.betRecords[seatNo][sendCardIndex]=this.betRecords[seatNo][sendCardIndex]+betNum;
		this.betOrderRecords.add(new BetSeatNoAndSendCardIndex(betNum, seatNo, sendCardIndex));
	}
	/**
	 * 获取某玩家 某轮加注次数。//FIXME 如何避免玩家一直多次为0的表态
	 * @param seatNo  玩家坐位号
	 * @param sendCardIndex   第几轮的加注
	 * @return  非0的加注次数
	 */
	public int getAddBetNum(int seatNo,int sendCardIndex ){
		int addBetNum=0;
		for(int i=0;i<this.betOrderRecords.size();i++){
			BetSeatNoAndSendCardIndex bet=this.betOrderRecords.get(i);
			if(bet.seatNo==seatNo&&bet.sendCardIndex==sendCardIndex&&bet.bet>0){
				addBetNum++;
			}
		}
		return addBetNum;
	}
	/**
	 * 查找部分玩家第Ｎ轮发牌时，<b>第一个</b>最大下注玩家位置号 
	 * @param sendCardIndex 第几轮发牌
	 * @param seatNos 要比较的玩家位位号 (仅考虑当前有效的玩家)。
	 * [0,2,3]表示只在坐号0,坐号2,坐号3玩家中查找。
	 * @return  最大玩家位置号,seatNos数组中的一个。
	 * <br>如果参数错误，则返回-1,seatNos中的玩家如果本轮均没下过注，
	 */
	public int getMaxBetSeatNo(int sendCardIndex,int[] seatNos){
		int maxSeatNo=-1;
		long maxBet=-1;

		//历遍下注记录，找到本轮下注的有效玩家，
		long[] sendCardIndexBetRecords=new long[betRecords.length];
		for(int i=0;i<this.betOrderRecords.size();i++){
			BetSeatNoAndSendCardIndex b=betOrderRecords.get(i);
			if(b.sendCardIndex==sendCardIndex){//是本轮下注
				for(int seatNo:seatNos){
					if(b.seatNo==seatNo){//本坐位下注
						sendCardIndexBetRecords[b.seatNo]=sendCardIndexBetRecords[b.seatNo]+b.bet;
						if(sendCardIndexBetRecords[b.seatNo]>maxBet){
							maxSeatNo=b.seatNo;
							maxBet=sendCardIndexBetRecords[b.seatNo];
						}
						break;
					}
				}
				
			}
		}
		return maxSeatNo;
	}
	
	/**
	 * 分配奖金<br>
	 * 坐位名次orders=[0,1,1,0,2,0]表示，坐号1,坐号2平分第一名次奖金，如奖金还有剩，坐号4获得剩余奖金
	 * <br>返回allocation=[0,100,150,0,50,0]表示，坐号1,2,分配到第一名奖金，坐号4获得第二名奖金
	 * <br>押注池［70,30,20,10,50］
	 * <br>名次  ［0,1,1,2,3］
	 * <br>则奖金分配应为：[0,(20+20+20+10+20)*0.5+]第一名平分<b>绝对奖池</b>，其它平分<b>押注剩余</b>
	 * @param orders  
	 * @return    各坐位分配到的奖金
	 */
	public long[] allocation(int[] orders){
		long[] allocations=new long[orders.length];
		for(long a:allocations){
			a=0;
		}
		//sum all bets
		List<BetSeatNoAndOrder> allBetsList=this.getBetSeatNoAndOrder(orders);
		List<BetSeatNoAndOrder> notGetUpBetList=new LinkedList();
		long getUpBets=0;
		boolean allocationsGetUpBets = false;
		for(int i=0;i<allBetsList.size();i++){
			if(allBetsList.get(i).order==0){
				getUpBets=getUpBets+allBetsList.get(i).bet;
			}else{
				notGetUpBetList.add(allBetsList.get(i));
			}
		}
		//坚持到后的玩家
		BetSeatNoAndOrder[] notGetUpBetArray= notGetUpBetList.toArray(new BetSeatNoAndOrder[notGetUpBetList.size()]);
		
		//按下注额大小排序
		Arrays.sort((BetSeatNoAndOrder[])notGetUpBetArray, new Comparator<BetSeatNoAndOrder>(){
			public int compare(BetSeatNoAndOrder o1, BetSeatNoAndOrder o2) {
				if(o1.bet<o2.bet){
					return -1;
				}else if(o1.bet==o2.bet){
					return 0;
				}else {
					return 1;
				}
			}
		});
		//从最小下注额开始分配
		for(int i=0;i<notGetUpBetArray.length;i++){
			long tempBet=0;//本轮分配金额
			long tempReduce=notGetUpBetArray[i].bet;//本轮扣除金额
			List<Integer> minOrderSeatNo=new LinkedList<Integer>();
			int minOrder=Integer.MAX_VALUE;
			for(int j=i;j<notGetUpBetArray.length;j++){
				//记录好最前排名玩家
				if(notGetUpBetArray[j].order<minOrder){
					minOrderSeatNo=new LinkedList<Integer>();
					minOrderSeatNo.add(notGetUpBetArray[j].seatNo);
					minOrder=notGetUpBetArray[j].order;
				}else if(notGetUpBetArray[j].order==minOrder){
					minOrderSeatNo.add(notGetUpBetArray[j].seatNo);
				}
				//扣除各个玩家的押注
				if(notGetUpBetArray[j].bet<tempReduce){
					tempBet=tempBet+notGetUpBetArray[j].bet;
					notGetUpBetArray[j].bet=0;
				}else{
					tempBet=tempBet+tempReduce;
					notGetUpBetArray[j].bet=notGetUpBetArray[j].bet-tempReduce;
				}
			}
			//分配给相应人员
			for(int n=0;n<minOrderSeatNo.size();n++){
				int seatNo=minOrderSeatNo.get(n);
				allocations[seatNo]=allocations[seatNo]+tempBet/minOrderSeatNo.size();
				//第一名玩家瓜分放弃玩家的押注
				if(notGetUpBetArray[i].order==1){
					allocations[seatNo]=allocations[seatNo]+getUpBets/minOrderSeatNo.size();
					allocationsGetUpBets = true;
				}
			}
			if(allocationsGetUpBets==true){
				getUpBets=0;
			}
		}
		return allocations;
	}
	
	/**
	 * 获取各坐位玩家本局的下注总额
	 * @return [70,0,20,10,0,60] 表示坐号0玩家押注70,坐号1押注0 。。。
	 */
	protected long[] getBets(){
		long[] bets=new long[this.betRecords.length];
		
		for(int i=0;i<this.betRecords.length;i++){
			bets[i]=0;
			for(long bet:this.betRecords[i]){
				bets[i]=bets[i]+bet;
			}
		}
		return bets;
	}
	/**
	 * 获取各玩家本轮的下注额
	 * @param sendCardIndex 第几轮发牌
	 * @return @return [70,0,20,10,0,60] 表示坐号0玩家押注70,坐号1押注0 。。。
	 */
	protected long[] getBets(int sendCardIndex){
		long[] bets=new long[this.betRecords.length];
		
		for(int i=0;i<this.betRecords.length;i++){
			bets[i]=this.betRecords[i][sendCardIndex];
		}
		return bets;
	}
	/**
	 * 把用户下注额，坐位号，牌型大小排名放置到BetSeatNoAndOrder链表中。
	 * @param orders [0,1,1,3,2,0] 表示坐号0玩家放弃了，坐号1、2玩家并列第一名，坐号3玩家第3名，坐号4玩家放弃。
	 * @return
	 */
	private List<BetSeatNoAndOrder> getBetSeatNoAndOrder(int[] orders){
		long[] bets=this.getBets();
		List<BetSeatNoAndOrder> list=new LinkedList<BetSeatNoAndOrder>();
		for(int i=0;i<orders.length;i++){
			BetSeatNoAndOrder betAndOrder=new BetSeatNoAndOrder(bets[i],i,orders[i]);
			list.add(betAndOrder);
		}
		return list;
	}
	
	/**
	 * 下注结果
	 * @Description: 
	 * @author artdi artditan@gmail.com 
	 * @date 2018年4月15日 下午6:07:36
	 */
	public class  BetSeatNoAndOrder{
		/**
		 * 下注额
		 */
		long bet=0;
		/**
		 * 玩家坐位号
		 */
		int seatNo=-1;
		/**
		 * 牌型大小排序，0表示弃权了
		 */
		int order=0;
		BetSeatNoAndOrder(long bet,int seatNo,int order){
			 this.bet=bet;
			 this.seatNo=seatNo;
			 this.order=order;
		}
	}	
	public class BetSeatNoAndSendCardIndex {
		/**
		 * 下注额
		 */
		long bet=0;
		/**
		 * 玩家坐位号
		 */
		int seatNo=-1;
		/**
		 * 表示第几轮的下注
		 */
		int sendCardIndex=-1;
		BetSeatNoAndSendCardIndex(long bet,int seatNo,int sendCardIndex){
			 this.bet=bet;
			 this.seatNo=seatNo;
			 this.sendCardIndex=sendCardIndex;
		}
	}
	

}

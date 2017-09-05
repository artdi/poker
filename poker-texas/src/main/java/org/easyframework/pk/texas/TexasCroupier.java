package org.easyframework.pk.texas;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.command.ICommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 处理发牌，收注，分配奖金
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年8月6日 上午10:13:42
 */
public class TexasCroupier extends TexasTable{
	private static final Logger log=LoggerFactory.getLogger(TexasCroupier.class);
	
	private PokerCard[] allPokerCard=PokerCard.create52PokerCard();
	private int currentPokerIndex=0;
	
	private static final int[] SendCardNum={2,3,1,1};
	private int sendCardIndex=0;//第几轮发牌
	private int sendCardIndexAdd=0;//第几轮加注，每轮发牌大盲可加注三次
	private long sendCardIndexAddMax=0;//本轮加注最大金额
	
	private volatile int dealerSeatNo=0;
	private volatile int betWatingNo;
	
	private volatile Map<String,Long> betRecord;
	
	
	public TexasCroupier(TexasCroupierConfig config,
			ICommandProcessor cmdProcessor) {
		super(config, cmdProcessor);
	}
	@Override
	protected int startGame() {
		//find next Dealer
		this.dealerSeatNo = nextEffectivePlayer(dealerSeatNo);
		// 小盲注
		this.betWatingNo = nextEffectivePlayer(dealerSeatNo);
		this.bet(seats[betWatingNo].getPlayer(), config.getSmallBlinds());
		// 大盲注
		this.betWatingNo = nextEffectivePlayer(betWatingNo);
		this.bet(seats[betWatingNo].getPlayer(), config.getBigBlinds());
		
		this.dealing();
		// 下一玩家
		this.betWatingNo = nextEffectivePlayer(betWatingNo);
		return 1;
	}
	@Override
	protected void clearTableStatus() {
		super.clearTableStatus();
		shuffle();
		//清空玩家手中牌
		for(TexasSeat seat:seats){
			if(seat.getPlayer()!=null){
				seat.getPlayer().setPokerHand(null);
			}
		}
		sendCardIndex=0;
		sendCardIndexAdd=0;
		sendCardIndexAddMax=0;
		this.betRecord=new HashMap();
		
		
	}
	
	/**
	 * 洗牌
	 */
	private void shuffle(){
		Random random=new Random();
		PokerCard[] newCards=new PokerCard[allPokerCard.length];
		for(int i=0;i<allPokerCard.length;i++){
			newCards[i]=allPokerCard[i];
		}
		PokerCard temp=null;
		for(int i=0;i<newCards.length;i++){
			int index=Math.abs(random.nextInt())%newCards.length;
			temp=newCards[i];
			newCards[i]=newCards[index];
			newCards[index]=temp;
		}
		allPokerCard= newCards;
	}
	/**
	 * 是最后一张牌
	 * @return
	 */
	private boolean isLastDealing(){
		return sendCardIndex==(SendCardNum.length-1)?true:false;
	}
	
	/**
	 * 对玩家发牌<br>
	 * 发牌一般分为5个步骤，分别为，
	 * 　　Pe—先下大小盲注，然后给每个玩家发2张底牌，大盲注后面第一个玩家选择跟注、加注或者盖牌放弃，按照顺时针方向，其他玩家依次表态
	 * ，大盲注玩家最后表态，如果玩家有加注情况，前面已经跟注的玩家需要再次表态甚至多次表态。
	 * 　　Flop—同时发三张公牌，由小盲注开始（如果小盲注已盖牌
	 * ，由后面最近的玩家开始，以此类推），按照顺时针方向依次表态，玩家可以选择下注、加注、或者盖牌放弃。
	 * 　　Turn—发第4张牌，由小盲注开始，按照顺时针方向依次表态，玩家可以选择下注、加注、或者盖牌放弃。
	 * 　　River—发第，由小盲注开始，按照顺时针方向依次表态，玩家可以选择下注、加注、或者盖牌放弃。
	 * 　　比牌—经过前面4轮发牌和下注，剩余的玩家开始亮牌比大小，成牌最大的玩家赢取池底。
	 * 
	 * @return 1:发牌后，本局继续 ,-1:本局发牌环节已结束，需要下一局洗牌后再发。
	 */
	private int dealing(){
		if(this.sendCardIndex<SendCardNum.length){
			int cardNum=SendCardNum[this.sendCardIndex];
			PokerCard[] pokers=this.getPokerCard(cardNum);
			for(int i=0;i<seats.length;i++){
				if(seats[i].getPlayer()!=null&&!seats[i].isGiveUp()){
					if(seats[i].getPlayer().getPokerHand()==null){
						seats[i].getPlayer().setPokerHand(new TexasPokerHand());
					}
					
					seats[i].getPlayer().getPokerHand().addPokers(pokers);
					
					if(this.sendCardIndex==0){ //第一论为私有牌，每次获取不一样
						pokers=this.getPokerCard(cardNum);
					}
				}
			}
			this.sendCardIndex++;
			this.sendCardIndexAdd=0;
			this.sendCardIndexAddMax=0;
		}else{
			return -1;
		}
		
		
		return 1;
	}
	private PokerCard[] getPokerCard(int cardNum){
		PokerCard[] cards=new PokerCard[cardNum];
		for(int i=0;i<cardNum;i++){
			cards[i]=this.allPokerCard[this.currentPokerIndex++];
		}
		return cards;
	}
	/**
	 * 计算押注奖池情况
	 */
	@Override
	protected int allot() {
		return 0;
	}
	/**
	 * 玩家奖金放入奖池
	 * @param player
	 * @param betNum
	 * @return 1:跟注，2:加注，3:ALLIN跟注，-1:押注不符合规则
	 */
	private int addBet(TexasPlayer player, long betNum){
		long playerBeted=0;
		if(betRecord.containsKey(player.getId())){
			playerBeted=betRecord.get(player.getId());
		}
		playerBeted=playerBeted+betNum;
		betRecord.put(player.getId(), playerBeted);
		
		if(betNum>this.sendCardIndexAddMax){
			this.sendCardIndexAddMax=betNum;
			this.sendCardIndexAdd++;
			return 2;
		}
		if(betNum==player.getBankroll()){
			return 3;
		}
		
		return 1;
	}
	
	
	@Override
	public int bet(TexasPlayer player, long betNum) {
		int seatNo=this.getPlayerSeatNo(player);
		if(-1==seatNo){
			return -3;
		}
		if(this.betWatingNo!=seatNo){
			return -1;
		}
		TexasPlayer p=this.seats[seatNo].getPlayer();
		
		if(p.getBankroll()<betNum){
			return -2;
		}
		int addResult=this.addBet(p, betNum);
		boolean needDealing=false;
		this.betWatingNo = nextEffectivePlayer(betWatingNo);
		//大盲继续加注，不发牌，下一玩家继续选择。
		//大盲不再加注，发牌未结束，则先发牌，继续下一玩家选择。
		//大盲不再加注，发牌已结束，则结束游戏
		if(this.isBigPlayer(seatNo)&&addResult==1){//大盲操作
			needDealing=true;
		}else{//普通玩家操作
			//下一玩家是大盲，已经是最后一轮加注，则要发牌
			if(this.isBigPlayer(this.betWatingNo)&&this.sendCardIndexAdd==2){
				needDealing=true;
			}
		}
		
		if(needDealing){
			if(this.isLastDealing()){
				this.endGame();
				this.tryStartGame();
				return 2;
			}else{
				
				this.dealing();
				
			}
		}
		
		return 1;
	}
	/**
	 * 是否是大盲玩家，庄家后面第二有效玩家为大盲玩家。
	 * @param seatNo
	 * @return
	 */
	private boolean isBigPlayer(int seatNo){
		int bigPlayerSeatNo=nextEffectivePlayer(this.dealerSeatNo);
		bigPlayerSeatNo=nextEffectivePlayer(bigPlayerSeatNo);
		return seatNo==bigPlayerSeatNo?true:false;
	}
	/**
	 * 本论下注完是否需要发牌
	 * @return 
	 */
	private boolean needDealing(){
		return true;
	}
	
	/**
	 * 找SeatNo坐位后第一个有效玩家坐位号
	 * @param SeatNo  开始查找的坐置
	 * @return  坐位号，-1:表示异常，某坐位后无有效玩家。不应出现的情况 
	 */
	private int nextEffectivePlayer(int seatNo){
		TexasSeat activeSeat=seats[seatNo].getNextSeat();
		while(activeSeat.getSeatNo()!=seatNo){
			if(activeSeat.getPlayer()!=null&&!activeSeat.isGiveUp()){
				return activeSeat.getSeatNo(); 
			}
			activeSeat=activeSeat.getNextSeat();
		}
		return -1;
	}
	
	

}

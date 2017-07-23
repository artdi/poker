package org.easyframework.pk.texas;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.PokerCardUtils;
import org.easyframework.pk.command.ICommand;
import org.easyframework.pk.command.ICommandProcessor;
import org.easyframework.pk.texas.command.TexasCommand;
import org.easyframework.pk.texas.exception.TexasException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HkTexasCroupier extends TexasCroupierBase implements ITexasCroupier,ITexasTableStatus {

	private static final Logger log=LoggerFactory.getLogger(HkTexasCroupier.class);
	private final ReentrantLock lock = new ReentrantLock();
	
	private static final TexasCroupierConfig config=new TexasCroupierConfig();
	 
	private ICommandProcessor cmdProcessor;
	
	
	private PokerCard[] allPokerCard=PokerCard.create52PokerCard();
	private int currentPokerIndex=0;
	private PokerCard[] flop=new PokerCard[3];
	
	
	private String tableId=UUID.randomUUID().toString();
	private List<TexasPlayer> viewers=new LinkedList<TexasPlayer>();
	private TexasPlayer[] players=new TexasPlayer[config.getMaxPlayer()];
	private TexasTableStatusName tableStatus=TexasTableStatusName.waitPlayer;
	private Map<Long,List<TexasPlayer>> betMap=new HashMap<Long,List<TexasPlayer>>();
	//庄家
	private TexasPlayer dealer;
	//等待操作的玩家
	private TexasPlayer waitingPlayer;
	private int playerNum=0;
	
	private void initPokers(){
		this.allPokerCard=this.shuffle(allPokerCard);
		this.currentPokerIndex=0;
		this.flop=new PokerCard[3];
		
		TexasPlayer doingPlayer=this.dealer;
		doingPlayer.setPokerHand(new TexasPokerHand());
		doingPlayer=doingPlayer.getNextPlayer();
		while(doingPlayer!=this.dealer){
			doingPlayer.setPokerHand(new TexasPokerHand());
			doingPlayer=doingPlayer.getNextPlayer();
		}
		
	}
	
	public HkTexasCroupier(ICommandProcessor cmdProcessor){
		this.cmdProcessor=cmdProcessor;
		
	}
	
	private int getPlayerSeatNo(TexasPlayer player){
		for(int i=0;i<players.length;i++){
			if(players[i]!=null&&players[i].getId().equals(player.getId())){
				return i;
			}
		}
		return -1;
	}
	
	private void handlePlayerChange(){
		playerNum=this.countEffectivePlayer(config.getBigBlinds());
		if(TexasTableStatusName.waitPlayer==this.tableStatus&&playerNum>1){
			//start game
			this.startGame();
			this.tableStatus=TexasTableStatusName.waitBet;
		}else if(TexasTableStatusName.waitBet==this.tableStatus&&playerNum<2){
			//end game
			this.endGame();
			this.tableStatus=TexasTableStatusName.waitPlayer;
			this.handlePlayerChange();
		}
		
		cmdProcessor.handledCommand(new TexasCommand("玩家人数有变化了，处理结束或开始"));
		
	}
	private int countEffectivePlayer(long bankroll){
		int effectivePlayer=0;
		for(int i=0;i<players.length;i++){
			if(players[i]!=null&&players[i].getBankroll()>bankroll){
				effectivePlayer++;
			}
		}
		return effectivePlayer;
	}
	private void startGame(){
		//确定庄家位置
		this.dealer=this.dealer.getNextPlayer();
		//收取大小盲注
		this.waitingPlayer=this.dealer.getNextPlayer();
		this.bet(waitingPlayer, this.config.getSmallBlinds());
		waitingPlayer=waitingPlayer.getNextPlayer();
		this.bet(waitingPlayer, this.config.getBigBlinds());
		
		//初始化各玩家牌
		initPokers();
		//发底牌
		TexasPlayer doingPlayer=this.dealer;
		PokerCard[] pri={this.allPokerCard[this.currentPokerIndex++],this.allPokerCard[this.currentPokerIndex++]};
		sendPoker2Player(doingPlayer,pri);
		doingPlayer=doingPlayer.getNextPlayer();
		while(doingPlayer!=this.dealer){
			PokerCard[] tempPri={this.allPokerCard[this.currentPokerIndex++],this.allPokerCard[this.currentPokerIndex++]};
			sendPoker2Player(doingPlayer,tempPri);
			doingPlayer=doingPlayer.getNextPlayer();
		}
		//等待收取押注
		waitingPlayer=waitingPlayer.getNextPlayer();
		callPlayerBet(waitingPlayer);
		//this.tableStatus=TexasTableStatusName.waitBet;
	}
	private void callPlayerBet(TexasPlayer player){
		ICommand command=new TexasCommand(this.tableId+player.getId()+"请下注");
		this.cmdProcessor.handledCommand(command);
	}
	private void sendPoker2Player(TexasPlayer player,PokerCard[] pokers){
		player.getPokerHand().addPriPokers(pokers);
		ICommand command=new TexasCommand(this.tableId+player.getId()+"发底牌");
		this.cmdProcessor.handledCommand(command);
	}
	private void endGame(){
		//计算各奖池玩家牌型大小
		//玩家分配奖池
		//计算有效玩家
	}
	
	public ITexasCroupier sitDown(TexasPlayer playerId,Integer seatNo) throws TexasException {
		if(seatNo>config.getMaxPlayer()){
			throw new TexasException(102, "坐位已满", null);
		}
		if(playerId.getBankroll()<config.getBigBlinds()){
			throw new TexasException(102, "带金钱不够", null);
		}
		TexasPlayer player=new TexasPlayer(playerId.getId());
		player.setBankroll(playerId.getBankroll());
		if(players[seatNo]==null||getPlayerSeatNo(player)==-1){
			player.setStatus(TexasPlayerStatus.siting);
			player.setSeated(true);
			
			try{
				lock.lock();
			
				players[seatNo]=player;
				if(dealer==null){
					dealer=player;
				}
				if(waitingPlayer==null){
					waitingPlayer=player;
					waitingPlayer.setNextPlayer(player);
				}else{
					player.setPrePlayer(waitingPlayer);
					player.setNextPlayer(waitingPlayer.getNextPlayer());
					waitingPlayer.setNextPlayer(player);
					waitingPlayer=player;
				}
			}finally{
				lock.unlock();
			}
		}else{
			throw new TexasException(102, "坐位已有人坐", null);
		}
		
		cmdProcessor.handledCommand(new TexasCommand(player.toString()+"玩家已坐下在第"+seatNo+"坐位"));
		handlePlayerChange();
		
		return this;
	}

	public ITexasCroupier standUp(TexasPlayer player) throws TexasException {
		int seatNo=this.getPlayerSeatNo(player);
		if(seatNo!=-1&&players[seatNo]!=null){
			this.players[seatNo].setStatus(TexasPlayerStatus.view);
			this.players[seatNo].setSeated(false);
			this.viewers.add(player);
			handlePlayerChange();
		}else{
			throw new TexasException(102, "玩家不在坐位上", null);
		}
		
		return this;
	}
	
	public ITexasCroupier playerAddChip(TexasPlayer player, long betNum)
			throws TexasException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ITexasCroupier bet(TexasPlayer player, long betNum)
			throws TexasException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ITexasCroupier view() {
		log.debug("桌面ID:"+this.tableId);
		log.debug("桌面状态:"+this.tableStatus);
		log.debug("坐位人数："+this.getPlayerNum());
		StringBuilder playerSb=new StringBuilder();
		for(int i=0;i<this.players.length;i++){
			playerSb.append("坐位").append(i).append("=");
			if(players[i]!=null){
				playerSb.append(players[i].getId());
				playerSb.append(":");
				playerSb.append(players[i].getBankroll());
				playerSb.append("[");
				playerSb.append(PokerCard.getPokerCardsName(players[i].getPokerHand().getUnNullPokers()));
				playerSb.append("]");
			}else{
				playerSb.append("空");
			}
			playerSb.append(",");
		}
		log.debug("玩家状态："+playerSb.toString());
		StringBuilder betSb=new StringBuilder();
		
		for(Long key:this.betMap.keySet()){
			betSb.append("奖池").append(key).append("[");
			for(TexasPlayer player:this.betMap.get(key)){
				betSb.append(player.getId()).append("、");
			}
			betSb.append("\n\r");
		}
		log.debug("奖池状态：");
		log.debug(betSb.toString());
		
		return this;
	}

	public int getCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	public TexasTableStatusName getStatusName() {
		
		return this.tableStatus;
	}



	public int getPlayerNum() {
		int playerNum=0;
		for(int i=0;i<this.players.length;i++){
			if(players[i]!=null){
				playerNum++;
			}
		}
		return playerNum;
	}



	public TexasPlayer[] getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}



	public Map<Long, List<TexasPlayer>> getBets() {
		return this.betMap;
	}


	public List<TexasPlayer> getViewer() {
		return this.viewers;
	}

	public TexasPlayer getBetPlayer() {
		return this.waitingPlayer;
	}

}

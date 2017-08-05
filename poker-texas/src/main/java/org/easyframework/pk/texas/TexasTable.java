package org.easyframework.pk.texas;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.command.ICommandProcessor;
import org.easyframework.pk.texas.command.TexasCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月27日 下午11:04:44
 */
public class TexasTable implements ITexasTable{
	
	public final static Logger log=LoggerFactory.getLogger(TexasTable.class);
	
	private final ReentrantLock lock = new ReentrantLock();
	protected TexasCroupierConfig config;
	
	private ICommandProcessor cmdProcessor;
	
	protected volatile TexasSeat[] seats;
	
	private volatile TexasTableStatus status;
	

	private String tableId;
	
	public TexasTable(TexasCroupierConfig config,ICommandProcessor cmdProcessor){
		this.config=config;
		this.cmdProcessor=cmdProcessor;
		
		seats=new TexasSeat[config.getMaxPlayer()];
		for(int i=0;i<seats.length;i++){
			seats[i]=new TexasSeat(i);
			if(i>0){
				seats[i-1].setNextSeat(seats[i]);
			}
		}
		seats[seats.length-1].setNextSeat(seats[0]);
		
		status=TexasTableStatus.waitPlayer;
	}
	/**
	 * 初始化桌子状态，包括：
	 * 1.设置所有坐位状态为非放弃状态，
	 * 2.清空玩家手里所有牌
	 * 
	 */
	private void clearTableStatus(){
		for(int i=0;i<seats.length;i++){
			seats[i].setGiveUp(false);
		}
		
	}
	public int getCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int sitDown(TexasPlayer playerId, Integer seatNo) {
		if(playerId.getBankroll()<config.getBigBlinds()){
			return -1;
		}
		if(seatNo!=null&&(seatNo>config.getMaxPlayer()||seatNo<0)){
			log.debug("坐位参数有误");
			return -4;
		}
		TexasPlayer player=new TexasPlayer(playerId.getId());
		player.setBankroll(playerId.getBankroll());
		
		if(getPlayerSeatNo(player)!=-1){
			log.debug("玩家已在坐位上");
			return -3;
		}
		if(seats[seatNo].getPlayer()!=null){
			log.debug("坐位已被其它玩家先坐");
			return -2;
		}
		
		//占位成功
		try {
			lock.lock();
			seats[seatNo].setPlayer(player);
			if(this.status==TexasTableStatus.waitBet){
				seats[seatNo].setGiveUp(true);//在游戏中加入，认为是放弃了的玩家
			}else{
				seats[seatNo].setGiveUp(false);
			}
		} finally {
			lock.unlock();
		}
		cmdProcessor.handledCommand(new TexasCommand(player.toString()+"玩家已坐下在第"+seatNo+"坐位"));
		int isStart= tryStartGame();
		return isStart;
	}
	/**
	 * 尝试开始游戏
	 * @return  1成功开始游戏,2:条件不够，继续等待.
	 */
	protected int tryStartGame(){
		if(TexasTableStatus.waitPlayer==this.status){
			this.takenAwayUnEffectivePlayer();
			int playerNum=this.countEffectivePlayer();
			if(playerNum>1){
				//start game
				try{
					lock.lock();
					
					
					this.status=TexasTableStatus.waitBet;
				}finally{
					lock.unlock();
				}
				return 1;
			}
			
		}
		
		
		return 2;
	}
	protected int betSmallBig(){
		return 1;
	}
	
	/**
	 * 结束游戏，对玩家进行比较，并分配奖金。
	 * @return 1:已结束游戏，并完成奖金分配
	 */
	protected int endGame() {
		try{
			//TODO 比较玩家牌，并分配奖金
			
			lock.lock();
			
			this.status=TexasTableStatus.waitPlayer;
			clearTableStatus();
		}finally{
			lock.unlock();
		}
		return 1;
	}
	private int getPlayerSeatNo(TexasPlayer player) {
		for(int i=0;i<seats.length;i++){
			if(seats[i].getPlayer()!=null&&seats[i].getPlayer().getId().equals(player.getId())){
				return seats[i].getSeatNo();
			}
		}
		return -1;
	}
	
	public int standUp(TexasPlayer player) {
		
		int result=this.giveUp(player,true);
		
		return result;
	}
	public int bet(TexasPlayer player, long betNum){
		
		return 0;
	}
	
	public ITexasTable view() {
		log.debug("桌面ID:"+this.tableId);
		log.debug("桌面状态:"+this.status);
		log.debug("坐位人数："+this.getPlayerNum());
		StringBuilder playerSb=new StringBuilder();
		for(int i=0;i<this.seats.length;i++){
			playerSb.append("坐位").append(i).append("=");
			if(seats[i].getPlayer()!=null){
				playerSb.append(seats[i].getPlayer().getId());
				playerSb.append(":");
				playerSb.append(seats[i].getPlayer().getBankroll());
				playerSb.append("[");
				playerSb.append(PokerCard.getPokerCardsName(seats[i].getPlayer().getPokerHand().getUnNullPokers()));
				playerSb.append("]");
			}else{
				playerSb.append("空");
			}
			playerSb.append(",");
		}
		log.debug("玩家状态："+playerSb.toString());
		StringBuilder betSb=new StringBuilder();
		
//		for(Long key:this.betMap.keySet()){
//			betSb.append("奖池").append(key).append("[");
//			for(TexasPlayer player:this.betMap.get(key)){
//				betSb.append(player.getId()).append("、");
//			}
//			betSb.append("\n\r");
//		}
		log.debug("奖池状态：");
		log.debug(betSb.toString());
		
		return this;
	}
	private int getPlayerNum() {
		int playerNum=0;
		for(int i=0;i<seats.length;i++){
			if(seats[i].getPlayer()!=null){
				playerNum++;
			}
		}
		return playerNum;
	}
	
	public int giveUp(TexasPlayer player) {
		return giveUp(player,false);
	}
	/**
	 * 玩家放弃游戏
	 * @param player 玩家
	 * @param leave  放弃后，是否离开桌面
	 * @return
	 */
	public int giveUp(TexasPlayer player,boolean leave) {
		int seatNo=getPlayerSeatNo(player);
		if(seatNo==-1){
			return -1;
		}
		try {
			lock.lock();
			seats[seatNo].setGiveUp(true);
			if(leave){
				seats[seatNo].setPlayer(null);
			}
		} finally {
			lock.unlock();
		}
		int playerNum=countEffectivePlayer();
		if(this.status==TexasTableStatus.waitBet&&playerNum<2){
			this.endGame();
			this.tryStartGame();
			
			return 2;
		}else{
			return 1;
		}
	}
	/**
	 * 计算有效玩家数量
	 * @return
	 */
	private int countEffectivePlayer(){
		int effectivePlayer=0;
		for(int i=0;i<seats.length;i++){
			if(seats[i].getPlayer()!=null&&!seats[i].isGiveUp()){
				effectivePlayer++;
			}
		}
		return effectivePlayer;
	}
	/**
	 * 踢除盲注不够玩家
	 * @return 踢除数量
	 */
	private int takenAwayUnEffectivePlayer(){
		int takenNum=0;
		for(int i=0;i<seats.length;i++){
			if(seats[i].getPlayer()!=null&&seats[i].getPlayer().getBankroll()<config.getBigBlinds()){
				try {
					lock.lock();
					seats[i].setPlayer(null);
					seats[i].setGiveUp(true);
				} finally {
					lock.unlock();
				}
				takenNum++;
				log.debug("玩家已被踢除");
				//cmdProcessor.handledCommand(new TexasCommand(player.toString()+"玩家已坐下在第"+seatNo+"坐位"));
			}
		}
		return takenNum;
	}
	
	
}

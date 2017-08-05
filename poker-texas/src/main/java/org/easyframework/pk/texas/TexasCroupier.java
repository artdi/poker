package org.easyframework.pk.texas;

import org.easyframework.pk.command.ICommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasCroupier extends TexasTable{
	private static final Logger log=LoggerFactory.getLogger(TexasCroupier.class);
	
	private static final int[] SendCard={2,3,1,1};
	private int sendCardIndex=0;
	
	private volatile int dealerSeatNo=0;
	private volatile int betWatingNo;
	
	
	public TexasCroupier(TexasCroupierConfig config,
			ICommandProcessor cmdProcessor) {
		super(config, cmdProcessor);
	}
	@Override
	protected int betSmallBig() {
		//find next Dealer
		this.dealerSeatNo=nextEffectivePlayer(dealerSeatNo);
		//小盲注
		this.betWatingNo=nextEffectivePlayer(dealerSeatNo);
		this.bet(seats[betWatingNo].getPlayer(), config.getSmallBlinds());
		//大盲注
		this.betWatingNo=nextEffectivePlayer(dealerSeatNo);
		this.bet(seats[betWatingNo].getPlayer(), config.getBigBlinds());
		// 下一玩家
		this.betWatingNo=nextEffectivePlayer(dealerSeatNo);
		return 1;
	}
	@Override
	public int bet(TexasPlayer player, long betNum) {
		log.debug("子类打印的");
		return 1;
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

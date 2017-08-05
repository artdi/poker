package org.easyframework.pk.texas;
/**
 * 
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月27日 下午11:07:12
 */
public class TexasSeat {
	private int seatNo;
	//private TexasSeat preSeat;
	private TexasSeat nextSeat;
	private boolean isDealer;
   
	private boolean giveUp;
	private TexasPlayer player;
	
	public TexasSeat(int seatNo){
		this.seatNo=seatNo;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public TexasSeat getNextSeat() {
		return nextSeat;
	}

	public void setNextSeat(TexasSeat nextSeat) {
		this.nextSeat = nextSeat;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public boolean isGiveUp() {
		return giveUp;
	}

	public void setGiveUp(boolean giveUp) {
		this.giveUp = giveUp;
	}

	public TexasPlayer getPlayer() {
		return player;
	}

	public void setPlayer(TexasPlayer player) {
		this.player = player;
	}
	
	
	
	
	
}

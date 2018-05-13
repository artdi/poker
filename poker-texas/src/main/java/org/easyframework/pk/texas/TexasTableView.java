package org.easyframework.pk.texas;

public class TexasTableView implements ITexasTableView {

	private TexasCroupier table;
	public TexasTableView (TexasCroupier table){
		this.table=table;
	}
	
	public TexasTableStatus getStatusName() {
		
		return table.getStatusName();
	}

	public int getPlayerNum() {
		return table.getPlayerNum();
	}

	public int getPlayerPokerNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPlayerPokerNum(int seatNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getEffectivePlayer() {
		
		return table.getEffectivePlayer();
	}
	/**
	 * 获取本局各坐位玩家下注量
	 * @return
	 */
	public long[] getBets(){
		return this.table.jackpot.getBets();
	}
	/**
	 * 获取本轮玩家下注情况
	 * @param sendCardIndex
	 * @return
	 */
	public long[] getBets(int sendCardIndex){
		return this.table.jackpot.getBets(sendCardIndex);
	}
	/**
	 * 获取玩家当前所剩金额
	 * @return
	 */
	public long[] getPlayerBankroll(){
		long[] bankrolls=new long[this.table.seats.length];
		for(int i=0;i<this.table.seats.length;i++){
			if(this.table.seats[i]!=null&&this.table.seats[i].getPlayer()!=null){
				bankrolls[i]=this.table.seats[i].getPlayer().getBankroll();
			}else{
				bankrolls[i]=0;
			}
			
		}
		return bankrolls;
	}
	/**
	 * 车轮应该谁下注
	 * @return
	 */
	public int getBetWatingNo(){
		return this.table.getBetWatingNo();
	}
	public long getGameId(){
		return this.table.getGameId();
	}
	

}

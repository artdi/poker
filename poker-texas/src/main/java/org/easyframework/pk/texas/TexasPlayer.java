package org.easyframework.pk.texas;
/**
 * 德州扑克玩家
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:18:18
 */
public class TexasPlayer {
	private String id;
    /**
     * 玩家游戏中的状态
     */
	private  TexasPlayerStatus Status;
	/**
	 * 游戏中的筹码数
	 */
	private long bankroll;
	
	private TexasPlayer prePlayer;
	private TexasPlayer nextPlayer;
	private boolean isDealer;
	private boolean seated;
	private TexasPokerHand pokerHand=new TexasPokerHand();
	
	
	public TexasPlayer getPrePlayer() {
		return prePlayer;
	}

	public void setPrePlayer(TexasPlayer prePlayer) {
		this.prePlayer = prePlayer;
	}

	public TexasPlayer getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(TexasPlayer nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public TexasPlayer(String id){
		this.id=id;
		prePlayer=null;
		nextPlayer=null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TexasPlayerStatus getStatus() {
		return Status;
	}
	public void setStatus(TexasPlayerStatus status) {
		Status = status;
	}
	public long getBankroll() {
		return bankroll;
	}
	public void setBankroll(long bankroll) {
		this.bankroll = bankroll;
	}

	public boolean isSeated() {
		return seated;
	}

	public void setSeated(boolean seated) {
		this.seated = seated;
	}

	public TexasPokerHand getPokerHand() {
		return pokerHand;
	}

	public void setPokerHand(TexasPokerHand pokerHand) {
		this.pokerHand=pokerHand;
	}
	
}


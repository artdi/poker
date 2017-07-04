package org.easyframework.pk;

/**
 * 一张扑克牌
 * <br>
 * 1=Ace,2=2,11=Jack,12=Queen,13=king,14=BlackJoker 15=ReadJoker
 * <br>
 * 0=方块,1=梅花,2=红桃,3=黑桃
 * <br>
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午6:10:25
 */
public class PokerCard {
	public final static int WEEK_ACE=1;
	/**
	 * 最小顺子的值，表示Ace,2,3,4,5
	 */
	public final static int MINE_STRAIGHT=62;
	public final static int MAX_STRAIGHT=MINE_STRAIGHT<<5;
	/**
	 * 表示无效牌
	 */
	public final static int UNINITCARD_ID=-1;
	
	private int id=UNINITCARD_ID;
	private int week=1;  //表示点数 1:Ace  13:King  14:BlackJoker 15:ReadJoker
	private int session=0;  //表示花色
	private String name;
	private final static String[] weekName={"","Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
	private final static String[] sessionName={"方块","梅花","红桃","黑桃"};
	
	
	public int getId() {
		return id;
	}

	public int getWeek() {
		return week;
	}

	public int getSession() {
		return session;
	}

	public String toString(){
		return this.name;
	}
	/**
	 * 
	 * @param week 1=Ace,2=2,11=Jack,12=Queen,13=king,14=BlackJoker 15=ReadJoker
	 * @param session 0=方块,1=梅花,2=红桃,3=黑桃
	 */
	public PokerCard(int week,int session){
		
		if(week>0 && week< 16 && session>=0 && session <4){
			this.week=week;
			this.session=session;
			this.name=getName();
			this.id=1<<(this.week+4)|1<<this.session;
		}else{
			throw new RuntimeException("非法扑克牌点数,week:"+week+",session"+session);
		}
	}
	public String getName(){
		String name="";
		if(this.week==14){
			name= "小王";
		}else if(this.week==15){
			name= "大王";
		}else{
			name=sessionName[this.session]+weekName[this.week];
		}
		return name;
	}
	/**
	 * 按点数花色创建一摞牌。
	 * @param cards  ｛点数，花色，点数，花色。。。。｝
	 * @return
	 */
	public static PokerCard[] createPokerCardArray(int[] cards){
		if(null!=cards&&cards.length/2>0){
			PokerCard[] pokerCards=new PokerCard[cards.length/2];
			int n=0;
			for(int i=0;i<cards.length;i=i+2){
				pokerCards[n++]=new PokerCard(cards[i],cards[i+1]);
			}
			return pokerCards;
		}else{
			throw new RuntimeException("非法扑克牌点数,cards:"+cards);
		}
	}
	public static String getPokerCardsName(PokerCard[] pokers){
		StringBuilder sb=new StringBuilder();
		for(PokerCard poker:pokers){
			if(poker!=null)
			sb.append(poker.getName()).append(",");
		}
		return sb.toString();
	}
	/**
	 * 创建一副52张牌的
	 * @return
	 */
	public static PokerCard[] create52PokerCard(){
		PokerCard[] pokerCards=new PokerCard[52];
		int index=0;
		for(int session=0;session<4;session++){
			for(int week=1;week<14;week++){
				pokerCards[index]=new PokerCard(week,session);
				index++;
			}
		}
		return pokerCards;
	}
	
}

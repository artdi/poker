package org.easyframework.pk;

/**
 * 一张poker牌，用二进制表示
 * <br>
 * 0b0001表示方块  
 * 0b0010表示黑花
 * 0b0100表示红挑
 * 0b1000表示黑挑
 * 0b0000 0000 0001 0001
 * 
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午6:10:25
 */
public class PokerCard {
	/**
	 * 最小顺子的值，表示Ace,2,3,4,5
	 */
	public final static int MINE_STRAIGHT=62;
	public final static int MAX_STRAIGHT=MINE_STRAIGHT<<5;
	
	private int id=-1;
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
	
	public PokerCard(int week,int session){
		
		if(week>0 && week< 16 && session>=0 && session <4){
			this.week=week;
			this.session=session;
			this.name=getName();
			this.id=1<<(this.week+4)&1<<this.session;
		}else{
			throw new RuntimeException("非法扑克牌点数,week:"+week+",session"+session);
		}
	}
	private String getName(){
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
	
	
	/*
	public static final int Club=0x01;
	public static final int Diamond=0x01<<1;
	public static final int Heart=0x01<<2;
	public static final int Spade=0x01<<3;
	
	public static final int ACE=0x01<<4;
	public static final int One=0x01<<5;
	public static final int Two=0x01<<6;
	public static final int Three=0x01<<7;
	public static final int Four=0x01<<8;
	public static final int Five=0x01<<9;
	public static final int Six=0x01<<10;
	public static final int Seven=0x01<<11;
	public static final int Eight=0x01<<12;
	public static final int Nine=0x01<<13;
	public static final int Ten=0x01<<14;
	public static final int Jack=0x01<<15;
	public static final int Queen=0x01<<16;
	public static final int King=0x01<<17;
	
	public static final int BlackJoker=0x01<<18;
	public static final int ReadJoker=0x01<<19;
	
	
	private int value=1;
	public int getValue(){
		return value;
	}
	public String toString(){
		return "";
	}
	public PokerCard(int v){
		this.value=v;
	}*/
}

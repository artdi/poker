package org.easyframework.pk.texas;
/**
 * 玩家手中的大牌位置及牌值。<br>
 * 牌型大小：<br>
1、皇家同花顺＞同花顺＞四条＞葫芦＞同花＞顺子＞三条＞两对＞一对＞高牌<br>
2、牌点从大到小为：A、K、。。。2，各花色不分大小。<br>
3、同种牌型，对子时比对子的大小，其它牌型比最大的牌张，如最大牌张相同则比第二大
的牌张，以此类推，都相同时为相同。
<br>

 * @author Artdi
 * @date 2017年7月4日 下午4:37:19  
 * @version V1.0
 */
public class TexasPokerHandPoint {

	/**
	 * 
	 * 同花顺＝同花顺权重＋顺子最大点  ＊
	 * 四条＝四条权重＋四条点数
	 * 葫芦＝葫芦权重＋三条点数
	 * 同花＝同花权重＋同花点数       ＊
	 * 顺子＝顺子权重＋顺子最大点     ＊
	 * 三条＝三条权重＋三条点数
	 * 两对＝两对权重＋两对点数＋最大点数   ＊
	 * 一对＝一对权重＋一对点数＋最大点数＋最大点数＋最大点数  ＊
	 * 高牌＝最大点数＋最大点数＋最大点数＋最大点数＋最大点数  ＊
	 */
	//private final static long hightPointWeight=0x1;
	private final static long pairWeight=0x1<<32;
	private final static long threeWeight=0x1<<48;
	private final static long straightWeight=0x1<<49;
	private final static long flushWeight=0x1<<50;
	private final static long fullHouseWeight=0x1<<51;
	private final static long fourWeight=0x1<<52;
	private final static long flushStraightWeight=0x1<<53;
	
	public final static int MINI_STRAIGHT_WEIGHT=62;//0b111110=62; 表示最小顺子
	
	private boolean isFlushStraight=false;
	private boolean isFlush=false;
	private boolean isStraight=false;
	private boolean isFour=false;
	private boolean isFullHouse=false;
	private boolean isThree=false;
	private boolean isTwoPair=false;
	private boolean isPair=false;
	private boolean isSingle=false;
	
	
	private int weight=0;
	private int[] maxPoint={-1,-1,-1,-1,-1};
	
	public int[] getMaxPoint() {
		long a=Long.MAX_VALUE;
		return maxPoint;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	public boolean isFlushStraight() {
		return isFlushStraight;
	}

	public void setFlushStraight(boolean isFlushStraight) {
		this.isFlushStraight = isFlushStraight;
	}

	public boolean isFlush() {
		return isFlush;
	}

	public void setFlush(boolean isFlush) {
		this.isFlush = isFlush;
	}

	public boolean isStraight() {
		return isStraight;
	}

	public void setStraight(boolean isStraight) {
		this.isStraight = isStraight;
	}

	public boolean isFour() {
		return isFour;
	}

	public void setFour(boolean isFour) {
		this.isFour = isFour;
	}

	public boolean isThree() {
		return isThree;
	}

	public void setThree(boolean isThree) {
		this.isThree = isThree;
	}

	public boolean isTwoPair() {
		return isTwoPair;
	}

	public void setTwoPair(boolean isTwoPair) {
		this.isTwoPair = isTwoPair;
	}

	public boolean isPair() {
		return isPair;
	}

	public void setPair(boolean isPair) {
		this.isPair = isPair;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public boolean isFullHouse() {
		return isFullHouse;
	}

	public void setFullHouse(boolean isFullHouse) {
		this.isFullHouse = isFullHouse;
	}



}

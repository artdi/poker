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
	 * 同花顺＝同花顺权重＋顺子最大点
	 * 四条＝四条权重＋四条点数
	 * 葫芦＝葫芦权重＋三条点数
	 * 同花＝同花权重＋同花最大点
	 * 顺子＝顺子权重＋顺子最大点
	 * 三条＝三条权重＋三条点数
	 * 两对＝两对权重＋两对点数＋最大点数
	 * 一对＝一对权重＋一对点数＋最大点数＋最大点数＋最大点数
	 * 高牌＝最大点数＋最大点数＋最大点数最大点数＋最大点数
	 */
	private int weight=0;
	private int[] maxPoint={-1,-1,-1,-1,-1};
	
	public int[] getMaxPoint() {
		return maxPoint;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	


}

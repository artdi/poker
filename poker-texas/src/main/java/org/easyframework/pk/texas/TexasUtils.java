package org.easyframework.pk.texas;

import java.util.Arrays;

import org.easyframework.pk.PokerCard;

/**
 * 德州扑克工具
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午6:06:05
 */
public class TexasUtils {
	
	
	/**
	 * 对扑克牌由大到小排序,ace最大
	 * @param pokers
	 * @return
	 */
	public static PokerCard[] sortPokerCard(PokerCard[] pokers){
		Arrays.sort(pokers, new TexasPokerCardComparator());
		return pokers;
	}
	/**
	 * 查找pokers中的顺子，ace,2，3，4，5最小，10,11,12,13,ace最大
	 * @param pokers  
	 * @return  组成最大顺子的牌所在位置，及顺子值。如果无顺子则反回空
	 */
	public static TexasPokerHandPoint findStraight(PokerCard[] pokers){
		if(pokers!=null&&pokers.length>=5){
			TexasPokerHandPoint p = new TexasPokerHandPoint();
			int tempValue=0;
			for(int i=0;i<pokers.length;i++){
				if(pokers[i].getWeek()==1){ //ＡＣＥ 可前后连接成顺子
					tempValue=tempValue|(1<<14);
				}
				tempValue=tempValue|(1<<pokers[i].getWeek());
			}
			int straight=62;//0b111110=62; 表示最小顺子
			for(int i=0;i<10;i++){
				if((tempValue|~straight)==-1){//是顺子   -1 is 11111111111111111111111111111111
					p.setWeight(straight);//记录顺子牌型值
				}
				straight=straight<<1;
			}
			if(p.getWeight()>0){
				return p;
			}
		}
		return null;
	}
	/**
	 * 计算一手牌的最大牌型
	 * @param pokerHand
	 */
	public void countValue(TexasPokerHand pokerHand){
		
	}

}

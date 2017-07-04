package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;

/**
 * 德州扑克工具
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午6:06:05
 */
public class TexasUtils {
	/**
	 * 查找pokers中的顺子，
	 * @param pokers  
	 * @return  组成最大顺子的牌所在位置，及顺子值
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
					p.setWight(straight);//记录顺子牌型值
				}
				straight=straight<<1;
			}
			if(p.getWight()>0){
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

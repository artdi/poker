package org.easyframework.pk.texas;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
	 * 查找同花色pokers中的顺子，ace,2，3，4，5最小，10,11,12,13,ace最大
	 * @param pokers  
	 * @return  组成最大顺子的牌所在位置，及顺子值。如果无顺子则反回空
	 */
	public static TexasPokerHandPoint findStraight(PokerCard[] pokers){
		//TODO pokers 里不能有空值
		if(pokers!=null&&pokers.length>=5){
			TexasPokerHandPoint p = new TexasPokerHandPoint();
			int tempValue=0;
			for(int i=0;i<pokers.length;i++){
				if(pokers[i].getWeek()==PokerCard.WEEK_ACE){ //ＡＣＥ 可前后连接成顺子
					tempValue=tempValue|(1<<14);
				}
				tempValue=tempValue|(1<<pokers[i].getWeek());
			}
			int straight=TexasPokerHandPoint.MINI_STRAIGHT_WEIGHT;//0b111110=62; 表示最小顺子
			for(int i=0;i<10;i++){
				if((tempValue|~straight)==-1){//是顺子   -1 is 11111111111111111111111111111111
					p.setStraight(true);
					p.setWeight(straight);//记录顺子牌型值
				}
				straight=straight<<1;
			}
			if(p.isStraight()){
				//FIXME 会漏先同样点数，但不同花色的牌。如 4、方块5、红桃5、黑桃5、6、7、8，红桃5、黑桃5会漏选。但算法中先判断花色，再判断顺子时，不影响结果。
				//记录选取的是那些牌，
				int selectIndex=0;
				for(int i=0;i<pokers.length;i++){
					int week=pokers[i].getWeek();
					int weekValue=1<<week;
					if(week==PokerCard.WEEK_ACE){
						weekValue=weekValue|1<<14;
					}
					if((weekValue&p.getWeight())>0){
						p.getMaxPoint()[selectIndex++]=i;
					}
				}
				return p;
			}else{
				//p.setSingle(true);//不一定是单牌，有可能存在一对或三张，
				//由大到小选取远素
				int singleWeight=0;
				int selectIndex=0;
				for(int week=14;week>1;week--){
					int weekValue=1<<week;
					if((weekValue&tempValue)>0){
						for(int i=0;i<pokers.length;i++){
							if(pokers[i].getWeek()==week){
								singleWeight=singleWeight|week;
								p.getMaxPoint()[selectIndex++]=i;
								break;
							}
						}
					}
					if(selectIndex>4){
						break;
					}
				}
				p.setWeight(singleWeight);
				return p;
			}
		}
		return null;
	}
	/**
	 * 计算一手牌的最大牌型
	 * @param pokerHand
	 */
	public static TexasPokerHand countValue(TexasPokerHand pokerHand){
		 PokerCard[] maxCard=new PokerCard[5];
		 List<Integer> theSameWeekCardPoint;
		 PokerCard[] pokers=pokerHand.getPokers();
		 
		 int[] maxPoint={-1,-1,-1,-1,-1};
		 
		 List<Integer>[] weekNum=new LinkedList[15];
		 
		 List<Integer>[] sessionNum=new LinkedList[4];
		 long weekValues=0;
		 
		 int maxSameWeek=0;
		 int secondSameWeek=0;
		 int maxSameWeeNum=0;//最大相同张数 1-4
		 int maxSameSessionNum=0;//最多花色的数量
		 int maxSession=-1;//最多的花色
		 int maxWeek=0;//最大点数 ACE 14点最大
		 //int 
		 for(int i=0;i<pokers.length;i++){
			 //记录算点数及顺子
			 int weekValue=0;
			 int week=pokers[i].getWeek();
			 if(PokerCard.WEEK_ACE==week){
				 week=14;
				 weekValue=(1<<week)|(1<<PokerCard.WEEK_ACE);//增加一位，方便记录顺子
			 }else{
				 weekValue=(1<<week);
			 }
			 
			 if((weekValues&(~weekValue))>0){
				 if(weekNum[pokers[i].getWeek()]==null){
					 weekNum[pokers[i].getWeek()]=new LinkedList<Integer>();
				 }
				 weekNum[pokers[i].getWeek()].add(i);
				 //weekNum[pokers[i].getWeek()]=weekNum[pokers[i].getWeek()]+1;
				 //记录最高有几张相同
				 if(weekNum[pokers[i].getWeek()].size()>maxSameWeeNum){
					 maxSameWeeNum=weekNum[pokers[i].getWeek()].size();
				 }
			 }
			 //保存记录
			 weekValues=weekValues|weekValue;
			 //记录花色
			 if(sessionNum[pokers[i].getSession()]==null){
				 sessionNum[pokers[i].getSession()]=new LinkedList<Integer>(); 
			 }
			 sessionNum[pokers[i].getSession()].add(i);
			 if(sessionNum[pokers[i].getSession()].size()>maxSameSessionNum){
				 maxSameSessionNum=sessionNum[pokers[i].getSession()].size();
				 maxSession = pokers[i].getSession();
			 }
		 }
		 //计算四条
		/* 同花顺＝同花顺权重＋顺子最大点  ＊
		 * 四条＝四条权重＋四条点数
		 * 葫芦＝葫芦权重＋三条点数
		 * 同花＝同花权重＋同花点数       ＊
		 * 顺子＝顺子权重＋顺子最大点     ＊
		 * 三条＝三条权重＋三条点数
		 * 两对＝两对权重＋两对点数＋最大点数   ＊
		 * 一对＝一对权重＋一对点数＋最大点数＋最大点数＋最大点数  ＊
		 * 高牌＝最大点数＋最大点数＋最大点数＋最大点数＋最大点数  */
		 
		 //最算最大的同花  包括同花顺
		 if(maxSameSessionNum>4){
			 
			 //计算同花里有无顺子
			 List<Integer> flushPokesList=sessionNum[maxSession];
			 PokerCard[] flushPokes=new PokerCard[flushPokesList.size()];
			 for(int i=0;i<flushPokes.length;i++){
				 flushPokes[i]=pokers[flushPokesList.get(i)];
			 }
			 TexasPokerHandPoint maxFlushHandPoint=findStraight(flushPokes);
			 //坐标的转换
			 int selectPoint=0;
			 for(int i=0;i<maxFlushHandPoint.getMaxPoint().length;i++){
				 for(int j=0;j<pokers.length;j++){
					 if(pokers[j].getId()==flushPokes[maxFlushHandPoint.getMaxPoint()[i]].getId()){
						 pokerHand.getMaxPoint()[selectPoint++]=j;
						 break;
					 }
				 }
			 }
			 if(maxFlushHandPoint.isStraight()){
				 pokerHand.setFlushStraight(true);
			 }else{
				 pokerHand.setFlush(true);
			 }
		 }else if(true){//计算四条
			 
		 }//计算葫芦
		 
		 
		 //计算最大的顺子
		 
		 return pokerHand;
	}

}

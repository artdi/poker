package org.easyframework.pk.texas;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.easyframework.pk.PokerCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 德州扑克工具
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午6:06:05
 */
public class TexasUtils {
	private static Logger log=LoggerFactory.getLogger(TexasUtils.class);

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
	 * 查找<b>*同花色*</b>pokers中的顺子，ace,2，3，4，5顺子最小，10,11,12,13,ace顺子最大
	 * <br>如无顺子，则反回最大点数的组合。
	 * <br><b>注意:当不是同花，不是顺子时，不能用些方法反回最大牌型。
	 * @param pokers  
	 * @return  TexasPokerHandPoint 组成最大顺子的牌所在位置，及顺子值。否则反回TexasPokerHandPoint.isStraight=false;
	 */
	private static TexasPokerHandPoint findStraight(PokerCard[] pokers){
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
				int straightValues=p.getWeight();
				for(int i=0;i<pokers.length;i++){
					int week=pokers[i].getWeek();
					int weekValue=1<<week;
					if(week==PokerCard.WEEK_ACE){
						weekValue=weekValue|1<<14;
					}
					if((weekValue&straightValues)>0){
						p.getMaxPoint()[selectIndex++]=i;
						straightValues=straightValues&(~weekValue);//会选取重复值，选择后的点数不再选择
					}
				}
				return p;
			}else{
				//由大到小选取远素
				int singleWeight=0;
				int selectIndex=0;
				for(int week=14;week>1;week--){
					int weekValue=1<<week;
					if((weekValue&tempValue)>0){
						for(int i=0;i<pokers.length;i++){
							int pokerWeek=pokers[i].getWeek();
							if(pokerWeek==PokerCard.WEEK_ACE){
								pokerWeek=14;
							}
							if(pokerWeek==week){
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
		 PokerCard[] pokers=pokerHand.getPokers();
		 List<Integer>[] weekNum=new LinkedList[15];
		 List<Integer>[] sessionNum=new LinkedList[4];
		 
		 long weekValues=0;// 记录是否存在的点数牌
		 
		 int maxSameWeeNum=1;//最大相同张数 1-4
		 int maxSameSessionNum=0;//最多花色的数量
		 int maxSession=-1;//最多的花色

		 for(int i=0;i<pokerHand.getCardsNum();i++){
			 //记录算点数及顺子
			 int weekValue=0;
			 int week=pokers[i].getWeek();
			 if(PokerCard.WEEK_ACE==week){
				 week=14;
				 weekValue=(1<<week)|(1<<PokerCard.WEEK_ACE);//增加一位，方便记录顺子
			 }else{
				 weekValue=(1<<week);
			 }
			 
			 if(weekNum[week]==null){
				 weekNum[week]=new LinkedList<Integer>();
			 }
			 weekNum[week].add(i);
			 
			//记录最高有几张相同
			if(weekNum[week].size()>maxSameWeeNum){
				maxSameWeeNum=weekNum[week].size();
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
			//count flush Value
			 long baseValue=0;
			 if(maxFlushHandPoint.isStraight()){
				 pokerHand.setFlushStraight(true);
				 baseValue=TexasPokerHandPoint.flushStraightWeight;
			 }else{
				 pokerHand.setFlush(true);
				 baseValue=TexasPokerHandPoint.flushWeight;
			 }
			 int weight=maxFlushHandPoint.getWeight();
			 
			 pokerHand.setValue(baseValue|weight);
			 
		 }else if(maxSameWeeNum==4){//计算四条
			 pokerHand.setFour(true);
			 int haveSelect=0;
			 //选择四条
			 for(int week=14;week>0;week--){
				 if(weekNum[week]!=null&&weekNum[week].size()==4){
					 for(Integer p: weekNum[week]){
						pokerHand.getMaxPoint()[haveSelect++]=p;
					 }
					 weekNum[week]=null;
					 //count four kind Value
					 pokerHand.setValue(TexasPokerHandPoint.fourWeight|week);
				 }
			 }
			 //选择另一条最大的 
			 for(int week=14;week>0;week--){
				 if(weekNum[week]!=null){
					 pokerHand.getMaxPoint()[haveSelect++]=weekNum[week].get(0);
					 break;
				 }
			 }
			 
		 }else if(maxSameWeeNum==3){//计算葫芦 或三条
			 
			 int haveSelect=0;
			 int threeWeek=0;
			 //选择三条
			 for(int week=14;week>0;week--){
				 if(weekNum[week]!=null&&weekNum[week].size()==3){
					 for(Integer p: weekNum[week]){
						if(haveSelect>4){
							pokerHand.setFullHouse(true);
							break;
						}
						pokerHand.getMaxPoint()[haveSelect++]=p;
					 }
					 weekNum[week]=null;
					 threeWeek=week;
				 }
				 if(haveSelect>4){
						break;
				 }
			 }
			 //先选择是否有两条以上的，有则从中选择最大的两条 
			 for(int week=14;week>0;week--){
				 if(weekNum[week]!=null&&weekNum[week].size()==2){
					 for(Integer p: weekNum[week]){
						 if(haveSelect>4){
								break;
						}
						pokerHand.getMaxPoint()[haveSelect++]=p;
					 }
					 weekNum[week]=null;
					 pokerHand.setFullHouse(true);
					 //count fullhouse value
					 pokerHand.setValue(TexasPokerHandPoint.fullHouseWeight|threeWeek);
				 }
			 }
			 //如果不是葫芦，选择另两条最大的单牌 
			 if(!pokerHand.isFullHouse()){
				 for(int week=14;week>0;week--){
					 if(haveSelect>4){
						 break;
					 }
					 if(weekNum[week]!=null){
						 pokerHand.getMaxPoint()[haveSelect++]=weekNum[week].get(0);
					 }
				 }
				 pokerHand.setThree(true);
				//count three value
				 pokerHand.setValue(TexasPokerHandPoint.threeWeight|threeWeek);
			 }
		 }else if(maxSameWeeNum==2){  //计算对子
			 int haveSelect=0;
			 long pairValue=0;
			 //选择两条
			 for(int week=14;week>0;week--){
				 if(weekNum[week]!=null&&weekNum[week].size()==2){
					 if(pokerHand.isPair()){
						 pokerHand.setTwoPair(true);
					 }else{
						 pokerHand.setPair(true);
					 }
					 for(Integer p: weekNum[week]){
						if(haveSelect>4){
							break;
						}
						pokerHand.getMaxPoint()[haveSelect++]=p;
					 }
					 weekNum[week]=null;
					 //count pair value
					 pairValue=pairValue|(1L<<(week+TexasPokerHandPoint.PAIR_WEIGHT_BASE_POINT));
				 }
				 if(haveSelect>3){//选两对后跳出，最后一条只选最大点数的
						break;
				 }
			 }
			 for(int week=14;week>0;week--){
				 if(haveSelect>4){
					 break;
				 }
				 if(weekNum[week]!=null){
					 pokerHand.getMaxPoint()[haveSelect++]=weekNum[week].get(0);
					 //单牌只记录点数
					 pairValue=pairValue|week;
				 }
			 }
			 if(pokerHand.isTwoPair()){
				 pairValue=pairValue|TexasPokerHandPoint.towPairWeight;
			 }
			 pokerHand.setValue(pairValue);
		 }else {//计算单牌
			 int haveSelect=0;
			 for(int week=14;week>0;week--){
				if(haveSelect>4){
					 break;
				 }
				 if(weekNum[week]!=null){
					 pokerHand.getMaxPoint()[haveSelect++]=weekNum[week].get(0);
				 }
			 }
			 pokerHand.setSingle(true);
			 //单牌的值
			 pokerHand.setValue(weekValues);
		 }
		 //计算顺子
		 if(pokerHand.isThree()||pokerHand.isTwoPair()||pokerHand.isPair()||pokerHand.isSingle()){
			 TexasPokerHandPoint straightPokerHand = TexasUtils.findStraight(pokerHand.getUnNullPokers());
			 if(straightPokerHand!=null&&straightPokerHand.isStraight()){
				 pokerHand.setStraight(true);
				 for(int i=0;i<straightPokerHand.getMaxPoint().length;i++){
					 pokerHand.getMaxPoint()[i]=straightPokerHand.getMaxPoint()[i];
				 }
				 //count Straight value
				 pokerHand.setValue(TexasPokerHandPoint.straightWeight|weekValues);
			 }
		 }
		 
		 
		 return pokerHand;
	}

}

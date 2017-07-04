package org.easyframework.pk.texas;

import java.util.Comparator;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.texas.exception.TexasException;

public class TexasPokerCardComparator implements Comparator<PokerCard> {

	public int compare(PokerCard o1, PokerCard o2) {
		if(o1==null||o2==null||PokerCard.UNINITCARD_ID==o1.getId()||PokerCard.UNINITCARD_ID==o2.getId()){
			throw new TexasException(101,"比较无效的牌型",null);
		}
		int value1=o1.getId();
		int value2=o2.getId();
		//设置ACE为点数最大的牌
		if(PokerCard.WEEK_ACE==o1.getWeek()){
			value1=value1<<14;
		}
		if(PokerCard.WEEK_ACE==o2.getWeek()){
			value2=value2<<14;
		}
		
		if(value1==value2){
			return 0;
		}else {
			return value1>value2 ? 1:-1;
		}
	}

}

package org.easyframework.pk.texas;

import java.util.ArrayList;
import java.util.List;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.texas.exception.TexasException;

/**
 * 一个玩家一手的牌，包括底牌和公共牌
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月3日 下午9:57:19
 */
public class TexasPokerHand extends TexasPokerHandPoint{
	
	
	private int cardsNum=0;
	
	private PokerCard[] pokers=new PokerCard[7];
	
	/**
	 * 返回前几张非空的牌
	 * @return
	 */
	public PokerCard[] getUnNullPokers(){
		PokerCard[] unNullPokers=new PokerCard[this.cardsNum];
		for(int i=0;i<unNullPokers.length;i++){
			unNullPokers[i]=this.pokers[i];
		}
		return unNullPokers;
	}
	
	public PokerCard[] getMaxPokers(){
		PokerCard[] maxPokers=new PokerCard[5];
		for(int i=0;i<5;i++){
			int index=this.getMaxPoint()[i];
			if(index>=0){
				maxPokers[i]=this.pokers[index];
			}else{
				break;
			}
		}
		
		return  maxPokers;
	}
	public PokerCard[] getPokers(){
		return this.pokers;
	}
	/**
	 * 派玩家低牌
	 * @param priPokers  必须是两张有效的牌，而且要先派这两张
	 */
	public void addPriPokers(PokerCard[] priPokers){
		if(this.cardsNum==0){
			this.cardsNum=2;
			this.pokers[0]=priPokers[0];
			this.pokers[1]=priPokers[1];
		}else{
			throw new TexasException(101,"玩家当前不能接收低牌",null);
		}
	}
	public void addFlop(PokerCard[] flop){
		if(2==this.cardsNum&&3==flop.length){
			this.cardsNum=5;
			pokers[2]=flop[0];
			pokers[3]=flop[1];
			pokers[4]=flop[2];
		}else{
			throw new TexasException(101,"玩家当前不能接收首三张公共牌",null);
		}
		
	}
	public void addTurn(PokerCard turn){
		if(5==this.cardsNum){
			this.cardsNum=6;
			this.pokers[5]=turn;
		}else{
			throw new TexasException(101,"玩家当前不能接收第四张公共牌",null);
		}
	}
	public void addRiver(PokerCard river){
		if(6==this.cardsNum){
			this.cardsNum=7;
			this.pokers[6]=river;
		}else{
			throw new TexasException(101,"玩家当前不能接收第五张公共牌",null);
		}
	}
	public int getCardsNum() {
		return cardsNum;
	}

	public void addPokers(PokerCard[] pokers) {
		if(pokers==null){
			throw new TexasException(101,"添加的牌不能为空",null);
		}
		for(PokerCard c:pokers){
			if(c==null){
				throw new TexasException(101,"添加的牌不能为空",null);
			}
		}
		if(this.cardsNum+pokers.length>7){
			throw new TexasException(101,"玩家当前已有"+this.cardsNum+"张牌，不能接收"+pokers.length+"张牌",null);
		}
		for(int i=0;i<pokers.length;i++){
			this.pokers[this.cardsNum++]=pokers[i];
		}
		
	}
	

}

package org.easyframework.pk.texas;

import java.util.List;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.command.ICommandProcessor;
import org.easyframework.pk.texas.command.TexasCommand;
import org.easyframework.pk.texas.exception.TexasException;

public class HkTexasCroupier extends TexasCroupierBase implements ITexasCroupier {

	private ICommandProcessor cmdProcessor;
	private static final TexasCroupierConfig config=new TexasCroupierConfig();
	private PokerCard[] allPokerCard=PokerCard.create52PokerCard();
	
	
	private List<TexasPlayer> viewers;
	private TexasPlayer[] players=new TexasPlayer[config.getMaxPlayer()];
	//private TexasPlayerStatus[] playerStatus=new TexasPlayerStatus[Max]
	
	
	public HkTexasCroupier(ICommandProcessor cmdProcessor){
		this.cmdProcessor=cmdProcessor;
		
		//allPokerCard.s
	}
	

	
	public ITexasCroupier sitDown(TexasPlayer player,Integer seatNo) throws TexasException {
		if(seatNo>config.getMaxPlayer()){
			throw new TexasException(102, "", null);
		}
		if(players[seatNo]==null){
			players[seatNo]=player;
		}else{
			
		}
		
		cmdProcessor.handledCommand(new TexasCommand(player.toString()+"玩家已坐下在第"+seatNo+"坐位"));
		return null;
	}

	
	public ITexasCroupier playerAddChip(TexasPlayer player, long betNum)
			throws TexasException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ITexasCroupier standUp(TexasPlayer player) throws TexasException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ITexasCroupier bet(TexasPlayer player, long betNum)
			throws TexasException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ITexasCroupier view() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}

}

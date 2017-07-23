package org.easyframework.pk.texas.command;

import java.util.HashMap;
import java.util.Map;

import org.easyframework.pk.command.ICommand;
import org.easyframework.pk.texas.HkTexasCroupier;
import org.easyframework.pk.texas.TexasPlayer;

public class TexasCommand implements ICommand {
	private Map param=new HashMap();
	private Object from=null;
	private Object to=null;
	public TexasCommand(String msg){
		param.put("paramStr", msg);
	}
	//上发命令
	public TexasCommand(TexasPlayer from,HkTexasCroupier to,Map param){
		this.param=param;
		this.from=from;
		this.to=to;
	}
	//下发命令
	public TexasCommand(HkTexasCroupier from,TexasPlayer to,Map param){
		this.param=param;
		this.from=from;
		this.to=to;
	}

	public int getCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map getCommandParam() {
		// TODO Auto-generated method stub
		return param;
	}

}

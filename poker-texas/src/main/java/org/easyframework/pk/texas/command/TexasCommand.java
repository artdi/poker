package org.easyframework.pk.texas.command;

import java.util.HashMap;
import java.util.Map;

import org.easyframework.pk.command.ICommand;

public class TexasCommand implements ICommand {
	private Map param=new HashMap();
	public TexasCommand(String msg){
		param.put("paramStr", msg);
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

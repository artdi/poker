package org.easyframework.pk.texas.command;

import java.util.Map;

import org.easyframework.pk.command.ICommand;
import org.easyframework.pk.command.ICommandProcessor;

public class TexasCommandProcessor implements ICommandProcessor{

	public void handledCommand(ICommand command) {
		Map param=command.getCommandParam();
		if(param!=null&&param.containsKey("paramStr")){
			System.out.println(param.get("paramStr"));
		}
	}

}

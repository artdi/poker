package org.easyframework.pk.texas.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.easyframework.pk.command.ICommand;
import org.easyframework.pk.command.ICommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasCommandProcessor implements ICommandProcessor{

	private static final Logger log=LoggerFactory.getLogger(TexasCommandProcessor.class);
	
	public List<ICommand> commands=new LinkedList<ICommand>();
	public void handledCommand(ICommand command) {
		commands.add(command);
		Map param=command.getCommandParam();
		if(param!=null&&param.containsKey("paramStr")){
			log.debug(""+param.get("paramStr"));
		}
	}
	public int getCommandNum(){
		return commands.size();
	}

}

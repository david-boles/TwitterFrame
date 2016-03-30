package space.davidboles.twitterframe.server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import space.davidboles.lib.ht.tp.ContextualHttpHandler;
import space.davidboles.lib.program.ProgramFs;

public class SubmitHandler extends ContextualHttpHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		String input = java.net.URLDecoder.decode(arg0.getRequestURI().toString(), "UTF-8");
		input = input.replaceFirst("/submit/", "");
		
		boolean like = input.startsWith("like?");
		if(like) input = input.replaceFirst("like?", "");
		else input = input.replaceFirst("dislike?", "");
		
		input = input.substring(5, input.length());
		
		//System.out.println(input);
		
		Photo target = Start.pMan.getPhoto(input);
		if(like) target.posVote++;
		else target.negVote++;
		ProgramFs.saveObject(Start.pMan.photoList, Start.pMan.photos);
		
		this.writeTextResponse(arg0, ProgramFs.loadString(ProgramFs.getProgramFile("web/forwarder.html")));
		
		Start.sLogger.log("Current state of pics:");
		Start.pMan.listAll();
	}

	@Override
	public String getURLPath() {
		return "/submit/";
	}

}

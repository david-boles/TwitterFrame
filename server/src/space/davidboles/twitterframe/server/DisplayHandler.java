package space.davidboles.twitterframe.server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import space.davidboles.lib.ht.tp.ContextualHttpHandler;
import space.davidboles.lib.program.ProgramFs;

public class DisplayHandler extends ContextualHttpHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		String displayHtml = ProgramFs.loadString(ProgramFs.getProgramFile("web/display.html"));
		displayHtml = displayHtml.replaceAll("!IMURLS!", Start.pMan.getPhotoString());
		this.writeTextResponse(arg0, displayHtml);
		//Start.sLogger.log("Display handled, refreshing database");
		Start.pMan.initialize();
	}

	@Override
	public String getURLPath() {
		return "/display.html";
	}

}

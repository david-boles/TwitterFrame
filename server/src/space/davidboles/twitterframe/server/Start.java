package space.davidboles.twitterframe.server;

import space.davidboles.lib.ht.tp.FolderHttpHandler;
import space.davidboles.lib.ht.tp.HTTPServerSimpleManager;
import space.davidboles.lib.program.Logger;
import space.davidboles.lib.program.ProgramFs;

public class Start {

	public static HTTPServerSimpleManager server;
	public static Logger sLogger = new Logger();
	public static PhotoManager pMan;
	
	public static void main(String[] args) {
		sLogger.log("STARTING");
		
		sLogger.log("DOWNLOADING NEW...");
		//TODO Download new images
		
		sLogger.log("LOADING AND CATALOGING NEW...");
		pMan = new PhotoManager(ProgramFs.getProgramFile("web/photos"));
		
		sLogger.log("STARTING SERVER...");
		try {
			server = new HTTPServerSimpleManager(712);
			server.addHandler(new FolderHttpHandler("/", ProgramFs.getProgramFile("web")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sLogger.log("STARTED");
	}

}

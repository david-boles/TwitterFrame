package space.davidboles.twitterframe.server;

import java.io.Serializable;

public class Photo implements Serializable {
	
	private static final long serialVersionUID = 7526472295622776147L;
	
	String id;
	int posVote;
	int negVote;
	
	public Photo(String id) {
		this.id = id;
		this.posVote = 1;
		this.negVote = 1;
	}
	
	public Photo(String id, int posVote, int negVote) {
		this(id);
		this.posVote = posVote;
		this.negVote = negVote;
	}
}

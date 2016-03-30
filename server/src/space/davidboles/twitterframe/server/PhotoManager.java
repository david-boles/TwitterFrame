package space.davidboles.twitterframe.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import space.davidboles.lib.program.ProgramFs;

public class PhotoManager {
	File photoDir;
	File photoList;
	ArrayList<Photo> photos = new ArrayList<>();
	Random random = new Random();
	
	public PhotoManager(File photoDir) {
		this.photoDir = photoDir;
		this.photoList = ProgramFs.getProgramFile("photo_list.ser");
		initialize();
	}
	
	@SuppressWarnings("unchecked")
	void initialize() {
		if(!this.photoDir.isDirectory()) photoDir.mkdirs();
		//TODO download new pics
		if(!this.photoList.isFile()) ProgramFs.saveObject(this.photoList, this.photos);
		photos = (ArrayList<Photo>) ProgramFs.loadObject(photoList);
		
		File[] allPhotos = this.photoDir.listFiles();
		for(int i = 0; i < allPhotos.length; i++) {
			String id = "photos/"+allPhotos[i].getName();
			if(getPhoto(id) == null) photos.add(new Photo(id));
		}
		
		ProgramFs.saveObject(this.photoList, this.photos);
		
		
	}
	
	Photo getPhoto(String id) {
		for(int i = 0; i < photos.size(); i++) {
			if(photos.get(i).id.equals(id)) return photos.get(i);
		}
		return null;
	}
	
	String getPhotoString() {
		ArrayList<Photo> output = getOutputPictures();
		String photos = "";
		photos += "\"" + output.get(0).id + "\"";
		for(int i = 1; i < output.size(); i++) {
			photos += ", \"" + output.get(i).id + "\"";
		}
		return photos;
	}
	
	public void listAll() {
		for(int i = 0; i < this.photos.size(); i++) {
			Photo p = this.photos.get(i);
			Start.sLogger.logMore("Pos V, Neg V of " + p.id, new Object[]{p.posVote, p.negVote});
		}
	}
	
	ArrayList<Photo> getOutputPictures() {
		ArrayList<Photo> output = new ArrayList<>();
		
		for(int i = 0; i < photos.size(); i++) {
			Photo current = photos.get(i);
			
			float rating = 0;
			rating = ((float)current.posVote)/((float)current.negVote);
			
			if(rating >=1) {
				for(int added = 0; added < rating; added++) {
					output.add(current);
				}
			}else {
				float rand = this.random.nextFloat();
				if(rand < rating) output.add(current);
			}
		}
		
		ArrayList<Photo> randomized = new ArrayList<>();
		while(output.size() != 0) {
			int get = this.random.nextInt(output.size());
			randomized.add(output.get(get));
			output.remove(get);
		}
		
		return randomized;
	}
}

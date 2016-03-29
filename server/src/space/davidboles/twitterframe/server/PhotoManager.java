package space.davidboles.twitterframe.server;

import java.io.File;
import java.util.ArrayList;

import space.davidboles.lib.program.ProgramFs;

public class PhotoManager {
	File photoDir;
	File photoList;
	ArrayList<Photo> photos = new ArrayList<>();
	
	public PhotoManager(File photoDir) {
		this.photoDir = photoDir;
		this.photoList = ProgramFs.getProgramFile("photo_list.ser");
		initialize();
	}
	
	@SuppressWarnings("unchecked")
	void initialize() {
		if(!this.photoDir.isDirectory()) photoDir.mkdirs();
		if(!this.photoList.isFile()) ProgramFs.saveObject(this.photoList, this.photos);
		photos = (ArrayList<Photo>) ProgramFs.loadObject(photoList);
		
		File[] allPhotos = this.photoDir.listFiles();
		for(int i = 0; i < allPhotos.length; i++) {
			String id = allPhotos[i].getName();
			if(getPhoto(id) == null) photos.add(new Photo(id));
		}
	}
	
	Photo getPhoto(String id) {
		for(int i = 0; i < photos.size(); i++) {
			if(photos.get(i).id.equals(id)) return photos.get(i);
		}
		return null;
	}
}

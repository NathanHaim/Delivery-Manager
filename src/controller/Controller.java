package controller;

import java.io.File;

import model.Model;
import view.View;

public class Controller implements IController{

	private Model model;
	private View view;
	
	/**
	 * Default constructor of the controller. It instanciate model and view, and set up observer/observable pattern
	 */
	public Controller()
	{
		model = new Model(this);
		view  = new View(this);
		model.addObserver(view);
	}

	/**
	 * This method just call the parseMapFile method of the model. It called by the view when a click on 
	 * Validate(SettingFrame) is caught.
	 * @param currentFile
	 */
	public void parseMapFile(File currentFile) {
		model.parseMapFile(currentFile);		
	}
}

package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
<<<<<<< HEAD
=======

import controller.commands.CommandContext;
import controller.commands.Commander;
import controller.commands.ICommander;
import controller.commands.undocommands.ResetDeliveriesCommand;
>>>>>>> branch 'master' of https://github.com/FrancoisChastel/Delivery-Manager.git
import model.IModel;
import model.Model;
import view.View;
import view.View.Page;

public class Controller implements IController{

	private Model model;
	private View view;
	private Logger logger;
	private Commander commander;
	
	/**
	 * Default constructor of the controller. It instanciate model and view, and set up observer/observable pattern
	 */
	public Controller()
	{
		model = new Model(this);
		view  = new View(this);
		this.commander = Commander.getInstance();
		try {
			logger = new Logger();
		} catch (IOException e) {
			//TODO properly handle the exception !
		}
		model.addObserver(view);		
		view.displayFrame(Page.Setting, false);
	}
	
	// Callbacks ------------------------------

	/**
	 * This method just call the parseMapFile method of the model. It called by the view when a click on 
	 * Validate(SettingFrame) is caught.
	 * @param currentFile
	 */
	public void parseMapFile(File currentFile) {
		model.loadMapFile(currentFile);
	}
	
	/**
	 * This method just call the parseDeliveriesFile method of the model. It called by the view when a click on 
	 * Validate(SettingFrame) is caught.
	 * @param currentFile
	 */
	public void parseDeliveriesFile(File currentFile)
	{
		model.loadDeliveriesFile(currentFile);
	}	

	/**
	 * Loads a delivery file XML. It can be called by the View.Mainframe
	 * @param currentFile
	 */
	public void loadDeliveryFile(File currentFile) {
		
		model.loadDeliveriesFile(currentFile);
	}
	
	/**
	 * Generates a trace route. It called by the view on rightclick>Generate trace route in the JTree
	 * @param tourid
	 */
	public void generateTraceRoute(int tourid)
	{
		String message ="Route for tour #"+tourid+" generated !";
		try {
			model.generateTraceRoute(tourid);
		}
		catch(Exception e)
		{
			message = "Exception while generating the Route for tour "+tourid;
		}
		view.displayMessage(message, "TraceRoute", view.getPage(Page.Main));
	}
	/**
	 * Reset the entire model with new managers
	 */
	public void reset()
	{
		model.resetModel();
	}
	
	/**
	 *  Reset all the model excepted the map associated to the graph
	 * @throws Throwable 
	 */
	public void resetDeliveries() throws Throwable
	{
		// For example : 
		ResetDeliveriesCommand reset = new ResetDeliveriesCommand(model);
		reset.execute();
		this.commander.execute(CommandContext.MAIN, reset);
	}
	
	/**
	 * Undo the last command executed by the user
	 */
	public void undoCommand(CommandContext context, int numberOfRedo)
	{
		try {
			this.commander.undo(context);
		} catch (Throwable e) {
			this.logger.write(e.getMessage());
		}
	}
	
	/**
	 * Redo the last command cancelled by an Undo
	 */
	public void redoCommand(CommandContext context, int numberOfRedo)
	{
		try {
			this.commander.redo(context, numberOfRedo);
		} catch (Throwable e) {
			this.logger.write(e.getMessage());
		}
	}
	
	
	// End callbacks ------------------------------
	
	public IModel getModel() { return model; }

	/**
	 * Display error in a popup frame and write it in the log file
	 * @param message
	 */
	public void error(String message)
	{
		View.displayMessage(message, "Error", JOptionPane.ERROR_MESSAGE,null);
		logger.write("Error : "+ message);
	}

	public Logger getLogger() {
		return logger;
	}
	

	
}

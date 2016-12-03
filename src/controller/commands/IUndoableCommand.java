/*************************************************************************
                        IUndoableCommand
 -------------------
 Begin                : 03/12/2016
 Copyright            : (C) 2016 by FACANT
 *************************************************************************/
package controller.commands;

/**
 * 
 * @author FACANT
 *
 */
public class IUndoableCommand extend ICommand {
	/**
	 * Undo the command
	 * @throws Throwable
	 */
	public void undo() throws Throwable;
	
	/**
	 * Redo the command
	 * @throws Throwable
	 */
	public void redo() throws Throwable;
}
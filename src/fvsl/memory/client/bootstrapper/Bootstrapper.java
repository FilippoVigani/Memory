package fvsl.memory.client.bootstrapper;
import java.sql.Connection;
import java.sql.SQLException;

import fvsl.memory.client.ui.WindowShell;
import fvsl.memory.server.db.DBHelper;

/**
 * @author Filippo Vigani
 *
 */
public class Bootstrapper {
	public static void main(String[] args){
		System.out.println("Hello world!");
		WindowShell mainWindow = new WindowShell();
		mainWindow.setVisible(true);
	}
}

package others;

import java.io.File;
import java.io.FileInputStream;

import Header.DataHeader;
import Header.NodeHeaderAdd;
import Header.RequestHeader;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.myRun(1030);
	}
}

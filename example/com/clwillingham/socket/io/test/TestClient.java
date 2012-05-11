/**
 * 
 */
package com.clwillingham.socket.io.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.clwillingham.socket.io.IOSocket;
import com.clwillingham.socket.io.MessageCallback;
import org.json.JSONObject;

/**
 * From the README...
 * 
 * @author cmg
 *
 */
public class TestClient {
	static Logger logger = Logger.getLogger(TestClient.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length!=1) {
			logger.severe("Usage: <iosocket-url>");
			System.exit(-1);
		}
		String url = args[0];
		new TestClient().test(url);
	}
	
	private IOSocket socket;
	
	void test(String url) {
		try {
			logger.info("Connect to "+url);
			// TODO Auto-generated method stub
			socket = new IOSocket(url, new MessageCallback() {
				@Override
				public void on(String event, JSONObject... data) {
					// Handle events
					logger.info("on("+event+"...)");
				}

				@Override
				public void onMessage(String message) {
					// Handle simple messages
					logger.info("onMessage("+message+")");
				}

				@Override
				public void onMessage(JSONObject message) {
					// Handle JSON messages
					logger.info("onMessage("+message+" [JSON])");
				}

				@Override
				public void onConnect() {
					// Socket connection opened
					logger.info("onConnect()");

					logger.info("Send Hello world...");
					// simple message
					try {
						socket.send("Hello world");

						logger.info("Send see...");
						// event with a json message
						socket.emit("see", new JSONObject().put("name", "Spot").put("action", "run"));
					} catch (Exception e) {
						logger.log(Level.SEVERE, "Error: "+e, e);
					}	

				}

				@Override
				public void onDisconnect() {
					// Socket connection closed
					logger.info("onDisconnect()");
				}
			});

			logger.info("Connect...");
			socket.connect();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error: "+e, e);
		}	
	}

}

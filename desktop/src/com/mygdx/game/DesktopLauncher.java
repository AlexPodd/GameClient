package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("HeroAdventure");
		config.setWindowedMode(1280,720);
		config.setForegroundFPS(60);
		config.setResizable(false);

		String IP = "localhost";
		int TCPPort = 8081;
		int UDPPort = 15914;
		MyGame app = new MyGame(IP, TCPPort, UDPPort);
		new Lwjgl3Application(app, config);
	}
}

package me.KevinW1998.Up2DateUpdater.err;

import java.io.IOException;
import java.net.MalformedURLException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ErrorReporter {
	public static void ReportError(Exception e, CommandSender cs){
		boolean imp = false;
		if(e instanceof MalformedURLException){
			cs.sendMessage(ChatColor.RED + "A MalformedURLException has been thrown!");
			cs.sendMessage(ChatColor.RED + "The URL of the plugin isn't set or wrong formatted!");
		}else if(e instanceof IOException){
			cs.sendMessage(ChatColor.RED + "A IOException has been thrown!");
			cs.sendMessage(ChatColor.RED + "Couldn't read the full website or download it correctly!");
			imp = true;
		}
		if(imp){
			cs.sendMessage(ChatColor.RED + "You may report this error to the admin of the server");
			cs.sendMessage(ChatColor.RED + "or to the developer of this plugin!");
			e.printStackTrace();
		}
	}
}

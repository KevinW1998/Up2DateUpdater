package me.KevinW1998.Up2DateUpdater.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandManager {

	public CommandManager() {
	}

	private String helpheader(String h) {
		return "----" + ChatColor.GREEN + h + ChatColor.WHITE + "----";
	}

	private String genHelpStr(String cmd, String h) {
		return ChatColor.AQUA + cmd + ChatColor.WHITE + " | " + ChatColor.GREEN + h;
	}

	public void help(CommandSender c) {
		c.sendMessage(helpheader("Up2Date Updater Help"));
		c.sendMessage(genHelpStr("/up2date", "Shows This Screen"));
		c.sendMessage(genHelpStr("/up2date update <plugin>", "Update <plugin>"));
		c.sendMessage(genHelpStr("/up2date updateall", "Update all plugins"));
		c.sendMessage(genHelpStr("/up2date updatemode <updatemode> <plugin>", "Use <updatemode> for <plugin>"));
		c.sendMessage(genHelpStr("/up2date check <plugin>", "Check for update for <plugin>"));
	}

}

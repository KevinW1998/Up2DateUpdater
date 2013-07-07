package me.KevinW1998.Up2DateUpdater.Utils;

import java.io.IOException;
import java.net.MalformedURLException;

import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.DevBukkitSite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandManager {

	public CommandManager() {
	}

	private String helpheader(String h) {
		return "----" + ChatColor.GREEN + h + ChatColor.WHITE + "----";
	}

	private String genHelpStr(String cmd, String h) {
		return ChatColor.AQUA + cmd + ChatColor.WHITE + " | " + ChatColor.GREEN + h;
	}
	
	private String genListPluginStr(Plugin p){
		return ChatColor.AQUA + p.getName() + "  " + ChatColor.GREEN + p.getDescription().getVersion();
	}
	
	private PluginUpdateInfo findPlugin(String search){
		for(PluginUpdateInfo pui : PluginUpdateInfo.plugins){
			if(pui.p.getName().equalsIgnoreCase(search)){
				return pui;
			}
		}
		return null;
	}

	public void help(CommandSender c) {
		c.sendMessage(helpheader("Up2Date Updater Help"));
		c.sendMessage(genHelpStr("/up2date", "Shows This Screen"));
		c.sendMessage(genHelpStr("/up2date update <plugin>", "Update <plugin>"));
		c.sendMessage(genHelpStr("/up2date updateall", "Update all plugins"));
		c.sendMessage(genHelpStr("/up2date updatemode <updatemode> <plugin>", "Use <updatemode> for <plugin>"));
		c.sendMessage(genHelpStr("/up2date check <plugin>", "Check for update for <plugin>"));
		c.sendMessage(genHelpStr("/up2date list", "Lists all your plugins"));
	}
	
	public void check(CommandSender c, String p){
		PluginUpdateInfo fP = findPlugin(p);
		if(fP == null){
			c.sendMessage(ChatColor.RED + "Didn't found plugin with name: \"" + ChatColor.GREEN + p + ChatColor.RED + "\"");
			return;
		}
		DevBukkitSite dbs = null;
		try {
			dbs = new DevBukkitSite(fP, fP.URL);
		} catch (MalformedURLException e) {
			c.sendMessage(ChatColor.RED + "Plugin-URL wrong, wrong formatted or isn't set yet!");
			return;
		}
		try {
			dbs.download();
		} catch (IOException e) {
			c.sendMessage(ChatColor.RED + "Couldn't download URL-file for version compare!");
			return;
		}
		if(dbs.hasUpdate()){
			c.sendMessage(ChatColor.GREEN + "New update found!");
		}else{
			c.sendMessage(ChatColor.RED + "No new updates!");
		}
	}
	
	public void list(CommandSender c){
		for(Plugin lP : Bukkit.getPluginManager().getPlugins()){
			c.sendMessage(genListPluginStr(lP));
		}
	}

}

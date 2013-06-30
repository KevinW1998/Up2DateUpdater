package me.KevinW1998.Up2DateUpdater;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import me.KevinW1998.Up2DateUpdater.Utils.*;
import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.DevBukkitSite;

public class MainUpdater extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static MainUpdater plugin;
	public ymlLoader pfu;

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
		enableMain();
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}

	public boolean checkPerms(CommandSender sender, String xPerms) {
		return (!(sender instanceof Player)) || sender.isOp() || sender.hasPermission(xPerms);
	}

	public String nP = ChatColor.RED + "YOU DO NOT HAVE THE PERMISSIONS TO USE THIS COMMAND!";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandManager m = new CommandManager();
		if (label.equalsIgnoreCase("up2date")) {
			if (args.length <= 1) {

			} else {
				if (checkPerms(sender, "up2date.help")) {
					m.help(sender);
				} else {
					sender.sendMessage(nP);
				}
			}
		}
		return false;
	}

	// private routines
	private void addPlugin(ConfigurationSection cs, Plugin p) {
		ConfigurationSection m = cs.getConfigurationSection(p.getDescription().getName());
		if (m == null) {
			m = pfu.yml.createSection(p.getDescription().getName());
		}
		if (m.getString("URL") == null || m.getString("URL").equals("unknown")) {
			if (p.getDescription().getWebsite() != null && DevBukkitSite.isBukkitSite(p.getDescription().getWebsite())) {
				m.set("URL", p.getDescription().getWebsite());
			} else {
				m.set("URL", "unknown");
			}
		}
		if (m.getString("Mode") == null) {
			m.set("Mode", Comparer.VERSION_WEBSITE_COMPARE.toString());
		}
	}

	private void addAllPlugins(ConfigurationSection sc) {
		for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			addPlugin(sc, pl);
		}
	}

	// Enable routines
	public void enableMain() {
		pfu = new ymlLoader(this, "pluginsforupdate.yml");
		addAllPlugins(pfu.yml);
		pfu.save();
		loadPluginInfo();
		test();
	}

	public void loadPluginInfo() {
		for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
			ConfigurationSection cs = pfu.yml.getConfigurationSection(p.getDescription().getName());
			PluginUpdateInfo pui = new PluginUpdateInfo(new VersionsComparer(Comparer.valueOf(cs.getString("Mode"))), p);
			pui.URL = getURL(p, cs);
			PluginUpdateInfo.plugins.add(pui);
		}
	}

	public String getURL(Plugin pFinder, ConfigurationSection sc) {
		return sc.getString("URL").equals("unknown") ? null : sc.getString("URL");
	}

	// XXX DEBUG
	public void test() {
		String f = "SafeCommandBlock";
		PluginUpdateInfo tar = null;
		for (PluginUpdateInfo pui : PluginUpdateInfo.plugins) {
			if (pui.p.getDescription().getName().equals(f)) {
				tar = pui;
			}
		}
		DevBukkitSite dbs = null;
		try {
			dbs = new DevBukkitSite(tar, tar.URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			dbs.download();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbs.hasUpdate();
	}
}

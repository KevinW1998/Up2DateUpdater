package me.KevinW1998.Up2DateUpdater.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

public class PluginUpdateInfo {
	
	public static List<PluginUpdateInfo> plugins = new ArrayList<PluginUpdateInfo>();
	
	public VersionsComparer v;
	public Plugin p;
	public String URL;
	
	public PluginUpdateInfo(VersionsComparer v, Plugin p) {
		this.v = v;
		this.p = p;
	}
	
}

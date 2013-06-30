package me.KevinW1998.Up2DateUpdater.Utils.DevBukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import me.KevinW1998.Up2DateUpdater.Utils.PluginUpdateInfo;

public class DevBukkitSite {
	public static boolean isBukkitSite(String url) {
		return (url.startsWith("dev.bukkit.org/bukkit-mods") || url.startsWith("http://www.dev.bukkit.org/bukkit-mods") || url.startsWith("www.dev.bukkit.org/bukkit-mods"))
				|| url.startsWith("http://dev.bukkit.org/bukkit-mods");
	}

	private PluginUpdateInfo targetPlugin;
	private URL bukkitUrl;
	private String Content;

	public DevBukkitSite(PluginUpdateInfo pui, String url) throws MalformedURLException {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		if (!url.endsWith("files") || !url.endsWith("files/")) {
			if (!url.endsWith("/")) {
				url += "/files/";
			}else{
				url += "files/";
			}
		}
		targetPlugin = pui;
		bukkitUrl = new URL(url);
	}

	public boolean isDownloaded() {
		return Content != null;
	}

	public void download() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(bukkitUrl.openStream()));
		String line;
		while ((line = br.readLine()) != null) {
			Content += line;
		}
	}
	
	public boolean hasUpdate(){
		return targetPlugin.v.hasUpdate(Content, targetPlugin);
	}

}

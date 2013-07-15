package me.KevinW1998.Up2DateUpdater.Utils.DevBukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.KevinW1998.Up2DateUpdater.Utils.PluginUpdateInfo;

public class DevBukkitSite {
	
	private String[] getGameVersions(String con) {
		int index = 0;
		List<String> retTable = new ArrayList<String>();
		while ((index = (con.indexOf("<li>", index) + 3)) != 2) {
			int lindex = con.indexOf("</li>", index);
			retTable.add(con.substring(index, lindex));
			index = lindex;
		}
		return retTable.toArray(new String[retTable.size()]);
	}

	private String getDate(String con) {
		int index = con.indexOf("class=\"standard-date") + 29;
		int lindex = con.indexOf("\" data", index);
		return con.substring(index, lindex);
	}

	private String getStatus(String con) {
		int index = con.indexOf("class=\"file-status") + 34;
		int lindex = con.indexOf("</span>", index);
		return con.substring(index, lindex);
	}

	private FileType getType(String con) {
		int index = con.indexOf("class=\"file-type") + 30;
		int lindex = con.indexOf("</span>", index);
		return FileType.valueOf(con.substring(index, lindex).toUpperCase());
	}

	private String getName(String con) {
		int index = con.indexOf("\">", con.indexOf("href")) + 2;
		int lindex = con.indexOf("</a>", index);
		return con.substring(index, lindex);
	}

	private String getDownloadPageURL(String con) {
		int index = con.indexOf("href") + 6;
		int lindex = con.indexOf("\">", index);
		return "http://dev.bukkit.org" + con.substring(index, lindex);
	}

	public DevBukkitFile[] getFileTargets() {
		String con = Content;
		String table = con.substring(con.indexOf("class=\"listing\"") - 7, con.indexOf("</table>") + 8);
		int i = 1880;
		int sInt = 0;
		List<DevBukkitFile> lF = new ArrayList<DevBukkitFile>();
		while ((sInt = table.indexOf("<tr class=", i)) != -1) {
			int eInt = table.indexOf("</tr>", sInt) - 4;
			String fDataHTML = table.substring(sInt, eInt);
			DevBukkitFile f = new DevBukkitFile(getDownloadPageURL(fDataHTML), getName(fDataHTML), getType(fDataHTML), getStatus(fDataHTML), getDate(fDataHTML),
					getGameVersions(fDataHTML));
			lF.add(f);
			i = eInt;
		}
		return lF.toArray(new DevBukkitFile[lF.size()]);
	}
	
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

}

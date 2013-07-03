package me.KevinW1998.Up2DateUpdater.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.DevBukkitFile;
import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.FileType;

public class VersionsComparer {

	/*
	 * def:
	 * 
	 * VERSION_WEBSITE_COMPARE
	 */
	public Comparer pattenComparerSystem;

	public VersionsComparer(Comparer c) {
		pattenComparerSystem = c;
	}

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

	private DevBukkitFile[] getFileTargets(String con) {
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

	public boolean hasUpdate(String content, PluginUpdateInfo pui) {
		DevBukkitFile[] files = getFileTargets(content);
		if (pattenComparerSystem == Comparer.VERSION_WEBSITE_COMPARE) {
			Pattern verParser = Pattern.compile("\\d+(?:\\.\\d+)+");
			Matcher nVerMatcher = verParser.matcher(files[0].Name);
			Matcher oVerMatcher = verParser.matcher(pui.p.getDescription().getVersion());
			if(!nVerMatcher.find() || !oVerMatcher.find()){
				return false; //TODO Better Reason (e.g. Reason Obj)
			}
			String nVer = nVerMatcher.group();
			String oVer = oVerMatcher.group();
			VersionWebsiteCompare vwc = new VersionWebsiteCompare(oVer);
			int res = vwc.compareTo(new VersionWebsiteCompare(nVer));
			if(res == -1){
				return true;
			}else if(res == 0 || res == 1){
				return false;
			}
		}
		return false;
	}
}

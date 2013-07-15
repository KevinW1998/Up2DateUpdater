package me.KevinW1998.Up2DateUpdater.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.DevBukkitFile;
import me.KevinW1998.Up2DateUpdater.Utils.DevBukkit.DevBukkitSite;
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

	public boolean hasUpdate(PluginUpdateInfo pui) throws IOException, MalformedURLException {
		if (pattenComparerSystem == Comparer.VERSION_WEBSITE_COMPARE) {
			DevBukkitSite dbs = new DevBukkitSite(pui, pui.URL);
			dbs.download();
			DevBukkitFile[] files = dbs.getFileTargets();
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

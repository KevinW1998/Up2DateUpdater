package me.KevinW1998.Up2DateUpdater.Utils.DevBukkit;

public class DevBukkitFile {
	
	public String Url;
	public String Name;
	public FileType Type;
	public String Status;
	public String Date;
	public String[] GameVersions;
	
	public DevBukkitFile(String fUrl, String fName, FileType ftype, String fstatus, String fdate, String[] fgameversion){
		Url = fUrl;
		Name = fName;
		Type = ftype;
		Status = fstatus;
		Date = fdate;
		GameVersions = fgameversion; 
	}
}

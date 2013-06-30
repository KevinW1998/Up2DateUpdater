package me.KevinW1998.Up2DateUpdater.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ymlLoader {

	public YamlConfiguration yml;
	public File ymlFile;

	public ymlLoader(Plugin p, String f) {
		ymlFile = new File(p.getDataFolder(), f);
		if (!ymlFile.exists()) {
			ymlFile.getParentFile().mkdirs();
			copy(p.getResource(f), ymlFile);
		}
		yml = new YamlConfiguration();
		try {
			yml.load(ymlFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			yml.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			yml.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

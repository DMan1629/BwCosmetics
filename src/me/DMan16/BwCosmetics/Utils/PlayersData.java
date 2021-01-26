package me.DMan16.BwCosmetics.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Particles.Effects;

public class PlayersData {
	private HashMap<String,String> players;
	private String dir;
	
	public PlayersData() throws IOException {
		players = new HashMap<String,String>();
		dir = "plugins/" + Main.pluginName + "/players";
		Files.createDirectories(Paths.get(dir), new FileAttribute[0]);
		for (File file : new File(dir).listFiles()) {
			try (InputStreamReader stream = new InputStreamReader(new FileInputStream(dir + "/" + file.getName()),"UTF-8")) {
				BufferedReader reader = new BufferedReader(stream);
				List<String> list = new ArrayList<String>();
				String line;
				while ((line = reader.readLine()) != null) if (!(line = line.trim()).isEmpty()) list.add(line.trim());
				String str = String.join("\n",list);
				if (Effects.getEffect(str) == null) file.delete();
				else {
					String ID = file.getName();
					players.put(ID,str);
					write(ID);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void write(String ID) {
		if (!players.containsKey(ID)) new File(dir,ID).delete();
		else {
			Path path = Paths.get(dir).resolve(ID);
			try {
				if (!Files.exists(path, new LinkOption[0])) {
					Files.createDirectories(path.getParent(), new FileAttribute[0]);
					Files.createFile(path, new FileAttribute[0]);
				}
				OutputStream fw = new FileOutputStream(dir + "/" + ID);
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(fw,"UTF-8"));
				pw.write(players.get(ID)); 
				pw.flush(); 
				pw.close(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String get(Player player) {
		if (players.containsKey(player.getUniqueId().toString())) return players.get(player.getUniqueId().toString());
		return null;
	}
	
	public boolean update(Player player, String str) {
		str = Effects.getEffectName(str);
		if (str != null && Effects.getEffect(str) == null) return false;
		if (str != null && !Main.Config.getEffectConfig(str).canApply(player)) return false;
		String ID = player.getUniqueId().toString();
		if (str == null) players.remove(ID);
		else if (!players.containsKey(ID)) players.put(ID,str);
		else players.replace(ID,str);
		write(ID);
		return true;
	}
	
	public boolean isEmpty() {
		return players == null || players.isEmpty();
	}
}
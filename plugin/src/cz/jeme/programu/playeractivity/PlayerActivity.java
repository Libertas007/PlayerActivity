package cz.jeme.programu.playeractivity;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerActivity extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, this);
		File dataDir = getDataFolder();

		if (!dataDir.exists()) {
			dataDir.mkdir();
		}
	}

	@Override
	public void onDisable() {
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		
	}
}
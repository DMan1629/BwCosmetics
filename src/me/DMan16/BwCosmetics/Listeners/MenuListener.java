package me.DMan16.BwCosmetics.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Config.EffectConfig;
import me.DMan16.BwCosmetics.Particles.Effects;
import me.DMan16.BwCosmetics.Utils.Utils;

public class MenuListener implements Listener {
	static String menuName = "&6[" + Main.pluginNameColors + "&6] &fKill effect menu";
	static NamespacedKey key = Utils.namespacedKey("effect");
	private static int lines = 4;
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onMenuClick(InventoryClickEvent event) {
		int slot = event.getRawSlot();
		if (!menuName.equals(Utils.chatColorsToString(event.getView().getTitle(),"&"))) return;
		event.setCancelled(true);
		if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) return;
		if (slot >= lines * 9) return;
		Inventory inv = event.getInventory();
		ItemStack item = inv.getItem(slot);
		Player player = (Player) event.getWhoClicked();
		if (slot >= (lines * 9) - 9) {
			if (slot == (lines * 9) - 5) player.closeInventory();
			else if (slot == (lines * 9) - 1) {
				Main.PlayersDataManager.update(player,null);
				updateInventory(inv,player);
			} else if (slot == (lines * 9) - 9) Bukkit.dispatchCommand(player,"gui open bedwars");
		} else try {
			if (Main.PlayersDataManager.update(player,item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING))) updateInventory(inv,player);
		} catch (Exception e) {}
	}
	
	private static void updateInventory(Inventory inv, Player player) {
		inv.clear();
		List<ItemStack> blocked = new ArrayList<ItemStack>();
		for (String name : Effects.names()) {
			if (!Utils.isNull(inv.getItem((lines * 9) - 10))) break;	//Full page break;
			EffectConfig effectConfig = Main.Config.getEffectConfig(name);
			if (effectConfig == null) continue;
			ItemStack item = getItemEffectConfig(effectConfig,player);
			if (effectConfig.canApply(player)) inv.addItem(item);
			else blocked.add(item);
		}
		if (Main.Config.effectsShowBlocked()) for (ItemStack item : blocked) inv.addItem(item);
		inv.setItem((lines * 9) - 9,getItem(Material.ARROW,"&aBack"));
		inv.setItem((lines * 9) - 5,getItem(Material.BARRIER,"&cClose"));
		inv.setItem((lines * 9) - 1,getItem(Material.PAPER,"&fReset"));
	}
	
	private static ItemStack getItemEffectConfig(EffectConfig effectConfig, Player player) {
		boolean active = Main.PlayersDataManager.get(player) != null && Main.PlayersDataManager.get(player).equals(effectConfig.effect);
		Material material = effectConfig.getEffect().materialGUI;
		ItemStack item = getItem(material,effectConfig.getEffect().nameColors,!effectConfig.canApply(player) ? Arrays.asList("&cUnavailable") : null);
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key,PersistentDataType.STRING,effectConfig.effect);
		item.setItemMeta(meta);
		if (active) item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
		return item;
	}
	
	private static <T,Z> ItemStack getItem(Material material, String name) {
		return getItem(material,name,null);
	}
	
	private static <T,Z> ItemStack getItem(Material material, String name, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chatColors(name));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (lore != null) meta.setLore(Utils.chatColors(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static void openMenu(Player player) {
		Inventory inv = Bukkit.createInventory(player,lines * 9,Utils.chatColors(menuName));
		player.closeInventory();
		updateInventory(inv,player);
		player.openInventory(inv);
	}
}
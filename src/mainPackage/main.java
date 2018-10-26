package mainPackage;

import java.awt.Event;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Vine;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import mainPackage.CaveGenerator;
import mainPackage.DressCursed;

public class main extends JavaPlugin implements Listener, CommandExecutor{

	BukkitScheduler scheduler = null;
	FileConfiguration config = getConfig();
	public ConsoleCommandSender console = getServer().getConsoleSender();
	Random randor = new Random();
	//public World wor = null;
	boolean hasWorlds = false;
	public static List<String> worlds = new ArrayList<String>();
	public boolean canSave = false;
	public List<Entity> effectEnts = new ArrayList<Entity>();
	boolean caveins = false;
	boolean hungerdark = false;
	boolean ambients = false;
	boolean cavetemp = false;
	boolean caveage = false;
	boolean caveents = false;
	static boolean cavestruct = false;
	static int cavechance = 3;
	public static List<String> mobNames = new ArrayList<String>();
	public static List<String> itemcustom = new ArrayList<String>();
	public static List<String> hotmessage = new ArrayList<String>();
	int damage = 0;
	public static int trrate = 0;
	public static int strate = 0;
	public static int blrate = 0;
	public static int plrate = 0;
	
	@Override
	public void onEnable() {
		createConfigFol();
		this.getServer().getPluginManager().registerEvents(this, this);
		caveins = config.getBoolean("Enable Cave-Ins ");
		hungerdark = config.getBoolean("Enable Hungering Darkness ");
		ambients = config.getBoolean("Enable Ambient Sounds ");
		cavetemp = config.getBoolean("Enable Cave Temperature ");
		caveage = config.getBoolean("Enable Cave Aging ");
		caveents = config.getBoolean("Enable Cave Monsters ");
		cavechance = config.getInt("Cave Structure Chance ");
		cavestruct = config.getBoolean("Enable Cave Structures ");
		blrate = config.getInt("Boulder Rate ");
		trrate = config.getInt("Traps Rate ");
		strate = config.getInt("Buildings Rate ");
		plrate = config.getInt("Pillar Rate ");
		mobNames.add("The Darkness");
		mobNames.add(config.getString("Magma Monster = "));
		mobNames.add(config.getString("Crying Bat = "));
		mobNames.add(config.getString("Lava Creeper = "));
		mobNames.add(config.getString("TnT Creeper = "));
		mobNames.add(config.getString("Watcher = "));
		mobNames.add(config.getString("Smoke Demon = "));
		mobNames.add(config.getString("Alpha Spider = "));
		mobNames.add(config.getString("Dead Miner = "));
		mobNames.add(config.getString("Hexed Armor = "));
		itemcustom = (List<String>) config.getList("Items that can spawn in chests ");
		hotmessage = (List<String>) config.getList("Temperature Messages ");
		worlds = (List<String>) config.getList("Enabled Worlds - If Left Blank Will Just Use default world ");
		int d = config.getInt("Hungering Darkness Damage ");
		if(d > 200) {
			damage = 200;
		}
		else if(d < 0) {
			damage = 0;
		}
		else {
			damage = d;
		}
		if(worlds.size()==0) {
			boolean hasWorldR = false;
			for(World w : Bukkit.getWorlds()) {
				if(w.getName().equals("world")) {
					hasWorldR = true;
				}
			}
			if(hasWorldR == true) {
				worlds.add("world");
			}
			else {
			worlds.add(Bukkit.getWorlds().get(0).getName());
			}
		}
		for(String namew : worlds) {
			World wor = Bukkit.getWorld(namew);
			if(wor!=null) {
			List<BlockPopulator> bp = new ArrayList<BlockPopulator>(wor.getPopulators());
			for(BlockPopulator bps : bp) {
				if(bps.toString().length()>=25) {
					if(bps.toString().substring(0, 25).equals("mainPackage.CaveGenerator")) {
						wor.getPopulators().remove(bps);
					}
				}
			}
			wor.getPopulators().add(new CaveGenerator());
			}
		}
		scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if(hungerdark==true||caveents==true) {
				betterEffectLooper();
				}
			}
		}, 0L, /* 600 */((long) 3));
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
					if (caveage == true) {
					for (Player p : wor.getPlayers()) {
						doCaveStuff(p);
					}
					}
					if(ambients == true) {
						doCaveSounds();
					}
				}
				}
				}
				catch(Exception e) {
					console.sendMessage(ChatColor.DARK_RED + "Our worst fears were real.");
				}
			}
		}, 0L, /* 600 */7000L);

	}
	
	private static boolean getLookingAt2(LivingEntity player, LivingEntity player1) {
		Location eye = player.getEyeLocation();
		Vector toEntity = ((LivingEntity) player1).getEyeLocation().toVector().subtract(eye.toVector());
		double dot = toEntity.normalize().dot(eye.getDirection());

		return dot > 0.70D;
	}
	
	@Override
	public void onDisable() {
		worlds.clear();
	}
	
	public void createConfigFol() {
		config.addDefault("Enable Cave-Ins ", true);
		config.addDefault("Enable Hungering Darkness ", true);
		config.addDefault("Enable Ambient Sounds ", true);
		config.addDefault("Enable Cave Temperature ", true);
		config.addDefault("Enable Cave Aging ", true);
		config.addDefault("Enable Cave Monsters ", true);
		config.addDefault("Enable Cave Structures ", true);
		config.addDefault("Enable Monster Fixing ", false);
		config.addDefault("Enable Broken Monster Deletion ", false);
		config.addDefault("::::Higher equals lower chance!::::", "");
		config.addDefault("Cave Aging Chance ", 2);
		config.addDefault("Cave Aging Change Chance ", 39);
		config.addDefault("Cave Ambience Chance ", 4);
		config.addDefault("Cave Walk Temp Chance ", 1449);
		config.addDefault("Cave Break Block Temp Chance ", 1450);
		config.addDefault("Cave-In Chance ", 399);
		config.addDefault("Darkness Spawn Chance ", 1);
		config.addDefault("Cave Structure Chance ", 1);
		config.addDefault("Hungering Darkness Damage ", 200);
		config.addDefault("Monster Spawning Highest Y ", 50);
		config.addDefault("Monster Spawning Lowest Y ", 0);
		config.addDefault(":::::::::::::::::::::::::::::::::::", "");
		config.addDefault("Traps Rate ", 0);
		config.addDefault("Buildings Rate ", 0);
		config.addDefault("Boulder Rate ", 0);
		config.addDefault("Pillar Rate ", 0);
		config.addDefault(":::::::::::::::::::::::::::::::::::", "");
		config.addDefault("Spawn Crying Bat ", true);
		config.addDefault("Spawn Magma Monster ", true);
		config.addDefault("Spawn Lava Creeper ", true);
		config.addDefault("Spawn TnT Creeper ", true);
		config.addDefault("Spawn Watcher ", true);
		config.addDefault("Spawn Smoke Demon ", true);
		config.addDefault("Spawn Alpha Spider ", true);
		config.addDefault("Spawn Dead Miner ", true);
		config.addDefault("Spawn Hexed Armor ", true);
		config.addDefault(":::::Monster Names - no Blanks:::::", "");
		config.addDefault("Crying Bat = ", "Crying Bat");
		config.addDefault("Magma Monster = ", "Magma Monster");
		config.addDefault("Lava Creeper = ", "Lava Creeper");
		config.addDefault("TnT Creeper = ", "TnT Infused Creeper");
		config.addDefault("Watcher = ", "Watcher");
		config.addDefault("Smoke Demon = ", "Smoke Demon");
		config.addDefault("Alpha Spider = ", "Alpha Spider");
		config.addDefault("Dead Miner = ", "Dead Miner");
		config.addDefault("Hexed Armor = ", "Hexed Armor");
		config.addDefault(":::::::::::::::::::::::::::::::::::", "");
		config.addDefault("Crying Bat Chance ", 0);
		config.addDefault("Magma Monster Chance ", 0);
		config.addDefault("Lava Creeper Chance ", 0);
		config.addDefault("TnT Creeper Chance ", 0);
		config.addDefault("Watcher Chance ", 0);
		config.addDefault("Smoke Demon Chance ", 0);
		config.addDefault("Alpha Spider Chance ", 0);
		config.addDefault("Dead Miner Chance ", 0);
		config.addDefault("Hexed Armor Chance ", 0);
		config.addDefault(":::::::::::::::::::::::::::::::::::", "");
		List<String> listitem1 = new ArrayList<String>();
		listitem1.add("Wow, it's hot down here.  I better bring something to cool myself off next time.");
		listitem1.add("It is realllyyy hot down here.");
		listitem1.add("I'm going to need something to cool myself off next time.");
		listitem1.add("Is it hot in here, or is it just me?");
		listitem1.add("I really need some ice.");
		config.addDefault("Temperature Messages ", listitem1);
		List<String> listitem2 = new ArrayList<String>();
		boolean hasWorldR = false;
		for(World w : Bukkit.getWorlds()) {
			if(w.getName().equals("world")) {
				hasWorldR = true;
			}
		}
		if(hasWorldR == true) {
			listitem2.add("world");
		}
		else {
			listitem2.add(Bukkit.getWorlds().get(0).getName());
		}
		config.addDefault("Enabled Worlds - If Left Blank Will Just Use default world ", listitem2);
		List<String> listitem3 = new ArrayList<String>();
		listitem3.add("SUGAR");
		listitem3.add("TORCH");
		listitem3.add("DIRT");
		config.addDefault("Items that can spawn in chests ", listitem3);
		//config.getString("Dead Miner = ")
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler
	public void setWorld(PlayerJoinEvent e) {
		//for(String namew : worlds) {
			//World wor = Bukkit.getWorld(namew);
			//wor = e.getPlayer().getWorld();
			//List<BlockPopulator> bp = new ArrayList<BlockPopulator>(wor.getPopulators());
			//for(BlockPopulator bps : bp) {
			//	if(bps.toString().length()>=25) {
			//		if(bps.toString().substring(0, 25).equals("mainPackage.CaveGenerator")) {
			//			wor.getPopulators().remove(bps);
			//		}
			//	}
			//}
			//wor.getPopulators().clear();
			//wor.getPopulators().add(new CaveGenerator());
			//console.sendMessage("" + e.getPlayer().getWorld().getPopulators());
		if(hasWorlds==false) {
			caveins = config.getBoolean("Enable Cave-Ins ");
			hungerdark = config.getBoolean("Enable Hungering Darkness ");
			ambients = config.getBoolean("Enable Ambient Sounds ");
			cavetemp = config.getBoolean("Enable Cave Temperature ");
			caveage = config.getBoolean("Enable Cave Aging ");
			caveents = config.getBoolean("Enable Cave Monsters ");
			cavechance = config.getInt("Cave Structure Chance ");
			cavestruct = config.getBoolean("Enable Cave Structures ");
		}
		//}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("getworldname")) {
			if (sender instanceof Player) {
				console.sendMessage(ChatColor.GREEN + "World Name = " + ChatColor.RESET + " " + ((Player) sender).getWorld().getName());
					return true;
			}
		}
	return true;
	}

	public Material getRandStone(int define) {
		if(define == 1) {
			if(randor.nextBoolean()==true) {
				return Material.STONE;
			}
			else {
				return Material.COBBLESTONE;
			}
		}
		else {
			return Material.AIR;
		}
	}
	
	//The Darkness
	
	public String isMetad(LivingEntity e) {
		if(e.hasMetadata(config.getString("Watcher = "))) {
			return config.getString("Watcher = ");
		}
		else if(e.hasMetadata(config.getString("Magma Monster = "))) {
			return config.getString("Magma Monster = ");
		}
		else if(e.hasMetadata(config.getString("Lava Creeper = "))) {
			return config.getString("Lava Creeper = ");
		}
		else if(e.hasMetadata(config.getString("TnT Creeper = "))) {
			return config.getString("TnT Creeper = ");
		}
		else if(e.hasMetadata(config.getString("Smoke Demon = "))) {
			return config.getString("Smoke Demon = ");
		}
		else if(e.hasMetadata(config.getString("Alpha Spider = "))) {
			return config.getString("Alpha Spider = ");
		}
		else if(e.hasMetadata(config.getString("Dead Miner = "))) {
			return config.getString("Dead Miner = ");
		}
		else if(e.hasMetadata(config.getString("Hexed Armor = "))) {
			return config.getString("Hexed Armor = ");
		}
		else if(e.hasMetadata("The Darkness")) {
			return "The Darkness";
		}
		return "null";
	}
	
	public boolean fixMonster(LivingEntity e) {
		if(!isMetad(e).equals("null")) {
			String name = isMetad(e);
			if (e != null && !e.isDead()) {
				try {
				if(name.equals(config.getString("Watcher = "))) {
					ItemStack helmet = e.getEquipment().getHelmet();
					if(helmet == null) {
						e.setSilent(false);
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Watcher = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Watcher = "))) {
							e.removeMetadata(config.getString("Watcher = "), this);
						}
						return true;
					}
					else if(helmet.getType() != Material.SKULL_ITEM) {
						e.setSilent(false);
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Watcher = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Watcher = "))) {
							e.removeMetadata(config.getString("Watcher = "), this);
						}
						return true;
					}
				}
				else if(name.equals(config.getString("TnT Creeper = "))) {
					String cus = e.getCustomName();
					if(e.getType()!=EntityType.CREEPER) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("TnT Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("TnT Creeper = "))) {
							e.removeMetadata(config.getString("TnT Creeper = "), this);
						}
						return true;
					}
					else if(cus == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("TnT Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("TnT Creeper = "))) {
							e.removeMetadata(config.getString("TnT Creeper = "), this);
						}
						return true;
					}
					else if(!cus.equals(config.getString("TnT Creeper = "))) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("TnT Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("TnT Creeper = "))) {
							e.removeMetadata(config.getString("TnT Creeper = "), this);
						}
						return true;
					}
				}
				else if(name.equals(config.getString("Lava Creeper = "))) {
					String cus = e.getCustomName();
					if(e.getType()!=EntityType.CREEPER) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Lava Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Lava Creeper = "))) {
							e.removeMetadata(config.getString("Lava Creeper = "), this);
						}
						return true;
					}
					else if(cus == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Lava Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Lava Creeper = "))) {
							e.removeMetadata(config.getString("Lava Creeper = "), this);
						}
						return true;
					}
					else if(!cus.equals(config.getString("Lava Creeper = "))) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Lava Creeper = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Lava Creeper = "))) {
							e.removeMetadata(config.getString("Lava Creeper = "), this);
						}
						return true;
					}
				}
				else if(name.equals(config.getString("Dead Miner = "))) {
					ItemStack helmet = e.getEquipment().getHelmet();
					if(helmet == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Dead Miner = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Dead Miner = "))) {
							e.removeMetadata(config.getString("Dead Miner = "), this);
						}
						e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
						e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
						return true;
					}
					else if(helmet.getType() != Material.SKULL_ITEM) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Dead Miner = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Dead Miner = "))) {
							e.removeMetadata(config.getString("Dead Miner = "), this);
						}
						e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
						e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
						return true;
					}
				}
				else if(name.equals(config.getString("Magma Monster = "))) {
					ItemStack boot = e.getEquipment().getBoots();
					if(boot == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Magma Monster = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Magma Monster = "))) {
							e.removeMetadata(config.getString("Magma Monster = "), this);
						}
						e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
						e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						}
						return true;
					}
					else if(boot.getType()!=Material.LEATHER_BOOTS) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Magma Monster = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Magma Monster = "))) {
							e.removeMetadata(config.getString("Magma Monster = "), this);
						}
						e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
						e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						}
						return true;
					}
				}
				else if(name.equals(config.getString("Smoke Demon = "))) {
					String cus = e.getCustomName();
					if(e.getType()!=EntityType.ZOMBIE) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Smoke Demon = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Smoke Demon = "))) {
							e.removeMetadata(config.getString("Smoke Demon = "), this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						return true;
					}
					else if(cus == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Smoke Demon = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Smoke Demon = "))) {
							e.removeMetadata(config.getString("Smoke Demon = "), this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						return true;
					}
					else if(!cus.equals(config.getString("Smoke Demon = "))) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals(config.getString("Smoke Demon = "))) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata(config.getString("Smoke Demon = "))) {
							e.removeMetadata(config.getString("Smoke Demon = "), this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						return true;
					}
				}
				else if(name.equals("The Darkness")) {
					String cus = e.getCustomName();
					if(e.getType()!=EntityType.HUSK) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals("The Darkness")) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata("The Darkness")) {
							e.removeMetadata("The Darkness", this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						}
						((LivingEntity) e).setCollidable(true);
						return true;
					}
					else if(cus == null) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals("The Darkness")) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata("The Darkness")) {
							e.removeMetadata("The Darkness", this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						}
						((LivingEntity) e).setCollidable(true);
						return true;
					}
					else if(!cus.equals("The Darkness")) {
						e.setSilent(false);
						if(e.getCustomName()!=null) {
							if(e.getCustomName().equals("The Darkness")) {
								e.setCustomName("");
							}
						}
						if(e.hasMetadata("The Darkness")) {
							e.removeMetadata("The Darkness", this);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						}
						if(((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						}
						((LivingEntity) e).setCollidable(true);
						return true;
					}
				}
				return false;
				}
				catch(Exception error) {
					return false;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent event) {
		try {
		if(hungerdark==true) {
			if(config.getBoolean("Enable Monster Fixing ")) {
			if(!worlds.contains(event.getEntity().getWorld().getName())) {
				return;
			}
				World wor = event.getEntity().getWorld();
		if(event.getEntity() instanceof Monster) {
			if(fixMonster((LivingEntity) event.getEntity())==false) {
		Monster e = (Monster) event.getEntity();
			if(e.getCustomName()!=null) {
				if(e.getCustomName().equals("The Darkness")||e.hasMetadata("The Darkness")) {
					if(event.getTarget().getLocation().getBlock().getLightLevel()>0) {
						event.setTarget(null);
					}
					else {
						try {
						effectEnts.add(event.getEntity());
						}
						catch(Exception error) {
							console.sendMessage(ChatColor.RED + "Uh oh error inside targeting.");
						}
					}
				}
			}
		}
		}
			}
			else {
				if(!worlds.contains(event.getEntity().getWorld().getName())) {
					return;
				}
					World wor = event.getEntity().getWorld();
			if(event.getEntity() instanceof Monster) {
				if(fixMonster((LivingEntity) event.getEntity())==false) {
			Monster e = (Monster) event.getEntity();
				if(e.getCustomName()!=null) {
					if(e.getCustomName().equals("The Darkness")||e.hasMetadata("The Darkness")) {
						if(event.getTarget().getLocation().getBlock().getLightLevel()>0) {
							event.setTarget(null);
						}
						else {
							try {
							effectEnts.add(event.getEntity());
							}
							catch(Exception error) {
								console.sendMessage(ChatColor.RED + "Uh oh error inside targeting.");
							}
						}
					}
				}
			}
			}
			}
		}
		if(caveents==true) {
		if(event.getEntity() instanceof Monster) {
			if(config.getBoolean("Enable Monster Fixing ")) {
			if(fixMonster((LivingEntity) event.getEntity())==false) {
			Monster e = (Monster) event.getEntity();
				if(e.getCustomName()!=null) {
					try {
					if(e.getCustomName().equals(config.getString("Watcher = "))||e.hasMetadata(config.getString("Watcher = "))) {
						if(e.getCustomName()!=null) {
							String name = e.getCustomName();
							if(name.toLowerCase().contains("elite")) {
								e.remove();
							}
						}
					}
					else if(e.getCustomName().equals(config.getString("Magma Monster = "))||e.hasMetadata(config.getString("Magma Monster = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Dead Miner = "))||e.hasMetadata(config.getString("Dead Miner = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Lava Creeper = "))||e.hasMetadata(config.getString("Lava Creeper = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Smoke Demon = "))||e.hasMetadata(config.getString("Smoke Demon = "))) {
						effectEnts.add(event.getEntity());
					}
					}
					catch(Exception error) {
						console.sendMessage(ChatColor.RED + "Uh oh error inside targeting.");
					}
				}
		}
			}
			else {
				Monster e = (Monster) event.getEntity();
				if(e.getCustomName()!=null) {
					try {
					if(e.getCustomName().equals(config.getString("Watcher = "))||e.hasMetadata(config.getString("Watcher = "))) {
							effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Magma Monster = "))||e.hasMetadata(config.getString("Magma Monster = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Dead Miner = "))||e.hasMetadata(config.getString("Dead Miner = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Lava Creeper = "))||e.hasMetadata(config.getString("Lava Creeper = "))) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals(config.getString("Smoke Demon = "))||e.hasMetadata(config.getString("Smoke Demon = "))) {
						effectEnts.add(event.getEntity());
					}
					}
					catch(Exception error) {
						console.sendMessage(ChatColor.RED + "Uh oh error inside targeting.");
					}
				}
			}
			}
		}
		}
		catch(Exception e) {
			
		}
	}
	
	public void betterEffectLooper() {
		if(!effectEnts.isEmpty()) {
		List<Entity> tempEntss = new ArrayList<Entity>(effectEnts);
		for(Entity e : tempEntss) {
			LivingEntity e2 = (LivingEntity) e;
			if(!worlds.contains(e2.getWorld().getName())) {
				effectEnts.remove(e);
			}
			else {
			if(e != null) {
				if(!e.isDead()) {
					if(e instanceof Monster) {
						if(((Monster) e).getTarget()!=null&&(!(((Monster) e).getTarget().isDead()))) {
							String name = e2.getCustomName();
							if(name!=null) {
								boolean continuee = true;
								if(config.getBoolean("Enable Broken Monster Deletion ")==true) {
									if(name.toLowerCase().contains("elite")) {
										continuee = false;
										effectEnts.remove(e);
										e.remove();
									}
									else {
										continuee = true;
									}
								}
								else {
									continuee = true;
								}
								if(continuee==true) {
								if(name.equals("The Darkness")||e.hasMetadata("The Darkness")) {
									try {
									if((e.getLocation().getBlock().getLightLevel()>0)||(e.getFireTicks()>0)) {
										e.remove();
									}
									else if(((LivingEntity) (((Monster) e).getTarget())).hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
										e.remove();
									}
									else if((((Monster) e).getTarget().getLocation().getBlock().getLightLevel()==0)){
										e.getWorld().playSound(e.getLocation(), Sound.ENTITY_CAT_PURR, (float) .5, 0);
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 1 : " + error.getCause());
									}
								}
								else if(name.equals(config.getString("Watcher = "))||e.hasMetadata(config.getString("Watcher = "))) {
									try {
									LivingEntity e3 = ((Monster) e).getTarget();
									if(e3 instanceof Player) {
									if(e3!=null&&(!e3.isDead())) {
									if(getLookingAt2(e3, (LivingEntity) e)==false) {
										Player p = (Player) e3;
										if(p!=null&&(!p.isDead())) {
										Location loc = getBlockInFrontOfPlayer(p);
										double newYaw = 0;
										double newPitch = 0;
										if (p.getLocation().getYaw() < 0) {
											newYaw = p.getLocation().getYaw() + 180;
										} else {
											newYaw = p.getLocation().getYaw() - 180;
										}
										newPitch = p.getLocation().getPitch() * -1;
										Location jumpLoc = new Location(p.getWorld(), loc.getX(), p.getLocation().getY(), loc.getZ(),
												((float) newYaw), ((float) newPitch));
										e.teleport(jumpLoc);
										e.setVelocity(new Vector(0, 0, 0));
										p.setVelocity(new Vector(0, 0, 0));
										p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 200));
										p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_HURT, 1, 2);
										p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 2));
										}
									}}
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 2 : " + error.getCause());
									}
								}
								else if(name.equals(config.getString("Dead Miner = "))||e.hasMetadata(config.getString("Dead Miner = "))) {
									try {
									if(e.getLocation().getBlock().getLightLevel()==0) {
										e.getLocation().getBlock().setType(Material.TORCH);
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 3 : " + error.getCause());
									}
								}
								else if(name.equals(config.getString("Magma Monster = "))||e.hasMetadata(config.getString("Magma Monster = "))) {
									try {
									if(randor.nextInt(14)==1) {
										e.getLocation().getBlock().setType(Material.FIRE);
									}
									if(randor.nextInt(28)==1) {
										e.getLocation().subtract(0, 1, 0).getBlock().setType(Material.MAGMA);
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 4 : " + error.getCause());
									}
								}
								else if(name.equals(config.getString("Lava Creeper = "))||e.hasMetadata(config.getString("Lava Creeper = "))) {
									try {
									if(randor.nextInt(24)==1) {
										e.getWorld().spawnParticle(Particle.LAVA, e.getLocation().add(0, 1, 0), 1);
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 5 : " + error.getCause());
									}
								}
								else if(name.equals(config.getString("Smoke Demon = "))||e.hasMetadata(config.getString("Smoke Demon = "))) {
									try {
									if(e.getLocation().getBlock().getLightLevel()<12) {
										List<Entity> ents = e.getNearbyEntities(3, 3, 3);
										for(Entity es : ents) {
											if(es instanceof LivingEntity) {
												((LivingEntity) es).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,120,1));
												((LivingEntity) es).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,120,0));
											}
										}
										e.getWorld().spawnParticle(Particle.CLOUD, e.getLocation().add(0, 1, 0), 60, 1,1,1,0.00f/*, m*/);
									}
									else {
										e.remove();
									}
									}
									catch(Exception error) {
										console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 6 : " + error.getCause());
									}
								}
								}
							}
							else {
								effectEnts.remove(e);	
							}
							}
						else {
							effectEnts.remove(e);	
						}
						}
					else if(e instanceof Bat) {
						String name = e2.getCustomName();
						if(name!=null) {
							if(name.equals(config.getString("Crying Bat = "))||e.hasMetadata(config.getString("Crying Bat = "))) {
								try {
								if(randor.nextInt(30)==1) {
									e.getWorld().playSound(e.getLocation(), Sound.ENTITY_WOLF_WHINE, 1, (float) (1.4+(randor.nextInt(5+1)/10.0)));
									if(randor.nextInt(5)==1) {
										((Bat) e).damage(999999);
									}
								}
								}
								catch(Exception error) {
									console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper. 7 : " + error.getCause());
								}
							}
						}
						else {
							effectEnts.remove(e);	
						}
					}
					else {
						effectEnts.remove(e);	
					}
					}
				else {
					effectEnts.remove(e);	
				}
		}
			else {
				effectEnts.remove(e);	
			}
			}
			}
		}
		}
	
	public static Location getBlockInFrontOfPlayer(LivingEntity entsa) {
		Vector inverseDirectionVec = entsa.getLocation().getDirection().normalize().multiply(1);
		return entsa.getLocation().add(inverseDirectionVec);
	}
	
	@EventHandler
	public void onMobName(PlayerInteractEntityEvent event) {
		if(!worlds.contains(event.getRightClicked().getWorld().getName())) {
			return;
		}
			World wor = event.getRightClicked().getWorld();
		if(!(event.getRightClicked() instanceof Player)) {
			ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
			if(i.getType()==Material.NAME_TAG) {
				if(i.hasItemMeta()) {
					if(i.getItemMeta().hasDisplayName()) {
						String s = i.getItemMeta().getDisplayName();
						if(mobNames.contains(s)) {
							removeItemNaturally(event.getPlayer());
							if(event.getRightClicked() instanceof LivingEntity && (!(event.getRightClicked() instanceof Player))) {
								((LivingEntity) event.getRightClicked()).setCustomName("");
								event.setCancelled(true);
							}
							else {
								event.getRightClicked().remove();
							}
						}
					}
				}
			}
		}
	}
	
	public static void removeItemNaturally(Player p) {
		try {
		if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
			//p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			p.getInventory().getItemInMainHand().setAmount(0);
		} else {
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
		}
		}
		catch(Exception error) {
			
		}
	}
	
	@EventHandler
	public void deleteLightLevel(CreatureSpawnEvent event) {
		if(!worlds.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		World wor = event.getEntity().getWorld();
		if(hungerdark==true||caveents==true) {
		Entity e = event.getEntity();
		try {
		if(e.getLocation().subtract(0, 1, 0).getBlock()==null) {
			return;
		}
		else if(randor.nextInt(config.getInt("Darkness Spawn Chance ")+1)!=0||hungerdark==false) {
			if(caveents==true) {
			if (e != null && !e.isDead()) {
				if ((e instanceof Creeper || e instanceof Skeleton || e instanceof Zombie || e instanceof Spider) && (!(e instanceof PigZombie || e instanceof ZombieVillager || e instanceof Husk))) {
					if ((e instanceof Monster && (event.getSpawnReason() == SpawnReason.NATURAL&&event.getSpawnReason() != SpawnReason.REINFORCEMENTS) && (e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.STONE || e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.DIRT))) {
						if (e.getLocation().getY() <= config.getInt("Monster Spawning Highest Y ")&&e.getLocation().getY() >= config.getInt("Monster Spawning Lowest Y ")) {
								doMobSpawns(e);
							}
					  }
				}
				}
			}
			else {
				return;
			}
		}
		else {
			if(hungerdark==true) {
		if (e != null && !e.isDead()) {
		if ((e instanceof Creeper || e instanceof Skeleton || e instanceof Zombie || e instanceof Spider) && (!(e instanceof PigZombie || e instanceof ZombieVillager || e instanceof Husk))) {
			if ((e instanceof Monster && event.getSpawnReason() == SpawnReason.NATURAL && (e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.STONE || e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.DIRT))) {
				if (e.getLocation().getY() <= config.getInt("Monster Spawning Highest Y ")&&e.getLocation().getY() >= config.getInt("Monster Spawning Lowest Y ")) {
					doDarkness(e);
					}
			  }
		}
		}
		}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside spawning method.");
		}
		}
	}
	
	@EventHandler
    public void onDeath(EntityDeathEvent e) {
		if(hungerdark==true) {
			if(!worlds.contains(e.getEntity().getWorld().getName())) {
				return;
			}
				World wor = e.getEntity().getWorld();
			try {
        if (e.getEntity().getCustomName()!=null) {
        	if(e.getEntity().getCustomName().equals("The Darkness")) {
        		List<ItemStack> drops = e.getDrops();
        		for(ItemStack i : drops) {
        			i.setType(Material.AIR);
        		}
        	}
        }
        if(e.getEntity().hasMetadata("The Darkness")) {
        	List<ItemStack> drops = e.getDrops();
            for(ItemStack i : drops) {
            	i.setType(Material.AIR);
            }
        }
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside death event.");
			}
		}
	}

	public void doDarkness(Entity entitye) {
		try {
		LivingEntity e = (LivingEntity) entitye;
		if (e != null && !e.isDead()) {
			if(e.getType()!=EntityType.HUSK) {
				Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
				e.remove();
				e = (LivingEntity) e2;
			}
			String name = "The Darkness";
			e.setCustomName(name);
			e.setMetadata(name, new FixedMetadataValue(this, 0));
			e.setMetadata("R", new FixedMetadataValue(this, 0));
			e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 200, false, false));
			e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2, false, false));
			e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, damage, false, false));
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 3, false, false));
			e.setSilent(true);
			e.setCanPickupItems(false);
			e.setCustomNameVisible(false);
			e.setCollidable(false);
		}
		return;
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside darkness dressing.");
		}
	}

	@EventHandler
    public void onDamage(EntityDamageEvent e) {
		if(hungerdark==true) {
			if(!worlds.contains(e.getEntity().getWorld().getName())) {
				return;
			}
				World wor = e.getEntity().getWorld();
			try {
	        if (e.getEntity().getCustomName()!=null) {
	        	if(e.getEntity().getCustomName().equals("The Darkness")) {
	        		if(((LivingEntity) e.getEntity()).getHealth()-e.getFinalDamage()<=0) {
	        			e.getEntity().remove();
	        			e.setCancelled(true);
	        		}
	        	}
	        }
	        if(e.getEntity().hasMetadata("The Darkness")) {
	        	if(((LivingEntity) e.getEntity()).getHealth()-e.getFinalDamage()<=0) {
        			e.getEntity().remove();
        			e.setCancelled(true);
        		}
	        }
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside Damage Event.");
			}
		}
	}
	
	@EventHandler
    public void onDamageP(EntityDamageByEntityEvent e) {
		try {
		if(!worlds.contains(e.getEntity().getWorld().getName())) {
			return;
		}
			World wor = e.getEntity().getWorld();
		if(e.getEntity() instanceof Player) {
			if(e.getDamager().getCustomName()!=null) {
				if(e.getDamager().getCustomName().equals("The Darkness")) {
					if(e.getEntity().getLocation().getBlock().getLightLevel()>0) {
						e.setCancelled(true);
						e.setDamage(0);
					}
				}
			}
			if(e.getEntity().hasMetadata("The Darkness")) {
				if(e.getEntity().getLocation().getBlock().getLightLevel()>0) {
				e.setCancelled(true);
				e.setDamage(0);
				}
			}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside onDamageP.");
		}
	}
	
	//
	
	//Ambient Sounds
	
	public void doCaveSounds() {
		try {
		for(String namew : worlds) {
		World wor = Bukkit.getWorld(namew);
		if (wor != null) {
			for (Player p : wor.getPlayers()) {
				if (randor.nextInt(config.getInt("Cave Ambience Chance ")+1) == 0) {
					int choice = randor.nextInt(6);
					if (p.getLocation().getY() < 47) {
						if (p.getLocation().getBlock().getLightFromSky() <= 1) {
							if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.STONE
									|| p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.DIRT) {
								if (choice == 0) {
									p.playSound(p.getLocation(), Sound.MUSIC_DRAGON, SoundCategory.AMBIENT, 100, 0);
								} else if (choice == 1) {
									p.playSound(p.getLocation(), Sound.MUSIC_NETHER, SoundCategory.AMBIENT, 100, (float) .5);
								} else if (choice == 2) {
									if (randor.nextInt(5) == 1) {
										try {
										p.playSound(p.getLocation(), Sound.valueOf("RECORD_11"), SoundCategory.AMBIENT, 100, (float) .5);
										}
										catch(Exception e) {
											try {
												p.playSound(p.getLocation(), Sound.valueOf("MUSIC_DISC_11"), SoundCategory.AMBIENT, 100, (float) .5);
												}
												catch(Exception e2) {
													
												}
										}
									}
								} else if (choice == 3) {
									try {
										p.playSound(p.getLocation(), Sound.valueOf("RECORD_13"), SoundCategory.AMBIENT, 100, (float) .5);
										}
										catch(Exception e) {
											try {
												p.playSound(p.getLocation(), Sound.valueOf("MUSIC_DISC_13"), SoundCategory.AMBIENT, 100, (float) .5);
												}
												catch(Exception e2) {
													
												}
										}
								} else if (choice == 4) {
									try {
									p.playSound(p.getLocation(), Sound.valueOf("RECORD_MELLOHI"), SoundCategory.AMBIENT, 100, (float) .5);
									}
									catch(Exception e) {
										try {
											p.playSound(p.getLocation(), Sound.valueOf("MUSIC_DISC_MELLOHI"), SoundCategory.AMBIENT, 100, (float) .5);
										}
										catch(Exception e2) {
											
										}
									}
								} else if (choice == 5) {
									if (randor.nextInt(6) == 1) {
										p.playSound(p.getLocation(), Sound.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, (float) .04, (float) .2);
									}
								}
							}
						}
					} else {
					}
				}
			}
		}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper.");
		}
	}
	
	//
	
	//Cave Aging
	
	public void doCaveStuff(Player p) {
		if(!worlds.contains(p.getWorld().getName())){
			return;
		}
		try {
			World wor = p.getWorld();
		if (p.getLocation().getBlock().getLightFromSky() > 0) {
			return;
		}
		if (p.getLocation().getY() > 49) {
			return;
		}
		if (p.getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		if ((p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.STONE
				&& p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.DIRT)) {
			return;
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside p1 cavestuff.");
			return;
		}
		if (randor.nextInt(config.getInt("Cave Aging Chance ")+1) == 0) {
			World wor = p.getWorld();
			if (wor != null) {
				Random rand = new Random();
				Location loc = p.getLocation();
				int radius = 45;
				int cx = loc.getBlockX();
				int cy = loc.getBlockY();
				int cz = loc.getBlockZ();
				for (int x = cx - radius; x <= cx + radius; x++) {
					for (int z = cz - radius; z <= cz + radius; z++) {
						for (int y = (cy - radius); y < (cy + radius); y++) {
							double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

							if (dist < radius * radius) {
								try {
								if (rand.nextInt(config.getInt("Cave Aging Change Chance ")+1) == 0) {
									Location l = new Location(loc.getWorld(), x, y, z);
									doCaveBlocks(l.getBlock(), p);
								}
								}
								catch(Exception error) {
									console.sendMessage(ChatColor.RED + "Uh oh error inside p2 cavestuff.");
								}
							}

						}
					}
				}
			}
		}
	}

	public void doCaveBlocks(org.bukkit.block.Block b, Player p) {
		try {
		if (b.getLocation().getY() > 50 || b.getLightFromSky() > 0) {
			return;
		}
		if ((b.getType() == Material.STONE && b.getData() == 0) && randor.nextInt(2) == 1) {
			int chose = randor.nextInt(3);
			if (chose == 0) {
				b.setType(Material.COBBLESTONE);
			} else if (chose == 1) {
				b.setData((byte) 5);
			} else {
				org.bukkit.block.Block b2 = b.getLocation().subtract(0, 1, 0).getBlock();
				if (randor.nextInt(2) == 1 && b2.getType() == Material.AIR) {
					b2.setType(Material.COBBLE_WALL);
				}
			}
		}
		if (b.getType() == Material.COBBLE_WALL) {
			if (randor.nextInt(3) == 1) {
				b.setType(Material.COBBLESTONE);
			} else {
				org.bukkit.block.Block b2 = b.getLocation().subtract(0, 1, 0).getBlock();
				if (randor.nextInt(2) == 1 && b2.getType() == Material.AIR) {
					b2.setType(Material.COBBLE_WALL);
				}
			}
		}
		if (((b.getType() == Material.STONE && b.getData() == 0) || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(8) == 1) {
			doVines2(b.getLocation());
		}
		if (((b.getType() == Material.STONE && b.getData() == 0) || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(9) == 1) {
			if (b.getRelative(BlockFace.UP) != null) {
				if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
					b.getRelative(BlockFace.UP).setType(Material.BROWN_MUSHROOM);
					b.getRelative(BlockFace.UP).setType(Material.RED_MUSHROOM);
				}
			}
		}
		if (((b.getType() == Material.STONE && b.getData() == 0) || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(6) == 1) {
			if (b.getRelative(BlockFace.UP) != null) {
				if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
					b.getRelative(BlockFace.UP).setType(Material.STONE_BUTTON);
					b.getRelative(BlockFace.UP).setData((byte) 5);
				}
			}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside cave blocks.");
		}
	}

	public void doVines2(Location l) {
		World wor = l.getWorld();
		try {
		if (wor != null) {

			Location loc = l;
			org.bukkit.block.Block b = l.getBlock();
			if (b != null) {
				if (b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2
						|| b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.STONE
						|| b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL) {
					BlockFace[] blocksf = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
					for (BlockFace blockface : blocksf) {
						org.bukkit.block.Block b2 = l.getBlock().getRelative(blockface);
						if (b2 != null) {
							if (b2.getType() == Material.AIR) {
								b2.setType(Material.VINE);
								if (b2.getState().getData() instanceof Vine) {
									Vine v = (Vine) b2.getState().getData();
									v.putOnFace(oppisiteBf(blockface));
									b2.setData(v.getData());
									b2.getState().update(true);
								}
							}
						}
					}
				}
			}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside vines.");
		}
	}

	public BlockFace oppisiteBf(BlockFace be) {
		if (be == BlockFace.EAST) {
			return BlockFace.WEST;
		} else if (be == BlockFace.WEST) {
			return BlockFace.EAST;
		} else if (be == BlockFace.NORTH) {
			return BlockFace.SOUTH;
		} else {
			return BlockFace.NORTH;
		}
	}
	
	//
	
	//Cave Temperature
	
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
	if(cavetemp==true) {
		try {
	Player p = event.getPlayer();
	if((!worlds.contains(p.getWorld().getName()))||(p.getLocation().getBlock().getBiome().toString().contains("OCEAN"))) {
		return;
	}
	World wor = p.getWorld();
	if(randor.nextInt(config.getInt("Cave Walk Temp Chance ")+1)==0) {
		if(p.getLocation().getY()<30&&(p.getGameMode()!=GameMode.CREATIVE)) {
		if(p.getInventory().contains(Material.POTION)||p.getInventory().contains(Material.SPLASH_POTION)||p.getInventory().contains(Material.SNOW)||p.getInventory().contains(Material.SNOW_BALL)||p.getInventory().contains(Material.SNOW_BLOCK)||p.getInventory().contains(Material.WATER_BUCKET)||p.getInventory().contains(Material.ICE)||p.getInventory().contains(Material.FROSTED_ICE)||p.getInventory().contains(Material.PACKED_ICE)) {
		}
		else if(p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
		}
		else {
			if(hotmessage.size()!=0) {
			int msg = randor.nextInt(hotmessage.size());
			p.sendMessage(ChatColor.RED + hotmessage.get(msg));
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,120 ,1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,55 ,1));
		}
		}
	}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside moving.");
		}
	}
	}
	
	//
	
	//Cave Ins
	
	@EventHandler
	public void onBreakB(BlockBreakEvent dr) {
		Player p = dr.getPlayer();
		if(!worlds.contains(p.getWorld().getName())) {
			return;
		}
		World wor = p.getWorld();
		if(caveins==true) {
			try {
		if(randor.nextInt(config.getInt("Cave-In Chance ")+1)==0) {
		if(dr.getPlayer().getLocation().getY()<25) {
			if((dr.getBlock().getType()==Material.STONE||dr.getBlock().getType()==Material.DIRT)&&(p.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.STONE||p.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.DIRT)) {
			if(p.getInventory().contains(Material.RABBIT_FOOT)) {
				
			}
			else {
			Location loc = p.getLocation();
			int radius = 7;
			int cx = loc.getBlockX();
			int cy = loc.getBlockY();
			int cz = loc.getBlockZ();
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,65 ,3));
			for (int x = cx - radius; x <= cx + radius; x++) {
				for (int z = cz - radius; z <= cz + radius; z++) {
					for (int y = (cy - radius); y < (cy + radius); y++) {
						double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

						if (dist < radius * radius) {
							Location l = new Location(loc.getWorld(), x, y + 2, z);
							Block b = l.getBlock();
							if(b.getType()==Material.AIR||b.getType()==Material.BEDROCK) {
							}
							else {
								b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
								l.getBlock().setType(Material.AIR);
							}
						}
					}
				}
			}
			}
			}
		}
		}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside cave-in.");
			}
		}
		if(cavetemp==true&&(!p.getLocation().getBlock().getBiome().toString().contains("OCEAN"))) {
			try {
		if(randor.nextInt(config.getInt("Cave Break Block Temp Chance ")+1)==0) {
			if(p.getLocation().getY()<30&&(p.getGameMode()!=GameMode.CREATIVE)) {
			if(p.getInventory().contains(Material.POTION)||p.getInventory().contains(Material.SPLASH_POTION)||p.getInventory().contains(Material.SNOW)||p.getInventory().contains(Material.SNOW_BALL)||p.getInventory().contains(Material.SNOW_BLOCK)||p.getInventory().contains(Material.WATER_BUCKET)||p.getInventory().contains(Material.ICE)||p.getInventory().contains(Material.FROSTED_ICE)||p.getInventory().contains(Material.PACKED_ICE)) {
			}
			else if(p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
			}
			else {
				if(hotmessage.size()!=0) {
					int msg = randor.nextInt(hotmessage.size());
					p.sendMessage(ChatColor.RED + hotmessage.get(msg));
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,120 ,1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,55 ,1));
			}
			}
		}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper.");
			}
		}
	}
	
	//
	
	//Cave Mobs
	
	public String mobTypes() {
		int choice = randor.nextInt(9);
		try {
		if(choice == 0) {
			if(config.getBoolean("Spawn Watcher ")==true) {
			return config.getString("Watcher = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 1) {
			if(config.getBoolean("Spawn TnT Creeper ")==true) {
			return config.getString("TnT Creeper = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 2) {
			if(config.getBoolean("Spawn Dead Miner ")==true) {
			return config.getString("Dead Miner = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 3) {
			if(config.getBoolean("Spawn Lava Creeper ")==true) {
			return config.getString("Lava Creeper = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 4) {
			if(config.getBoolean("Spawn Magma Monster ")==true) {
			return config.getString("Magma Monster = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 5) {
			if(config.getBoolean("Spawn Smoke Demon ")==true) {
			return config.getString("Smoke Demon = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 6) {
			if(config.getBoolean("Spawn Crying Bat ")==true) {
			if(randor.nextInt(20)==1) {
			return config.getString("Crying Bat = ");
			}
			else {
				return "";
			}
			}
			else {
				return "";
			}
		}
		else if(choice == 7) {
			if(config.getBoolean("Spawn Alpha Spider ")==true) {
			return config.getString("Alpha Spider = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 8) {
			if(config.getBoolean("Spawn Hexed Armor ")==true) {
			return config.getString("Hexed Armor = ");
			}
			else {
				return "";
			}
		}
		return "";
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside mob names.");
			return "";
		}
	}

	public void doMobSpawns(Entity entitye) {
			LivingEntity e = (LivingEntity) entitye;
			String name = mobTypes();
			if (e != null && !e.isDead()) {
				try {
				if(name.equals(config.getString("Watcher = "))&&(randor.nextInt(config.getInt("Watcher Chance ")+1)==0)) {
					//now teleports in front of the player if the player looks away from it
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
					e.remove();
					e = (LivingEntity) e2;
					e.setSilent(true);
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 200));
					EntityEquipment ee = ((LivingEntity) e).getEquipment();
					ItemStack myAwesomeSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
					myAwesomeSkullMeta.setOwner("Creegn");
					myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
					ee.setHelmet(myAwesomeSkull);
					ee.setHelmetDropChance(0);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					removeHand(e);
					e.setCanPickupItems(false);
				}
				else if(name.equals(config.getString("TnT Creeper = "))&&(randor.nextInt(config.getInt("TnT Creeper Chance ")+1)==0)) {
					if(e.getType()!=EntityType.CREEPER) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
					e.remove();
					e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Lava Creeper = "))&&(randor.nextInt(config.getInt("Lava Creeper Chance ")+1)==0)) {
					if(e.getType()!=EntityType.CREEPER) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
					e.remove();
					e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Dead Miner = "))&&(randor.nextInt(config.getInt("Dead Miner Chance ")+1)==0)) {
					if(e.getType()!=EntityType.ZOMBIE) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
					e.remove();
					e = (LivingEntity) e2;
					}
					EntityEquipment ee = ((LivingEntity) e).getEquipment();
					ItemStack myAwesomeSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
					myAwesomeSkullMeta.setOwner("wallabee");
					myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
					ee.setHelmet(myAwesomeSkull);
					ee.setHelmetDropChance(0);
					setMinerHands(e);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Magma Monster = "))&&(randor.nextInt(config.getInt("Magma Monster Chance ")+1)==0)) {
					if(e.getType()!=EntityType.ZOMBIE) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
						e.remove();
						e = (LivingEntity) e2;
					}
					EntityEquipment ee = (e).getEquipment();
					e.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 100, false, false));
					e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 100, false, false));
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lch6 = (LeatherArmorMeta) lchest.getItemMeta();
					lch6.setColor(Color.fromRGB(252, 115, 69));
					lchest.setItemMeta(lch6);
					ItemStack lchest2 = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta lch61 = (LeatherArmorMeta) lchest2.getItemMeta();
					lch61.setColor(Color.fromRGB(252, 115, 69));
					lchest2.setItemMeta(lch61);
					ItemStack lchest3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lch62 = (LeatherArmorMeta) lchest3.getItemMeta();
					lch62.setColor(Color.fromRGB(252, 115, 69));
					lchest3.setItemMeta(lch62);
					ee.setItemInMainHand(new ItemStack(Material.FIRE, 1));
					ee.setItemInOffHand(new ItemStack(Material.FIRE, 1));
					ee.setChestplate(lchest);
					ee.setLeggings(lchest3);
					ee.setBoots(lchest2);
					e.setFireTicks(999999);
					e.setCustomName(name);
					e.setSilent(true);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setCanPickupItems(false);
				}
				else if(name.equals(config.getString("Smoke Demon = "))&&(randor.nextInt(config.getInt("Smoke Demon Chance ")+1)==0)) {
					if(e.getType()!=EntityType.ZOMBIE) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
						e.remove();
						e = (LivingEntity) e2;
					}
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
					e.setSilent(true);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					removeHand(e);
					e.setCanPickupItems(false);
				}
				else if(name.equals(config.getString("Crying Bat = "))&&(randor.nextInt(config.getInt("Crying Bat Chance ")+1)==0)) {
					if(e.getType()!=EntityType.BAT) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.BAT);
						e.remove();
						e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					effectEnts.add(e);
				}
				else if(name.equals(config.getString("Alpha Spider = "))&&(randor.nextInt(config.getInt("Alpha Spider Chance ")+1)==0)) {
					if(!(e.getType()==EntityType.SPIDER||e.getType()==EntityType.CAVE_SPIDER)) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.SPIDER);
						e.remove();
						e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Hexed Armor = "))&&(randor.nextInt(config.getInt("Hexed Armor Chance ")+1)==0)) {
				if(e.getType()!=EntityType.ZOMBIE||e.getType()!=EntityType.SKELETON) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
					e.remove();
					e = (LivingEntity) e2;
				}
				((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
				DressCursed.dressDGolem((LivingEntity) e);
				e.setSilent(true);
				e.setCustomName(name);
				e.setMetadata(name, new FixedMetadataValue(this, 0));
				e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				}
				catch(Exception error) {
					console.sendMessage(ChatColor.RED + "Uh oh error inside dressing mob.");
				}
			}
				
			return;
	}
	
	public void setMinerHands(Entity e) {
		try {
		EntityEquipment ee = ((LivingEntity) e).getEquipment();
		// 0 == wood 1 == stone 2 == iron
		// 0 == left handed 1 == right handed
		// boolean true = torch in other hand
		int type = randor.nextInt(3);
		boolean holdtorch = randor.nextBoolean();
		int handside = randor.nextInt(2);
		boolean hasboots = randor.nextBoolean();
		boolean haschest = randor.nextBoolean();
		ItemStack pickaxe = null;
		if(type == 0) {
			pickaxe = new ItemStack(Material.WOOD_PICKAXE);
		}
		else if(type == 1) {
			pickaxe = new ItemStack(Material.STONE_PICKAXE);
		}
		else if(type == 2) {
			pickaxe = new ItemStack(Material.IRON_PICKAXE);
		}
		if(handside == 0) {
			ee.setItemInOffHand(pickaxe);
			if(holdtorch == true) {
				ee.setItemInMainHand(new ItemStack(Material.TORCH, 1));
			}
		}
		else {
			ee.setItemInMainHand(pickaxe);
			if(holdtorch == true) {
				ee.setItemInOffHand(new ItemStack(Material.TORCH, 1));
			}
		}
		if(hasboots==true) {
			if(randor.nextBoolean()==true) {
				ee.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
			}
			else {
				ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
			}
		}
		if(haschest==true) {
			if(randor.nextBoolean()==true) {
				ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
			}
			else {
				ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
			}
		}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside mining hands.");
		}
	}
	
	@EventHandler
	public void entityHit(EntityDamageByEntityEvent event) {
		if(!worlds.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		World wor = event.getEntity().getWorld();
		if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
			try {
			boolean isMagma = false;
			if((event.getDamager()).hasMetadata(config.getString("Magma Monster = "))) {
				isMagma = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals(config.getString("Magma Monster = "))){
					isMagma = true;
				}
			}
			if(isMagma == true) {
				if (randor.nextInt(2) == 1) {
					((LivingEntity) event.getEntity()).setFireTicks(60);
				}
				return;
			}
			boolean isAlpha = false;
			if((event.getDamager()).hasMetadata(config.getString("Alpha Spider = "))) {
				isAlpha = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals(config.getString("Alpha Spider = "))){
					isAlpha = true;
				}
			}
			if(isAlpha == true) {
				if (randor.nextInt(2) == 1) {
					if (randor.nextInt(5) == 1) {
						event.getEntity().getLocation().getBlock().setType(Material.WEB);
						event.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.WEB);
						Location loc = event.getEntity().getLocation();
						if (randor.nextInt(15) == 1) {
							event.getDamager().getWorld().spawnEntity(event.getDamager().getLocation(),
									EntityType.CAVE_SPIDER);
						}
						int radius = 4;
						int cx = loc.getBlockX();
						int cy = loc.getBlockY();
						int cz = loc.getBlockZ();
						for (int x = cx - radius; x <= cx + radius; x++) {
							for (int z = cz - radius; z <= cz + radius; z++) {
								for (int y = (cy - radius); y < (cy + radius); y++) {
									double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

									if (dist < radius * radius) {
										Location l = new Location(loc.getWorld(), x, y, z);
										if (randor.nextInt(7) == 1) {
											if (l.getBlock().getType() == Material.AIR) {
												l.getBlock().setType(Material.WEB);
											}
										}
									}
								}

							}
						}
					}
					((LivingEntity) event.getEntity())
					.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 75, 1));
				}
				return;
			}
			boolean isCurse = false;
			if((event.getDamager()).hasMetadata(config.getString("Hexed Armor = "))) {
				isCurse = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals(config.getString("Hexed Armor = "))){
					isCurse = true;
				}
			}
			if(isCurse == true) {
				if (randor.nextInt(5) == 1) {
					Monster m = (Monster) event.getDamager();
					Player p = (Player) event.getEntity();
					ItemStack[] armor = p.getInventory().getArmorContents();
					for (ItemStack i2 : armor) {
						if (i2 != null) {
							if (i2.getType() != null) {
								if (i2.getType() != Material.AIR) {
									p.getWorld().dropItemNaturally(m.getLocation(), i2);
								}
							}
						}
					}
					p.getInventory().setArmorContents(m.getEquipment().getArmorContents());
					m.getEquipment().clear();
					m.remove();
				}
				return;
			}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside 1 hit.");
			}
		}
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
			try {
			if(randor.nextInt(6)==1) {
				boolean isTNT = false;
				if((event.getEntity()).hasMetadata(config.getString("TnT Creeper = "))) {
					isTNT = true;
				}
				else if(event.getEntity().getCustomName() != null) {
					if (event.getEntity().getCustomName().equals(config.getString("TnT Creeper = "))){
						isTNT = true;
					}
				}
				if(isTNT == true) {
					event.getDamager().getWorld().createExplosion(event.getDamager().getLocation(), (float) 0.01);
					return;
				}
			}
			if(randor.nextInt(6)==1) {
				boolean isMine = false;
				if((event.getEntity()).hasMetadata(config.getString("Dead Miner = "))) {
					isMine = true;
				}
				else if(event.getEntity().getCustomName() != null) {
					if (event.getEntity().getCustomName().equals(config.getString("Dead Miner = "))){
						isMine = true;
					}
				}
				if(isMine == true) {
					// 0 == cobblestone 1 == dirt 2 == coal 3 == torch 
					int choice = randor.nextInt(4);
					if(choice == 0) {
						event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.COBBLESTONE, randor.nextInt(3+1)));
					}
					else if(choice == 1) {
						event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.DIRT, randor.nextInt(3+1)));
					}
					else if(choice == 2) {
						event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.COAL, randor.nextInt(2+1)));
					}
					else if(choice == 3) {
						event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.TORCH, randor.nextInt(3+1)));
					}
					return;
				}
			}
			boolean isWatch = false;
			if((event.getEntity()).hasMetadata(config.getString("Watcher = "))) {
				isWatch = true;
			}
			else if(event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().equals(config.getString("Watcher = "))){
					isWatch = true;
				}
			}
			if(isWatch == true) {
				event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, (float) 1.1);
				return;
			}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside better E-Looper.");
			}
		}
	}
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent event) {
		if(!worlds.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		World wor = event.getEntity().getWorld();
		if(!event.isCancelled()) {
			try {
		if(event.getEntity().getType()==EntityType.CREEPER) {
			boolean isTNT = false;
			if((event.getEntity()).hasMetadata(config.getString("TnT Creeper = "))) {
				isTNT = true;
			}
			else if(event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().equals(config.getString("TnT Creeper = "))){
					isTNT = true;
				}
			}
			if(isTNT == true) {
				Location l = event.getEntity().getLocation();
				Entity e1 = event.getEntity().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
				e1.setVelocity(new Vector(-1*(randor.nextInt(5+1)/10.0),randor.nextInt(5+1)/10.0,-1*(randor.nextInt(5+1)/10.0)));
				if(randor.nextBoolean()==true) {
					Entity e2 = event.getEntity().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
					e2.setVelocity(new Vector(randor.nextInt(5+1)/10.0,randor.nextInt(5+1)/10.0,randor.nextInt(5+1)/10.0));
				}
				return;
			}
			boolean isLava = false;
			if((event.getEntity()).hasMetadata(config.getString("Lava Creeper = "))) {
				isLava = true;
			}
			else if(event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().equals(config.getString("Lava Creeper = "))){
					isLava = true;
				}
			}
			if(isLava == true) {
				Location l = event.getEntity().getLocation();
				if (wor != null) {
					Random rand = new Random();
					Location loc = l;
					int radius = 4;
					int cx = loc.getBlockX();
					int cy = loc.getBlockY();
					int cz = loc.getBlockZ();
					for (int x = cx - radius; x <= cx + radius; x++) {
						for (int z = cz - radius; z <= cz + radius; z++) {
							for (int y = (cy - radius); y < (cy + radius); y++) {
								double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

								if (dist < radius * radius) {
										Location l2 = new Location(loc.getWorld(), x, y, z);
										if(l2.getBlock().getType()!=Material.BEDROCK) {
										if(l2.getBlock().getType()==Material.AIR) {
											if(randor.nextInt(3)==1) {
												l2.getBlock().setType(Material.FIRE);
											}
										}
										else if(l2.getBlock().getType()!=Material.AIR) {
											if(randor.nextInt(4)==1) {
												l2.getBlock().setType(Material.MAGMA);
											}
											else if(randor.nextInt(5)==1) {
												l2.getBlock().setType(Material.OBSIDIAN);
											}
											else if(randor.nextInt(6)==1) {
												l2.getBlock().setType(Material.STATIONARY_LAVA);
											}
										}
									}
								}

							}
						}
					}
				}
				return;
			}
		}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside explosion.");
			}
		}
	}
	
	public void removeHand(Entity e) {
		if(e instanceof LivingEntity) {
			EntityEquipment ee = ((LivingEntity) e).getEquipment();
			ee.setItemInMainHand(new ItemStack(Material.AIR));
			ee.setItemInOffHand(new ItemStack(Material.AIR));
		}
	}
	
	//
	
}

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
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Vine;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;

public class main extends JavaPlugin implements Listener, CommandExecutor{

	BukkitScheduler scheduler = null;
	FileConfiguration config = getConfig();
	public ConsoleCommandSender console = getServer().getConsoleSender();
	Random randor = new Random();
	public World wor = null;
	public boolean canSave = false;
	public List<Entity> effectEnts = new ArrayList<Entity>();
	boolean caveins = false;
	boolean hungerdark = false;
	boolean ambients = false;
	boolean cavetemp = false;
	boolean caveage = false;
	
	@Override
	public void onEnable() {
		createConfigFol();
		this.getServer().getPluginManager().registerEvents(this, this);
		if(!this.getServer().getOnlinePlayers().isEmpty()) {
			for(Player start : this.getServer().getOnlinePlayers()) {
				wor = start.getWorld();
				caveins = config.getBoolean("Enable Cave-Ins ");
				hungerdark = config.getBoolean("Enable Hungering Darkness ");
				ambients = config.getBoolean("Enable Ambient Sounds ");
				cavetemp = config.getBoolean("Enable Cave Temperature ");
				caveage = config.getBoolean("Enable Cave Aging ");
				break;
			}
		}
		scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if(hungerdark==true) {
				betterEffectLooper();
				}
			}
		}, 0L, /* 600 */((long) 3));
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (wor != null) {
					if (caveage == true) {
					for (Player p : wor.getPlayers()) {
						//doCaveStuff(p);
					}
					}
					if(ambients == true) {
						doCaveSounds();
					}
				}
			}
		}, 0L, /* 600 */7000L);
	}
	
	@Override
	public void onDisable() {
		wor=null;
	}
	
	public void createConfigFol() {
		config.addDefault("Enable Cave-Ins ", true);
		config.addDefault("Enable Hungering Darkness ", true);
		config.addDefault("Enable Ambient Sounds ", true);
		config.addDefault("Enable Cave Temperature ", true);
		config.addDefault("Enable Cave Aging ", true);
		config.addDefault("::::Higher equals lower chance!::::", "");
		config.addDefault("Cave Aging Chance ", 2);
		config.addDefault("Cave Aging Change Chance ", 39);
		config.addDefault("Cave Ambience Chance ", 4);
		config.addDefault("Cave Walk Temp Chance ", 1449);
		config.addDefault("Cave Break Block Temp Chance ", 1450);
		config.addDefault("Cave-In Chance ", 399);
		config.addDefault("Darkness Spawn Chance ", 0);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler
	public void setWorld(PlayerJoinEvent e) {
		if(wor==null) {
			wor = e.getPlayer().getWorld();
			caveins = config.getBoolean("Enable Cave-Ins ");
			hungerdark = config.getBoolean("Enable Hungering Darkness ");
			ambients = config.getBoolean("Enable Ambient Sounds ");
			cavetemp = config.getBoolean("Enable Cave Temperature ");
			caveage = config.getBoolean("Enable Cave Aging ");
		}
		else {
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("testc")) {
			if (sender instanceof Player) {
				return true;
			}
		}
	return true;
	}

	//The Darkness
	
	@EventHandler
	public void onTarget(EntityTargetEvent event) {
		try {
		if(hungerdark==true) {
		if(event.getEntity() instanceof Monster) {
		Monster e = (Monster) event.getEntity();
			if(e.getCustomName()!=null) {
				if(e.getCustomName().equals("The Darkness")||e.hasMetadata("The Darkness")) {
					if(event.getTarget().getLocation().getBlock().getLightLevel()>0) {
						event.setTarget(null);
					}
					else {
						effectEnts.add(event.getEntity());
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

			if(e != null) {
				if(!e.isDead()) {
					if(e instanceof Monster) {
						if(((Monster) e).getTarget()!=null) {
							String name = e2.getCustomName();
							if(name!=null) {
								if(name.equals("The Darkness")||e.hasMetadata("The Darkness")) {
									if((e.getLocation().getBlock().getLightLevel()>0)||(e.getFireTicks()>0)) {
										e.remove();
									}
									else if((((Monster) e).getTarget().getLocation().getBlock().getLightLevel()==0)){
										e.getWorld().playSound(e.getLocation(), Sound.ENTITY_CAT_PURR, (float) .5, 0);
									}
								}
							}
						}
					}
		}}}}}
	
	@EventHandler
	public void onMobName(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof Player)) {
			ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
			if(i.getType()==Material.NAME_TAG) {
				if(i.hasItemMeta()) {
					if(i.getItemMeta().hasDisplayName()) {
						String s = i.getItemMeta().getDisplayName();
						if(s.equals("The Darkness")) {
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
		if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
			//p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			p.getInventory().getItemInMainHand().setAmount(0);
		} else {
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
		}
	}
	
	@EventHandler
	public void deleteLightLevel(CreatureSpawnEvent event) {
		if(hungerdark==true) {
		Entity e = event.getEntity();
		if(e.getLocation().subtract(0, 1, 0).getBlock()==null||randor.nextInt(config.getInt("Darkness Spawn Chance ")+1)!=0) {
			return;
		}
		if (e != null && !e.isDead()) {
		if ((e instanceof Creeper || e instanceof Skeleton || e instanceof Zombie || e instanceof Spider) && (!(e instanceof PigZombie || e instanceof ZombieVillager || e instanceof Husk))) {
			if ((e instanceof Monster && event.getSpawnReason() == SpawnReason.NATURAL && (e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.STONE || e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.DIRT))) {
				if (e.getLocation().getY() < 50) {
					doDarkness(e);
					}
			  }
		}
		}
		}
	}
	
	@EventHandler
    public void onDeath(EntityDeathEvent e) {
		if(hungerdark==true) {
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
	}

	public void doDarkness(Entity entitye) {
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
			e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 200, false, false));
			e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 200, false, false));
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 3, false, false));
			e.setSilent(true);
			e.setCanPickupItems(false);
			e.setCustomNameVisible(false);
			e.setCollidable(false);
		}
		return;
	}

	@EventHandler
    public void onDamage(EntityDamageEvent e) {
		if(hungerdark==true) {
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
	}
	
	@EventHandler
    public void onDamageP(EntityDamageByEntityEvent e) {
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
	
	//
	
	//Ambient Sounds
	
	public void doCaveSounds() {
		if (wor != null) {
			for (Player p : wor.getPlayers()) {
				if (randor.nextInt(config.getInt("Cave Ambience Chance ")+1) == 0) {
					int choice = randor.nextInt(6);
					if (p.getLocation().getY() < 47) {
						if (p.getLocation().getBlock().getLightFromSky() <= 1) {
							if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.STONE
									|| p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.DIRT) {
								if (choice == 0) {
									p.playSound(p.getLocation(), Sound.MUSIC_DRAGON, 100, 0);
								} else if (choice == 1) {
									p.playSound(p.getLocation(), Sound.MUSIC_NETHER, 100, (float) .5);
								} else if (choice == 2) {
									if (randor.nextInt(5) == 1) {
										p.playSound(p.getLocation(), Sound.RECORD_11, 100, (float) .5);
									}
								} else if (choice == 3) {
									p.playSound(p.getLocation(), Sound.RECORD_13, 100, (float) .5);
								} else if (choice == 4) {
									p.playSound(p.getLocation(), Sound.RECORD_MELLOHI, (float) 100, (float) .3);
								} else if (choice == 5) {
									if (randor.nextInt(6) == 1) {
										p.playSound(p.getLocation(), Sound.ENCHANT_THORNS_HIT, (float) .04, (float) .2);
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
	
	//
	
	//Cave Aging
	
	public void doCaveStuff(Player p) {
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
		if (randor.nextInt(config.getInt("Cave Aging Chance ")+1) == 0) {
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
								if (rand.nextInt(config.getInt("Cave Aging Change Chance ")+1) == 0) {
									Location l = new Location(loc.getWorld(), x, y, z);
									doCaveBlocks(l.getBlock(), p);
								}
							}

						}
					}
				}
			}
		}
	}

	public void doCaveBlocks(org.bukkit.block.Block b, Player p) {
		if (b.getLocation().getY() > 50 || b.getLightFromSky() > 0) {
			return;
		}
		if (b.getType() == Material.STONE && randor.nextInt(2) == 1) {
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
		if ((b.getType() == Material.STONE || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(8) == 1) {
			doVines2(b.getLocation());
		}
		if ((b.getType() == Material.STONE || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(9) == 1) {
			if (b.getRelative(BlockFace.UP) != null) {
				if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
					b.getRelative(BlockFace.UP).setType(Material.BROWN_MUSHROOM);
					b.getRelative(BlockFace.UP).setType(Material.RED_MUSHROOM);
				}
			}
		}
		if ((b.getType() == Material.STONE || b.getType() == Material.COBBLESTONE || b.getType() == Material.GRAVEL)
				&& randor.nextInt(6) == 1) {
			if (b.getRelative(BlockFace.UP) != null) {
				if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
					b.getRelative(BlockFace.UP).setType(Material.STONE_BUTTON);
					b.getRelative(BlockFace.UP).setData((byte) 5);
				}
			}
		}
	}

	public void doVines2(Location l) {
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
	Player p = event.getPlayer();
	if(randor.nextInt(config.getInt("Cave Walk Temp Chance ")+1)==0) {
		if(p.getLocation().getY()<30&&(p.getGameMode()!=GameMode.CREATIVE)) {
		if(p.getInventory().contains(Material.POTION)||p.getInventory().contains(Material.SPLASH_POTION)||p.getInventory().contains(Material.SNOW)||p.getInventory().contains(Material.SNOW_BALL)||p.getInventory().contains(Material.SNOW_BLOCK)||p.getInventory().contains(Material.WATER_BUCKET)||p.getInventory().contains(Material.ICE)||p.getInventory().contains(Material.FROSTED_ICE)||p.getInventory().contains(Material.PACKED_ICE)) {
		}
		else if(p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
		}
		else {
			int msg = randor.nextInt(4);
			if(msg==0) {
			p.sendMessage(ChatColor.RED + "Wow, it's hot down here.  I better bring something to cool myself off next time.");
			}
			else if(msg==1) {
			p.sendMessage(ChatColor.RED + "It is realllyyy hot down here.");
			}
			else if(msg==2) {
			p.sendMessage(ChatColor.RED + "I'm going to need something to cool myself off next time.");
			}
			else if(msg==3) {
			p.sendMessage(ChatColor.RED + "Is it hot in here, or is it just me?");
			}
			else if(msg==4) {
			p.sendMessage(ChatColor.RED + "I really need some ice.");
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,120 ,1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,55 ,1));
		}
		}
	}
	}
	}
	
	//
	
	//Cave Ins
	
	@EventHandler
	public void onBreakB(BlockBreakEvent dr) {
		Player p = dr.getPlayer();
		if(caveins==true) {
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
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST_FAR, 100, 1);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST_FAR, 100, 1);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
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
		if(cavetemp==true) {
		if(randor.nextInt(config.getInt("Cave Break Block Temp Chance ")+1)==0) {
			if(p.getLocation().getY()<30&&(p.getGameMode()!=GameMode.CREATIVE)) {
			if(p.getInventory().contains(Material.POTION)||p.getInventory().contains(Material.SPLASH_POTION)||p.getInventory().contains(Material.SNOW)||p.getInventory().contains(Material.SNOW_BALL)||p.getInventory().contains(Material.SNOW_BLOCK)||p.getInventory().contains(Material.WATER_BUCKET)||p.getInventory().contains(Material.ICE)||p.getInventory().contains(Material.FROSTED_ICE)||p.getInventory().contains(Material.PACKED_ICE)) {
			}
			else if(p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
			}
			else {
				int msg = randor.nextInt(4);
				if(msg==0) {
				p.sendMessage(ChatColor.RED + "Wow, it's hot down here.  I better bring something to cool myself off next time.");
				}
				else if(msg==1) {
				p.sendMessage(ChatColor.RED + "It is realllyyy hot down here.");
				}
				else if(msg==2) {
				p.sendMessage(ChatColor.RED + "I'm going to need something to cool myself off next time.");
				}
				else if(msg==3) {
				p.sendMessage(ChatColor.RED + "Is it hot in here, or is it just me?");
				}
				else if(msg==4) {
				p.sendMessage(ChatColor.RED + "I really need some ice.");
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,120 ,1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,55 ,1));
			}
			}
		}
		}
	}
	
	//
	
}

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
	public World wor = null;
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
	
	@Override
	public void onEnable() {
		createConfigFol();
		this.getServer().getPluginManager().registerEvents(this, this);
		if(!this.getServer().getOnlinePlayers().isEmpty()) {
			for(Player start : this.getServer().getOnlinePlayers()) {
				wor = start.getWorld();
				/*List<BlockPopulator> bp = new ArrayList<BlockPopulator>(start.getWorld().getPopulators());
				for(BlockPopulator bps : bp) {
					if(bps instanceof CaveGenerator) {
						start.getWorld().getPopulators().remove(bps);
					}
				}*/
				start.getWorld().getPopulators().clear();
				console.sendMessage("Ignore this: " + start.getWorld().getPopulators());
				wor.getPopulators().add(new CaveGenerator());
				caveins = config.getBoolean("Enable Cave-Ins ");
				hungerdark = config.getBoolean("Enable Hungering Darkness ");
				ambients = config.getBoolean("Enable Ambient Sounds ");
				cavetemp = config.getBoolean("Enable Cave Temperature ");
				caveage = config.getBoolean("Enable Cave Aging ");
				caveents = config.getBoolean("Enable Cave Monsters ");
				cavechance = config.getInt("Cave Structure Chance ");
				cavestruct = config.getBoolean("Enable Cave Structures ");
				break;
			}
		}
		mobNames.add("The Darkness");
		mobNames.add("Watcher");
		mobNames.add("TnT Infused Creeper");
		mobNames.add("Dead Miner");
		mobNames.add("Lava Creeper");
		mobNames.add("Magma Monster");
		mobNames.add("Smoke Demon");
		mobNames.add("Crying Bat");
		mobNames.add("Alpha Spider");
		mobNames.add("Hexed Armor");
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
		wor=null;
	}
	
	public void createConfigFol() {
		config.addDefault("Enable Cave-Ins ", true);
		config.addDefault("Enable Hungering Darkness ", true);
		config.addDefault("Enable Ambient Sounds ", true);
		config.addDefault("Enable Cave Temperature ", true);
		config.addDefault("Enable Cave Aging ", true);
		config.addDefault("Enable Cave Monsters ", true);
		config.addDefault("Enable Cave Structures ", true);
		config.addDefault("::::Higher equals lower chance!::::", "");
		config.addDefault("Cave Aging Chance ", 2);
		config.addDefault("Cave Aging Change Chance ", 39);
		config.addDefault("Cave Ambience Chance ", 4);
		config.addDefault("Cave Walk Temp Chance ", 1449);
		config.addDefault("Cave Break Block Temp Chance ", 1450);
		config.addDefault("Cave-In Chance ", 399);
		config.addDefault("Darkness Spawn Chance ", 1);
		config.addDefault("Cave Structure Chance ", 1);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler
	public void setWorld(PlayerJoinEvent e) {
		if(wor==null) {
			wor = e.getPlayer().getWorld();
			/*List<BlockPopulator> bp = new ArrayList<BlockPopulator>(e.getPlayer().getWorld().getPopulators());
			for(BlockPopulator bps : bp) {
				if(bps instanceof CaveGenerator) {
					e.getPlayer().getWorld().getPopulators().remove(bps);
				}
			}*/
			wor.getPopulators().clear();
			wor.getPopulators().add(new CaveGenerator());
			console.sendMessage("" + e.getPlayer().getWorld().getPopulators());
			caveins = config.getBoolean("Enable Cave-Ins ");
			hungerdark = config.getBoolean("Enable Hungering Darkness ");
			ambients = config.getBoolean("Enable Ambient Sounds ");
			cavetemp = config.getBoolean("Enable Cave Temperature ");
			caveage = config.getBoolean("Enable Cave Aging ");
			caveents = config.getBoolean("Enable Cave Monsters ");
			cavechance = config.getInt("Cave Structure Chance ");
			cavestruct = config.getBoolean("Enable Cave Structures ");
		}
		else {
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("testc")) {
			if (sender instanceof Player) {
					if(args!=null&&args.length==1) {
						Location loc = ((Player) sender).getLocation();
						int[][][] rock1 = { { {0, 1, 0}, {0, 0, 0}, {0, 0, 0} },
											{ {0, 1, 1}, {0, 1, 0}, {0, 1, 0} },
											{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } };
						if(args[0].toString().toLowerCase().equals("1")) {
							int randDirection = randor.nextInt(4);
							if(randDirection==0) {
								for(int y = 0; y < rock1[0].length; y++) {
									for(int x = -1; x < rock1.length-1; x++) {
										for(int z = -1; z < rock1[0][0].length-1; z++) {
											if(rock1[x+1][y][z+1]==1) {
											Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z);
											loc2.getBlock().setType(getRandStone(1));
											}
										}	
									}
								}
							}
							else if(randDirection==1) {
								for(int y = 0; y < rock1[0].length; y++) {
									for(int x = -1; x < rock1.length-1; x++) {
										for(int z = -1; z < rock1[0][0].length-1; z++) {
											if(rock1[x+1][y][z+1]==1) {
											Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()+z);
											loc2.getBlock().setType(getRandStone(1));
											}
										}	
									}
								}
							}
							else if(randDirection==2) {
								for(int y = 0; y < rock1[0].length; y++) {
									for(int x = -1; x < rock1.length-1; x++) {
										for(int z = -1; z < rock1[0][0].length-1; z++) {
											if(rock1[x+1][y][z+1]==1) {
											Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()-z);
											loc2.getBlock().setType(getRandStone(1));
											}
										}	
									}
								}
							}
							else if(randDirection==3) {
								for(int y = 0; y < rock1[0].length; y++) {
									for(int x = -1; x < rock1.length-1; x++) {
										for(int z = -1; z < rock1[0][0].length-1; z++) {
											if(rock1[x+1][y][z+1]==1) {
											Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()-z);
											loc2.getBlock().setType(getRandStone(1));
											}
										}	
									}
								}
							}
						}
					//for (Player p : wor.getPlayers()) {
					//	doCaveStuff(p);
					//}
					return true;
				}
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
		if(caveents==true) {
		if(event.getEntity() instanceof Monster) {
			Monster e = (Monster) event.getEntity();
				if(e.getCustomName()!=null) {
					if(e.getCustomName().equals("Watcher")||e.hasMetadata("Watcher")) {
							effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals("Magma Monster")||e.hasMetadata("Magma Monster")) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals("Dead Miner")||e.hasMetadata("Dead Miner")) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals("Lava Creeper")||e.hasMetadata("Lava Creeper")) {
						effectEnts.add(event.getEntity());
					}
					else if(e.getCustomName().equals("Smoke Demon")||e.hasMetadata("Smoke Demon")) {
						effectEnts.add(event.getEntity());
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
								else if(name.equals("Watcher")||e.hasMetadata("Watcher")) {
									LivingEntity e3 = ((Monster) e).getTarget();
									if(getLookingAt2(e3, (LivingEntity) e)==false) {
										Player p = (Player) e3;
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
									}
								else if(name.equals("Dead Miner")||e.hasMetadata("Dead Miner")) {
									if(e.getLocation().getBlock().getLightLevel()==0) {
										e.getLocation().getBlock().setType(Material.TORCH);
									}
								}
								else if(name.equals("Magma Monster")||e.hasMetadata("Magma Monster")) {
									if(randor.nextInt(14)==1) {
										e.getLocation().getBlock().setType(Material.FIRE);
									}
									if(randor.nextInt(28)==1) {
										e.getLocation().subtract(0, 1, 0).getBlock().setType(Material.MAGMA);
									}
								}
								else if(name.equals("Lava Creeper")||e.hasMetadata("Lava Creeper")) {
									if(randor.nextInt(24)==1) {
										e.getWorld().spawnParticle(Particle.LAVA, e.getLocation().add(0, 1, 0), 1);
									}
								}
								else if(name.equals("Smoke Demon")||e.hasMetadata("Smoke Demon")) {
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
								}
							}
						}
					else if(e instanceof Bat) {
						String name = e2.getCustomName();
						if(name!=null) {
							if(name.equals("Crying Bat")||e.hasMetadata("Crying Bat")) {
								if(randor.nextInt(30)==1) {
									e.getWorld().playSound(e.getLocation(), Sound.ENTITY_WOLF_WHINE, 1, (float) (1.4+(randor.nextInt(5+1)/10.0)));
									if(randor.nextInt(5)==1) {
										((Bat) e).damage(999999);
									}
								}
							}
						}
					}
					}
		}}}}
	
	public static Location getBlockInFrontOfPlayer(LivingEntity entsa) {
		Vector inverseDirectionVec = entsa.getLocation().getDirection().normalize().multiply(1);
		return entsa.getLocation().add(inverseDirectionVec);
	}
	
	@EventHandler
	public void onMobName(PlayerInteractEntityEvent event) {
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
		if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
			//p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			p.getInventory().getItemInMainHand().setAmount(0);
		} else {
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
		}
	}
	
	@EventHandler
	public void deleteLightLevel(CreatureSpawnEvent event) {
		if(hungerdark==true||caveents==true) {
		Entity e = event.getEntity();
		if(e.getLocation().subtract(0, 1, 0).getBlock()==null) {
			return;
		}
		else if(randor.nextInt(config.getInt("Darkness Spawn Chance ")+1)!=0||hungerdark==false) {
			if(caveents==true) {
			if (e != null && !e.isDead()) {
				if ((e instanceof Creeper || e instanceof Skeleton || e instanceof Zombie || e instanceof Spider) && (!(e instanceof PigZombie || e instanceof ZombieVillager || e instanceof Husk))) {
					if ((e instanceof Monster && event.getSpawnReason() == SpawnReason.NATURAL && (e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.STONE || e.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.DIRT))) {
						if (e.getLocation().getY() < 50) {
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
				if (e.getLocation().getY() < 50) {
					doDarkness(e);
					}
			  }
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
			e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2, false, false));
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
	
	//Cave Mobs
	
	public String mobTypes() {
		int choice = randor.nextInt(9);
		if(choice == 0) {
			return "Watcher";
		}
		else if(choice == 1) {
			return "TnT Infused Creeper";
		}
		else if(choice == 2) {
			return "Dead Miner";
		}
		else if(choice == 3) {
			return "Lava Creeper";
		}
		else if(choice == 4) {
			return "Magma Monster";
		}
		else if(choice == 5) {
			return "Smoke Demon";
		}
		else if(choice == 6) {
			if(randor.nextInt(20)==1) {
			return "Crying Bat";
			}
			else {
				return "";
			}
		}
		else if(choice == 7) {
			return "Alpha Spider";
		}
		else if(choice == 8) {
			return "Hexed Armor";
		}
		return "";
	}
	
	public void doMobSpawns(Entity entitye) {
			LivingEntity e = (LivingEntity) entitye;
			String name = mobTypes();
			if (e != null && !e.isDead()) {
				if(name.equals("Watcher")) {
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
				}
				else if(name.equals("TnT Infused Creeper")) {
					if(e.getType()!=EntityType.CREEPER) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
					e.remove();
					e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals("Lava Creeper")) {
					if(e.getType()!=EntityType.CREEPER) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
					e.remove();
					e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals("Dead Miner")) {
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
				else if(name.equals("Magma Monster")) {
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
				}
				else if(name.equals("Smoke Demon")) {
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
				}
				else if(name.equals("Crying Bat")) {
					if(e.getType()!=EntityType.BAT) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.BAT);
						e.remove();
						e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals("Alpha Spider")) {
					if(!(e.getType()==EntityType.SPIDER||e.getType()==EntityType.CAVE_SPIDER)) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.SPIDER);
						e.remove();
						e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
				}
				else if(name.equals("Hexed Armor")) {
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
			return;
	}
	
	public void setMinerHands(Entity e) {
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
	
	@EventHandler
	public void entityHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
			boolean isMagma = false;
			if((event.getDamager()).hasMetadata("Magma Monster")) {
				isMagma = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals("Magma Monster")){
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
			if((event.getDamager()).hasMetadata("Alpha Spider")) {
				isAlpha = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals("Alpha Spider")){
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
			if((event.getDamager()).hasMetadata("Hexed Armor")) {
				isCurse = true;
			}
			else if(event.getDamager().getCustomName() != null) {
				if (event.getDamager().getCustomName().equals("Hexed Armor")){
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
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
			if(randor.nextInt(6)==1) {
				boolean isTNT = false;
				if((event.getEntity()).hasMetadata("TnT Infused Creeper")) {
					isTNT = true;
				}
				else if(event.getEntity().getCustomName() != null) {
					if (event.getEntity().getCustomName().equals("TnT Infused Creeper")){
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
				if((event.getEntity()).hasMetadata("Dead Miner")) {
					isMine = true;
				}
				else if(event.getEntity().getCustomName() != null) {
					if (event.getEntity().getCustomName().equals("Dead Miner")){
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
		}
	}
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent event) {
		if(!event.isCancelled()) {
		if(event.getEntity().getType()==EntityType.CREEPER) {
			boolean isTNT = false;
			if((event.getEntity()).hasMetadata("TnT Infused Creeper")) {
				isTNT = true;
			}
			else if(event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().equals("TnT Infused Creeper")){
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
			if((event.getEntity()).hasMetadata("Lava Creeper")) {
				isLava = true;
			}
			else if(event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().equals("Lava Creeper")){
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
				return;
			}
		}
		}
	}
	
	//
	
}

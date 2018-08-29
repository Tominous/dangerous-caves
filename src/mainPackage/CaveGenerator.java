package mainPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

//import net.minecraft.server.v1_12_R1.BlockPosition;
//import net.minecraft.server.v1_12_R1.IBlockData;

public class CaveGenerator extends BlockPopulator {
	
	Random randor = new Random();
	/*int[][][] rock5 = { { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
						{   {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
						{   {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } };*/
	int[][][] rock1 = { { {0, 1, 0}, {0, 1, 0}, {0, 0, 0} },
						{ {0, 1, 1}, {0, 1, 0}, {0, 1, 0} },
						{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] rock2 = { { {0, 1, 0}, {0, 1, 0}, {0, 0, 0} },
						{ {1, 1, 1}, {1, 1, 1}, {0, 1, 0} },
						{ {0, 1, 1}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] rock3 = { { {0, 1, 0}, {0, 0, 0}, {0, 0, 0} },
						{ {0, 1, 1}, {0, 1, 0}, {0, 0, 0} },
						{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] rock4 = { { {0, 1, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
						{ {1, 1, 1}, {0, 1, 1}, {0, 1, 1}, {0, 1, 0} },
						{ {0, 1, 0}, {0, 1, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] rock5 = { { {0, 1, 0}, {0, 0, 0}, {0, 0, 0} },
						{ {1, 1, 1}, {0, 1, 1}, {0, 0, 1} },
						{ {1, 1, 0}, {0, 1, 0}, {0, 0, 0} } };
	int[][][] rock6 = { { {1, 1, 1}, {0, 1, 1}, {0, 1, 1}, {0, 1, 1}, {0, 0, 1} },
						{ {1, 1, 1}, {1, 1, 1}, {1, 1, 0}, {0, 0, 0}, {0, 0, 0} },
						{ {1, 1, 0}, {0, 1, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] rock7 = { { {1, 1, 0}, {1, 0, 0}, {0, 0, 0} },
						{ {1, 1, 1}, {1, 1, 1}, {1, 1, 0} },
						{ {1, 1, 1}, {0, 1, 0}, {0, 0, 0} } };
	int[][][] rock8 = { { {0, 1, 0}, {0, 0, 0}, {0, 0, 0} },
					{     {1, 1, 1}, {0, 1, 0}, {0, 0, 0} },
					{     {0, 1, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] chests1 = { { {0, 6, 0}, {0, 6, 0}, {0, 0, 0} },
						{   {6, 2, 6}, {6, 0, 0}, {0, 0, 0} },
						{   {0, 5, 0}, {0, 0, 0}, {0, 0, 0} } };
	//1 == wood decide 2 == chest 3 == torch 4 == random utility 5 == door 6 = wood stay 7 == Random Ore 8 == Snow Block 9 == Spawner 10 = silverfish stone
	int[][][] chests2 = { { {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {0, 1, 1, 1, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0} },
						{   {1, 1, 1, 1, 1}, {1, 4, 4, 4, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0} },
						{   {1, 1, 1, 1, 1}, {1, 4, 3, 4, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0} },
						{   {1, 1, 1, 1, 1}, {1, 4, 0, 4, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0} },
						{   {1, 1, 1, 1, 1}, {0, 1, 0, 1, 0}, {0, 1, 0, 1, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0} } };
	int[][][] chests3 = { { {1, 1, 1}, {0, 0, 0}, {0, 0, 0} },
						{   {1, 1, 1}, {0, 2, 0}, {0, 0, 0} },
						{   {1, 1, 1}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] sfishs1 = { { {0, 7, 0}, {0, 0, 0}, {0, 0, 0} },
						{   {7, 9, 7}, {0, 7, 0}, {0, 0, 0} },
						{   {0, 7, 0}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] sfishs2 = { { {0, 10, 0}, {0, 10, 0}, {0, 0, 0} },
						{   {10, 9, 10}, {0, 8, 0}, {0, 0, 0} },
						{   {0, 10, 8}, {0, 0, 0}, {0, 0, 0} } };
	int[][][] sfishs3 = { { {0, 10, 0}, {0, 10, 0}, {0, 0, 0} },
						{   {10, 9, 10}, {0, 8, 10}, {0, 10, 0} },
						{   {0, 10, 8}, {0, 0, 0}, {0, 0, 0} } };
	
	@Override
	public void populate(World wor, Random rand, Chunk chnk) {
		
		if(rand.nextInt(main.cavechance+1)==0&&main.cavestruct==true) {
		//-1 + | 1 == random pillar or shape / boulder 2 == random skeleton skull 3 == random room with stuff or random chest 4 == monsters spawner surrounded 5 == random mineshaft / tunnel 6 == spiders nest small 7 == traps
		//int typeC = rand.nextInt(8);
			int typeC = rand.nextInt(4);
		//sendCaveMessage(typeC);
		int cX = chnk.getX() * 16;
		int cZ = chnk.getZ() * 16;
		int cXOff = cX + rand.nextInt(10);
		int cZOff = cZ + rand.nextInt(10);
    		if(typeC==0) {
    			randomShape(cXOff, cZOff, wor);
    		}
    		else if(typeC==1) {
    			randomBoulder(cXOff, cZOff, wor);
    		}
    		else if(typeC==2) {
    			randomTrap(cXOff, cZOff, wor);
    		}
    		else if(typeC==3) {
    			randomStructure(cXOff, cZOff, wor);
    		}
		}
	}
	
	public void sendCaveMessage(int typeC) {
		if(typeC==0) {
    		Bukkit.getServer().getConsoleSender().sendMessage("!Generated Spider Den!");
    		}
    		else if(typeC==1){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Mushroom Cave!");
    		}
    		else if(typeC==2){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Andesite Cave!");
    		}
    		else if(typeC==3){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Granite Cave!");
    		}
    		else if(typeC==4){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Diorite Cave!");
    		}
    		else if(typeC==5){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Lava Cave!");
    		}
    		else if(typeC==6){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Sandy Cave!");
    		}
    		else if(typeC==7){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Snow Cave!");
    		}
    		else if(typeC==8){
    			Bukkit.getServer().getConsoleSender().sendMessage("!Generated Flooded Cave!");
    		}
	}
	
	public int getClosestAir(int cXOff, int cZOff, World w) {
		Location loc = new Location(w, cXOff, 1, cZOff);
		while(loc.getY()<55) {
			loc.add(0, 1, 0);
			if(loc.getBlock().getType()==Material.AIR) {
				Location loc2 = new Location(w, loc.getX(), loc.getY() - 1, loc.getZ());
				if(loc2.getBlock().getType()==Material.STONE || loc2.getBlock().getType()==Material.DIRT || loc2.getBlock().getType()==Material.GRAVEL) {
					Location loc3 = new Location(w, loc.getX(), loc.getY() + 1, loc.getZ());
					if(loc3.getBlock().getType()==Material.AIR) {
					break;
					}
				}
			}
		}
		return (int) loc.getY();
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
	
	public void randomShape(int cXOff, int cZOff, World w) {
		int yVal = getClosestAir(cXOff, cZOff, w);
		if(yVal == 55) {
			return;
		}
		else {
		Location loc = new Location(w, cXOff, yVal, cZOff);
		int type = randor.nextInt(8);
		if(type==0) {
			loc.getBlock().setType(Material.STONE);
			loc.getBlock().setData((byte) 6);
			loc.add(0, 1, 0).getBlock().setType(getRandStone(1));
			loc.add(0, 1, 0).getBlock().setType(Material.STEP);
			loc.getBlock().setData((byte) 5);
		}
		else if(type==1) {
			loc.getBlock().setType(Material.STONE);
			loc.getBlock().setData((byte) 6);
			loc.add(0, 1, 0).getBlock().setType(Material.STEP);
			loc.getBlock().setData((byte) 5);
		}
		else if(type==2) {
			loc.getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc2 = new Location(loc.getWorld(), loc.getX()+randor.nextInt(2), loc.getY(), loc.getZ()+1);
			loc2.getBlock().setType(Material.STEP);
			loc2.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.STEP);
			loc.getBlock().setData((byte) 5);
		}
		else if(type==3) {
			loc.getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc2 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+randor.nextInt(2));
			loc2.getBlock().setType(Material.STEP);
			loc2.getBlock().setData((byte) 5);
		}
		else if(type==4) {
			loc.getBlock().setType(getRandStone(1));
			Location loc2 = new Location(loc.getWorld(), loc.getX()+randor.nextInt(2), loc.getY(), loc.getZ()+1);
			loc2.getBlock().setType(Material.STEP);
			loc2.getBlock().setData((byte) 5);
			loc.add(0,1,0).getBlock().setType(Material.STONE);
			loc.getBlock().setData((byte) 6);
			Location loc22 = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+randor.nextInt(2));
			loc22.getBlock().setType(Material.STEP);
			loc22.getBlock().setData((byte) 5);
		}
		else if(type==5) {
			loc.getBlock().setType(Material.SMOOTH_BRICK);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
			loc2.getBlock().setType(Material.STEP);
			loc2.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc22 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
			loc22.getBlock().setType(Material.STEP);
			loc22.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.STEP);
			loc.getBlock().setData((byte) 5);
		}
		else if(type==6) {
			loc.getBlock().setType(Material.SMOOTH_BRICK);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc2 = new Location(loc.getWorld(), loc.getX()+randor.nextInt(2), loc.getY(), loc.getZ()+1);
			loc2.getBlock().setType(Material.STEP);
			loc2.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc22 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
			loc22.getBlock().setType(Material.STEP);
			loc22.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc222 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
			loc222.getBlock().setType(Material.STEP);
			loc222.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.SMOOTH_BRICK);
			if(randor.nextBoolean()==true) {
				loc.getBlock().setData((byte) 2);
			}
			Location loc2222 = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
			loc2222.getBlock().setType(Material.STEP);
			loc2222.getBlock().setData((byte) 5);
			loc.add(0, 1, 0).getBlock().setType(Material.STEP);
			loc.getBlock().setData((byte) 5);
			Location loc22222 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
			loc22222.getBlock().setType(Material.STEP);
			loc22222.getBlock().setData((byte) 5);
		}
		else if(type==7) {
			loc.getBlock().setType(Material.COBBLESTONE);
			loc.add(0, 1, 0).getBlock().setType(Material.COBBLE_WALL);
		}
	  }
	}
	
	public void randomBoulder(int cXOff, int cZOff, World w) {
		int yVal = getClosestAir(cXOff, cZOff, w);
		if(yVal == 55) {
			return;
		}
		else {
		Location loc = new Location(w, cXOff, yVal, cZOff);
		int type = randor.nextInt(8);
			if(type==0) {
				generateBoulder(rock1, loc);
			}
			else if(type==1) {
				generateBoulder(rock2, loc);
			}
			else if(type==2) {
				generateBoulder(rock3, loc);
			}
			else if(type==3) {
				generateBoulder(rock4, loc);
			}
			else if(type==4) {
				generateBoulder(rock5, loc);
			}
			else if(type==5) {
				generateBoulder(rock6, loc);
			}
			else if(type==6) {
				generateBoulder(rock7, loc);
			}
			else if(type==7) {
				generateBoulder(rock8, loc);
			}
		}
	}
	
	public void randomTrap(int cXOff, int cZOff, World w) {
		int yVal = getClosestAir(cXOff, cZOff, w);
		if(yVal == 55) {
			return;
		}
		else {
		Location loc = new Location(w, cXOff, yVal, cZOff);
		int type = randor.nextInt(9);
			if(type==0) {
				//for(int i = yVal ; i > 4 ; i--) {
				while(loc.getY()>4) {
					loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);
				}
			}
			else if(type==1) {
				while(loc.getY()>4) {
					loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);
				}
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
			}
			else if(type==2) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 1, 0).getBlock().setType(Material.GRAVEL);
				loc.subtract(0, 1, 0).getBlock().setType(Material.TNT);
				while(loc.getY()>4) {
					loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);
				}
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
				loc.add(0, 1, 0).getBlock().setType(Material.LAVA);
			}
			else if(type==3) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 1, 0).getBlock().setType(Material.GRAVEL);
				loc.subtract(0, 1, 0).getBlock().setType(Material.TNT);
				while(loc.getY()>4) {
					loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);
				}
				loc.add(0, 1, 0).getBlock().setType(Material.WEB);
				loc.add(0, 1, 0).getBlock().setType(Material.WEB);
				loc.add(0, 1, 0).getBlock().setType(Material.WEB);
			}
			else if(type==4) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 1, 0).getBlock().setType(Material.GRAVEL);
				loc.subtract(0, 1, 0).getBlock().setType(Material.TNT);
				while(loc.getY()>4) {
					loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);
				}
			}
			else if(type==5) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 1, 0).getBlock().setType(Material.GRAVEL);
				loc.subtract(0, 1, 0).getBlock().setType(Material.TNT);
				if(randor.nextBoolean()==true) {
					loc.add(0, 0, 1).getBlock().setType(Material.TNT);
					if(randor.nextBoolean()==true) {
						loc.add(1, 0, 0).getBlock().setType(Material.TNT);
					}
				}
			}
			else if(type==6) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 2, 0).getBlock().setType(Material.TNT);
				if(randor.nextBoolean()==true) {
					loc.add(0, 0, 1).getBlock().setType(Material.TNT);
					if(randor.nextBoolean()==true) {
						loc.add(1, 0, 0).getBlock().setType(Material.TNT);
					}
				}
			}
			else if(type==7) {
				loc.getBlock().setType(Material.TRAPPED_CHEST);
				ChestRandomizer.fillChest(loc.getBlock());
				loc.subtract(0, 2, 0).getBlock().setType(Material.TNT);
				if(randor.nextBoolean()==true) {
					loc.add(0, 0, 1).getBlock().setType(Material.TNT);
					if(randor.nextBoolean()==true) {
						loc.add(1, 0, 0).getBlock().setType(Material.TNT);
					}
				}
			}
			else if(type==8) {
				loc.getBlock().setType(Material.STONE_PLATE);
				loc.subtract(0, 1, 0).getBlock().setType(Material.DISPENSER);
				Dispenser dis = (Dispenser) loc.getBlock().getState();
		        Inventory inv = dis.getInventory();
		        inv.addItem(new ItemStack(Material.ARROW, randor.nextInt(3)+1));
			}
		}
	}
	
	public void randomStructure(int cXOff, int cZOff, World w) {
		int yVal = getClosestAir(cXOff, cZOff, w);
		if(yVal == 55) {
			return;
		}
		else {
		Location loc = new Location(w, cXOff, yVal, cZOff);
		int type = randor.nextInt(11);
			if(type==0) {
				loc.getBlock().setType(Material.CHEST);
				ChestRandomizer.fillChest(loc.getBlock());
			}
			else if(type==1) {
				loc.subtract(0, 1, 0);
				generateStructure(chests3, loc);
			}
			else if(type==2) {
				loc.subtract(0, 1, 0);
				generateStructure(chests2, loc);
			}
			else if(type==3) {
				generateStructure(chests1, loc);
			}
			else if(type==4) {
				loc.getBlock().setType(Material.SKULL);
			}
			else if(type==5) {
				int direction = randor.nextInt(4);
				int length = randor.nextInt(27);
				int typeT = randor.nextInt(2);
				for(int i = 0; i < length; i++) {
					if(direction == 0) {
						loc.add(1, 0, 0);
					}
					else if(direction == 1) {
						loc.add(0, 0, 1);
					}
					else if(direction == 2) {
						loc.subtract(1, 0, 0);
					}
					else if(direction == 3) {
						loc.subtract(0, 0, 1);
					}
					Location tempL = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
					if(typeT == 1) {
						tempL.subtract(0, 1, 0).getBlock().setType(Material.MONSTER_EGGS);
						tempL.add(0, 1, 0);
						if(i%3==0) {
							tempL.getBlock().setType(Material.REDSTONE_TORCH_ON);
						}
						else {
							tempL.getBlock().setType(Material.AIR);
						}
						tempL.add(0, 1, 0).getBlock().setType(Material.AIR);
					}
					else {
						if(i%3==0) {
							tempL.getBlock().setType(Material.TORCH);
						}
						else {
							tempL.getBlock().setType(Material.AIR);
						}
						tempL.add(0, 1, 0).getBlock().setType(Material.AIR);
					}
				}
			}
			else if(type==6) {
				Location tempL1 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
				Location tempL2 = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
				Location tempL3 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
				Location tempL4 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
				tempL1.getBlock().setType(Material.STEP);
				tempL1.getBlock().setData((byte) 3);
				tempL2.getBlock().setType(Material.STEP);
				tempL2.getBlock().setData((byte) 3);
				tempL3.getBlock().setType(Material.STEP);
				tempL3.getBlock().setData((byte) 3);
				tempL4.getBlock().setType(Material.STEP);
				tempL4.getBlock().setData((byte) 3);
				Location tempL5 = new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ());
				tempL5.getBlock().setType(Material.NETHERRACK);
				loc.getBlock().setType(Material.FIRE);
			}
			else if(type==7) {
				Location tempL1 = new Location(loc.getWorld(), loc.getX()+1, loc.getY()-1, loc.getZ());
				Location tempL2 = new Location(loc.getWorld(), loc.getX()-1, loc.getY()-1, loc.getZ());
				Location tempL3 = new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()+1);
				Location tempL4 = new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()-1);
				loc.getBlock().setType(Material.WORKBENCH);
				if(tempL1.getBlock().getType()!=Material.AIR&&randor.nextInt(3)==1) {
					tempL1.add(0, 1, 0).getBlock().setType(Material.REDSTONE_WIRE);
				}
				if(tempL2.getBlock().getType()!=Material.AIR&&randor.nextInt(3)==1) {
					tempL2.add(0, 1, 0).getBlock().setType(Material.REDSTONE_WIRE);
				}
				if(tempL3.getBlock().getType()!=Material.AIR&&randor.nextInt(3)==1) {
					tempL3.add(0, 1, 0).getBlock().setType(Material.REDSTONE_WIRE);
				}
				if(tempL4.getBlock().getType()!=Material.AIR&&randor.nextInt(3)==1) {
					tempL4.add(0, 1, 0).getBlock().setType(Material.REDSTONE_WIRE);
				}
			}
			else if(type==8) {
				generateStructure(sfishs1, loc);
			}
			else if(type==9) {
				generateStructure(sfishs2, loc);
			}
			else if(type==10) {
				generateStructure(sfishs3, loc);
			}
		}
	}
	
	public void decideBlock(int type, Block b) {
		//1 == wood decide 2 == chest 3 == torch 4 == random utility 5 == door 6 = wood stay 7 == Random Ore 8 == Snow Block 9 == Spawner 10 = silverfish stone
		if(type==1) {
			if(randor.nextInt(3)!=0) {
				b.setType(Material.WOOD);
			}
		}
		else if(type == 2) {
			b.setType(Material.CHEST);
			ChestRandomizer.fillChest(b);
		}
		else if(type == 3) {
			b.setType(Material.TORCH);
		}
		else if(type == 4) {
			if(randor.nextInt(3)==1) {
			int choice = randor.nextInt(5);
			if(choice == 0) {
				b.setType(Material.FURNACE);
			}
			else if(choice == 1) {
				b.setType(Material.CHEST);
				ChestRandomizer.fillChest(b);
			}
			else if(choice == 2) {
				b.setType(Material.WORKBENCH);
			}
			else if(choice == 3) {
				b.setType(Material.CAULDRON);
			}
			else if(choice == 4) {
				b.setType(Material.ANVIL);
			}
			}
		}
		else if(type == 5) {
			b.setType(Material.WOOD);
		}
		else if(type == 6) {
			b.setType(Material.WOOD);
		}
		else if(type == 7) {
			int typer = randor.nextInt(3);
			if(typer == 0) {
				b.setType(Material.STONE);
			}
			else if(typer == 1) {
				b.setType(Material.COAL_ORE);
			}
			else if(typer == 2) {
				b.setType(Material.IRON_ORE);
			}
		}
		else if(type == 8) {
			b.setType(Material.SNOW_BLOCK);
		}
		else if(type == 9) {
			b.setType(Material.MOB_SPAWNER);
			BlockState blockState = b.getState();
			CreatureSpawner spawner = ((CreatureSpawner) blockState);
			spawner.setSpawnedType(EntityType.SILVERFISH);
			blockState.update();
		}
		else if(type == 10) {
			b.setType(Material.MONSTER_EGGS);
		}
	}
	
	public void generateStructure(int[][][] rock, Location loc) {
		int randDirection = randor.nextInt(4);
		if(randDirection==0) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z);
						decideBlock(rock[x+1][y][z+1], loc2.getBlock());
					}	
				}
			}
		}
		else if(randDirection==1) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()+z);
						decideBlock(rock[x+1][y][z+1], loc2.getBlock());
					}	
				}
			}
		}
		else if(randDirection==2) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()-z);
						decideBlock(rock[x+1][y][z+1], loc2.getBlock());
					}	
				}
			}
		}
		else if(randDirection==3) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()-z);
						decideBlock(rock[x+1][y][z+1], loc2.getBlock());
					}	
				}
			}
		}
	}
	
	public void generateBoulder(int[][][] rock, Location loc) {
		int randDirection = randor.nextInt(4);
		if(randDirection==0) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						if(rock[x+1][y][z+1]==1) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z);
						loc2.getBlock().setType(getRandStone(1));
						}
					}	
				}
			}
		}
		else if(randDirection==1) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						if(rock[x+1][y][z+1]==1) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()+z);
						loc2.getBlock().setType(getRandStone(1));
						}
					}	
				}
			}
		}
		else if(randDirection==2) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						if(rock[x+1][y][z+1]==1) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()-z);
						loc2.getBlock().setType(getRandStone(1));
						}
					}	
				}
			}
		}
		else if(randDirection==3) {
			for(int y = 0; y < rock[0].length; y++) {
				for(int x = -1; x < rock.length-1; x++) {
					for(int z = -1; z < rock[0][0].length-1; z++) {
						if(rock[x+1][y][z+1]==1) {
						Location loc2 = new Location(loc.getWorld(), loc.getX()-x, loc.getY()+y, loc.getZ()-z);
						loc2.getBlock().setType(getRandStone(1));
						}
					}	
				}
			}
		}
	}
	
}

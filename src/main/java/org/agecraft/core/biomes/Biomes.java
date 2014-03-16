package org.agecraft.core.biomes;

import org.agecraft.ACComponent;

import net.minecraft.world.biome.BiomeGenBase;

public class Biomes extends ACComponent {

	public static BiomeGenBase ocean; // 0
	public static BiomeGenBase deepOcean;
	public static BiomeGenBase river;
	public static BiomeGenBase island;
	public static BiomeGenBase archipelago;
	public static BiomeGenBase beach;
	public static BiomeGenBase stoneBeach;
	public static BiomeGenBase snowBeach;

	public static BiomeGenBase frozenOcean; // 8
	public static BiomeGenBase frozenRiver;
	public static BiomeGenBase alpine;
	public static BiomeGenBase glacier;
	public static BiomeGenBase icePlains;
	public static BiomeGenBase taiga;
	public static BiomeGenBase taigaHills;
	public static BiomeGenBase snowForest;
	public static BiomeGenBase tundra;

	public static BiomeGenBase mountains; // 17
	public static BiomeGenBase extremeHills;
	public static BiomeGenBase extremeHillsEdge;
	public static BiomeGenBase extremeHillsPlus;
	public static BiomeGenBase stoneHills;
	public static BiomeGenBase volcano;

	public static BiomeGenBase swamp; // 23
	public static BiomeGenBase greenSwamplands;
	public static BiomeGenBase marsh;
	public static BiomeGenBase bayou;
	public static BiomeGenBase bog;
	public static BiomeGenBase mangrove;

	public static BiomeGenBase plains; // 29
	public static BiomeGenBase highland;
	public static BiomeGenBase chaparral;
	public static BiomeGenBase heathland;
	public static BiomeGenBase pasture;
	public static BiomeGenBase prairie;

	public static BiomeGenBase forest1; // 35
	public static BiomeGenBase forestHills1;
	public static BiomeGenBase forest2;
	public static BiomeGenBase forestHills2;
	public static BiomeGenBase forest3;
	public static BiomeGenBase forestHills3;
	public static BiomeGenBase meadow;
	public static BiomeGenBase roofedForest;

	public static BiomeGenBase jungle; // 43
	public static BiomeGenBase jungleHills;
	public static BiomeGenBase jungleEdge;
	public static BiomeGenBase rainforest;
	public static BiomeGenBase rainforestHills;

	public static BiomeGenBase wasteland; // 48
	public static BiomeGenBase deadForest;
	public static BiomeGenBase deadlands;
	public static BiomeGenBase scrubland;

	public static BiomeGenBase desert; // 52
	public static BiomeGenBase desertHills;
	public static BiomeGenBase redBadlands;
	public static BiomeGenBase droughtPlains;
	public static BiomeGenBase savanna;
	public static BiomeGenBase savannaPlateau;
	public static BiomeGenBase dunes;
	public static BiomeGenBase lushDesert;
	public static BiomeGenBase mesa;
	public static BiomeGenBase mesaPlateau;
	public static BiomeGenBase mesaPlateauF;
	public static BiomeGenBase steppe;
	
	public static BiomeGenBase timelessIsland; //64
	public static BiomeGenBase timelessIslandShore;

	public Biomes() {
		super("Biomes", false);
	}

	@Override
	public void preInit() {
		// remove vanilla biomes
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			BiomeGenBase.getBiomeGenArray()[i] = null;
		}

		// init biomes
		ocean = new BiomeOcean(0).setBiomeName("Ocean");
		deepOcean = new BiomeDeepOcean(1).setBiomeName("Deep Ocean");
		river = new BiomeRiver(2).setBiomeName("River");

		alpine = new BiomeAlpine(11).setMutation().setBiomeName("Alpine");
		glacier = new BiomeGlacier(12).setMutation().setBiomeName("Glacier");
		taiga = new BiomeTaiga(13).setMutation().setBiomeName("Taiga");

		plains = new BiomePlains(29).setMutation().setBiomeName("Plains");
		
		jungle = new BiomeJungle(43).setMutation().setBiomeName("Jungle");

		desert = new BiomeDesert(52).setMutation().setBiomeName("Desert");
	}
}

package elcon.mods.agecraft.core;

import elcon.mods.agecraft.ACLog;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeRegistry {

	public static class Biome {
		
		public int id;
		public String name;
		
		public BiomeGenBase biome;
		
		public float temperature;
		public float humidity;
		
		public Biome(int id, String name, BiomeGenBase biome, float temperature, float humidity) {
			this.id = id;
			this.name = name;
			
			this.biome = biome;
			
			this.temperature = temperature;
			this.humidity = humidity;
		}
	}
	
	public static Biome[] biomes = new Biome[BiomeGenBase.biomeList.length];
	
	public static Biome getBiome(BiomeGenBase biomeGenBase) {
		for(int i = 0; i < biomes.length; i++) {
			if(biomes[i] != null && biomes[i].biome.equals(biomeGenBase)) {
				return biomes[i];
			}
		}
		return null;
	}
	
	public static float getTemperature(BiomeGenBase biomeGenBase) {
		Biome biome = getBiome(biomeGenBase);
		if(biome != null) {
			return biome.temperature;
		}
		return 0;
	}
	
	public static float getHumidity(BiomeGenBase biomeGenBase) {
		Biome biome = getBiome(biomeGenBase);
		if(biome != null) {
			return biome.humidity;
		}
		return 0;
	}
	
	public static boolean canSurviveTemperature(BiomeGenBase biomeGenBase, float preferedTemperature, int resistance) {
		float temperature = getTemperature(biomeGenBase);
		if(preferedTemperature == temperature) {
			return true;
		} else if(resistance == 0) {
			return false;
		} else {
			float temp = Math.max(Math.min((preferedTemperature + 1.0F) + resistance * 0.5F, 0.0F), 2.0F);
			return ((preferedTemperature >= 1.0F && temperature >= 1.0F) || (preferedTemperature <= 1.0F && temperature <= 1.0F)) && temperature <= temp;
		}
	}
	
	public static boolean canSurviveHumidity(BiomeGenBase biomeGenBase, float preferedHumidity, int resistance) {
		float humidity = getHumidity(biomeGenBase);
		if(preferedHumidity == humidity) {
			return true;
		} else if(resistance == 0) {
			return false;
		} else {
			float hum = Math.max(Math.min((preferedHumidity + 1.0F) + resistance * 0.5F, 0.0F), 2.0F);
			return ((preferedHumidity >= 1.0F && humidity >= 1.0F) || (preferedHumidity <= 1.0F && humidity <= 1.0F)) && humidity <= hum;
		}
	}
	
	public static void registerBiome(Biome biome) {
		if(biomes[biome.id] != null) {
			ACLog.warning("[BiomeRegistry] Overriding existing biome (" + biomes[biome.id] + ": " + biomes[biome.id].name.toUpperCase() + ") with new biome (" + biome.id + ": " + biome.name.toUpperCase() + ")");
		}
		biomes[biome.id]= biome;
	}
	
	public static void registerBiomes() {
		for(int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			if(BiomeGenBase.biomeList[i] != null) {
				BiomeGenBase biome = BiomeGenBase.biomeList[i];
				registerBiome(new Biome(biome.biomeID, biome.biomeName, biome, biome.temperature - 1.0F, biome.rainfall - 1.0F));
			}
		}
	}
}

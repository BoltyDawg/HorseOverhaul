package com.github.boltydawg.horseoverhaul.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import com.github.boltydawg.horseoverhaul.StatHorse;

public class FoalListener implements Listener{
	
	@EventHandler
	public void onBreed(EntityBreedEvent event) {
		
		if(event.getEntityType().equals(EntityType.HORSE) && event.getBreeder() instanceof Player) {
			
			if(!(event.getFather() instanceof Horse && event.getMother() instanceof Horse)) return;
			
			Player player = (Player)event.getBreeder();
			
			if(event.getFather().getScoreboardTags().contains("ho.isNeutered") || event.getMother().getScoreboardTags().contains("ho.isNeutered")) {
				
				player.sendMessage("One of these horses is neutered! Why must you be so cruel, with your false promises of parenthood?");
				Horse father = (Horse)event.getFather();
				Horse mother = (Horse)event.getMother();
				father.setLoveModeTicks(0);
				mother.setLoveModeTicks(0);
				event.setCancelled(true);
				return;
				
			}
			
			StatHorse foal;
			if(event.getBredWith().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {
				
				foal = new StatHorse(event.getEntity(), (byte)2);
				event.getEntity().getScoreboardTags().add("ho.isNeutered");
				
			}

			else if(event.getBredWith().getType().equals(Material.GOLDEN_APPLE))
				foal = new StatHorse(event.getEntity(), (byte)1);
			else
				foal = new StatHorse(event.getEntity());
			
			
			foal.calculateBirth((Horse)event.getMother(),(Horse)event.getFather());
			foal.roach.setTamed(true);
			
//			StatHorse father = new StatHorse(event.getFather());
//			StatHorse mother = new StatHorse(event.getMother());
//			player.sendMessage(foal.printStats("Foal"));
//			player.sendMessage(father.printStats("Father"));
//			player.sendMessage(mother.printStats("Mother"));
		}
	}
}
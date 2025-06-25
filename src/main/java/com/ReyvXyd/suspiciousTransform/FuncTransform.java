package com.ReyvXyd.suspiciousTransform;


import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import java.util.*;

public class FuncTransform implements Listener {
    private final HashMap<UUID, ScheduledTask> hashInfo = new HashMap<>();

    @EventHandler

    public void playerInventoryChek(PlayerInteractEvent event) {

        ItemStack item = event.getItem();

        if (item == null || !item.getType().equals(Material.BRUSH)) return;

        if (event.getAction() !=(Action.RIGHT_CLICK_BLOCK)) return;

        Block block = event.getClickedBlock();

        if (block == null || !block.getType().equals(Material.SAND) && !block.getType().equals(Material.GRAVEL)) return;

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (hashInfo.containsKey(playerId)) {
            hashInfo.get(playerId).cancel();
            hashInfo.remove(playerId);
        }

        Location location = block.getLocation();

        RegionScheduler scheduler = Bukkit.getRegionScheduler();

        ScheduledTask task = scheduler.runDelayed (SuspiciousTransform.getInstance(), location, t -> {
            boolean useBrush = player.getActiveItem().getType().equals(Material.BRUSH);
            Block targetBlock = player.getTargetBlockExact(5);
            if (targetBlock == null) return;
            boolean blockChek = blockLocate(block, targetBlock);
            if (!blockChek){
                hashInfo.remove(playerId);
                return;
            }
            if (useBrush) {
                switch (targetBlock.getType()) {
                    case Material.SAND:
                        targetBlock.setType(Material.SUSPICIOUS_SAND);
                        itemAddSus(targetBlock, player);
                        break;
                    case Material.GRAVEL:
                        targetBlock.setType(Material.SUSPICIOUS_GRAVEL);
                        itemAddSus(targetBlock, player);
                        break;
                }
            } else {
                hashInfo.remove(playerId);
            }
            hashInfo.remove(playerId);
        }, 100);
        hashInfo.put(playerId, task);
    }

    public void offPlayer (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (hashInfo.containsKey(playerId)){
            hashInfo.get(playerId).cancel();
            hashInfo.remove(playerId);
        }
    }

    private boolean blockLocate (Block block, Block targetBlock) {
        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();
        int tBlockX = targetBlock.getX();
        int tBlockY = targetBlock.getY();
        int tBlockZ = targetBlock.getZ();
        boolean b;
        b = tBlockX == blockX && tBlockY == blockY && tBlockZ == blockZ;
        return b;
    }

    private void itemAddSus (Block block, Player player) {
        ItemStack ofHand = player.getInventory().getItemInOffHand();
        BrushableBlock putBlock = (BrushableBlock) block.getState();
        ItemStack itemPut = ofHand.asOne();
        putBlock.setItem(itemPut);
        putBlock.setLootTable(null);
        putBlock.update();
        ofHand.subtract(1);
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        mainHand.setDurability((short) (mainHand.getDurability()+1));
        if (mainHand.getDurability()==64)
            player.getInventory().setItemInMainHand(ItemStack.of(Material.AIR));
        Location soundLoc = block.getLocation();
        Sound soundSand = Sound.BLOCK_SAND_PLACE;
        Sound soundGravel = Sound.BLOCK_GRAVEL_PLACE;
        if (block.getType().equals(Material.SUSPICIOUS_SAND)) {
            block.getWorld().playSound(soundLoc, soundSand, 1f, 1f);
            block.getWorld().spawnParticle(Particle.EGG_CRACK, soundLoc.add(0.5,1.05,0.5), 10, 0.2, 0, 0.2 );
        } else if (block.getType().equals(Material.SUSPICIOUS_GRAVEL)) {
            block.getWorld().playSound(soundLoc, soundGravel, 1f, 1f);
            block.getWorld().spawnParticle(Particle.EGG_CRACK, soundLoc.add(0.5,1.05,0.5), 10, 0.2, 0, 0.2);
        }
    }
}



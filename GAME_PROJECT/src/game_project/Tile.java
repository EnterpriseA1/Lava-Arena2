/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_project;

import java.util.*;

/**
 *
 * @author THAN
 */
public class Tile {
    private boolean isLava;

    public Tile(boolean isLava) {
        this.isLava = isLava;
    }

    public boolean isLava() {
        return isLava;
    }
}

class Board {
    private Tile[][] tiles;
    private int size;
    private List<Buff> buffs;
    private Random random;
    private Character player;
    private Character enemy;

    public Board(int size, Character player, Character enemy) {
        this.size = size;
        this.player = player;
        this.enemy = enemy;
        tiles = new Tile[size][size];
        buffs = new ArrayList<>();
        random = new Random();

        // First store the player and enemy positions
        int playerX = player.getX();
        int playerY = player.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        // Initialize board tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == playerX && j == playerY) || (i == enemyX && j == enemyY)) {
                    tiles[i][j] = new Tile(false);
                } else {
                    tiles[i][j] = new Tile(Math.random() < 0.15);
                }
            }
        }

        System.out.println("Spawning initial buffs...");
        for (int i = 0; i < 3; i++) {
            spawnRandomBuff();
        }
        System.out.println("Initial buffs spawned: " + buffs.size());
    }

    public void spawnRandomBuff() {
        if (random.nextDouble() < 1) {
            int x, y;
            int attempts = 0;
            final int MAX_ATTEMPTS = 100;
            
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
                attempts++;
                if (attempts > MAX_ATTEMPTS) {
                    System.out.println("Failed to find valid buff position after " + MAX_ATTEMPTS + " attempts");
                    return;
                }
            } while (!isValidBuffPosition(x, y));

            // สร้าง buff ตามประเภทที่สุ่มได้
            Buff newBuff;
            if (random.nextDouble() < 0.5) {
                newBuff = new AttackBuff(x, y);
            } else {
                newBuff = new HealBuff(x, y);
            }
            buffs.add(newBuff);
        }
    }

    private boolean isValidBuffPosition(int x, int y) {
        // Check if position is within bounds
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;
        }
        
        // Check if position is on lava
        if (tiles[x][y].isLava()) {
            return false;
        }
        
        // Check if position already has a buff
        if (hasBuff(x, y)) {
            return false;
        }
        
        // Check if position is occupied by player or enemy
        // Use local variables to prevent NPE
        if (player != null && enemy != null) {
            if ((x == player.getX() && y == player.getY()) || 
                (x == enemy.getX() && y == enemy.getY())) {
                return false;
            }
        }
        
        return true;
    }

    // Rest of the methods remain the same...
    boolean hasBuff(int x, int y) {
        return buffs.stream().anyMatch(buff -> buff.getX() == x && buff.getY() == y);
    }

    public Buff getBuff(int x, int y) {
        return buffs.stream()
                .filter(buff -> buff.getX() == x && buff.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public void removeBuff(Buff buff) {
        buffs.remove(buff);
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void removeBuff(int x, int y) {
        buffs.removeIf(buff -> buff.getX() == x && buff.getY() == y);
    }
}
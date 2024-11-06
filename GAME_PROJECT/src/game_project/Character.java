package game_project;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Character {
    private int x, y;
    private int hp = 100;
    private String direction;
    private Board board;
    private int atkdamage = 10;
    private int temporaryAttackBonus = 0;
    private HealthBar healthBar;

    public Character(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.direction = "UP";
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void checkAndCollectBuff() {
        try {
            if (board != null && board.hasBuff(x, y)) {
                Buff buff = board.getBuff(x, y);
                if (buff != null) {
                    applyBuff(buff);
                    board.removeBuff(x, y);
                    // Only spawn new buff if there's space on the board
                    if (board.getBuffs().size() < 3) {
                        board.spawnRandomBuff();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking/collecting buff: " + e.getMessage());
        }
    }

    private void applyBuff(Buff buff) {
        try {
            if (buff.isAttackBuff()) {
                temporaryAttackBonus = buff.getValue();
                System.out
                        .println("Attack buff collected! Next attack will deal " + buff.getValue() + " extra damage!");
            } else if (buff.isHealBuff()) {
                heal(buff.getValue());
                System.out.println("Healed for " + buff.getValue() + " HP!");
            }
        } catch (Exception e) {
            System.out.println("Error applying buff: " + e.getMessage());
        }
    }

    private void heal(int amount) {
        int oldHp = hp;
        hp = Math.min(100, hp + amount);
        int healedAmount = hp - oldHp;
        if (healthBar != null) {
            healthBar.setHealth(hp); // Update the health bar
            System.out.println("Updated health bar to: " + hp);
        }
        System.out.println("Healed for " + healedAmount + " HP! Current HP: " + hp);
    }

    public boolean attack(Character target, JPanel boardPanel) {
        try {
            if (!canAttack(target)) {
                System.out.println("Target is not in range!");
                return false;
            }

            int totalDamage = this.atkdamage + temporaryAttackBonus;
            System.out.println("Attacking with " + totalDamage + " damage! (Base: " + atkdamage + ", Bonus: "
                    + temporaryAttackBonus + ")");

            target.takeDamage(totalDamage);
            temporaryAttackBonus = 0; // Reset bonus after attack

            // Knockback calculation
            int newX = target.getX();
            int newY = target.getY();

            switch (this.direction) {
                case "UP":
                    newX -= 1;
                    break;
                case "DOWN":
                    newX += 1;
                    break;
                case "LEFT":
                    newY -= 1;
                    break;
                case "RIGHT":
                    newY += 1;
                    break;
            }

            if (canPushTarget(newX, newY)) {
                if (board.getTile(newX, newY).isLava()) {
                    System.out.println("Target fell into lava! Target takes massive damage!");
                    target.setHp(0);
                } else {
                    target.setX(newX);
                    target.setY(newY);
                    System.out.println("Target pushed to position: (" + newX + ", " + newY + ")");
                }
            } else {
                System.out.println("Target hit wall - no knockback!");
            }

            if (!target.isAlive()) {
                System.out.println("Target has been defeated!");
            }

            if (boardPanel != null) {
                boardPanel.repaint();
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error during attack: " + e.getMessage());
            return false;
        }
    }

    public boolean canAttack(Character target) {
        if (target == null)
            return false;

        int dx = Math.abs(target.getX() - this.x);
        int dy = Math.abs(target.getY() - this.y);

        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private boolean canPushTarget(int newX, int newY) {
        if (board == null)
            return false;
        return newX >= 0 && newX < board.getSize() && newY >= 0 && newY < board.getSize();
    }

    // Getters and Setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public String getDirection() {
        return direction;
    }

    public int getCurrentAttackDamage() {
        return atkdamage + temporaryAttackBonus;
    }

    public int getBaseAttackDamage() {
        return atkdamage;
    }

    public int getTemporaryAttackBonus() {
        return temporaryAttackBonus;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(100, hp)); // Ensure HP stays between 0 and 100
        if (healthBar != null) {
            healthBar.setHealth(this.hp);
        }
    }

    public void setDirection(String direction) {
        if (direction != null && (direction.equals("UP") || direction.equals("DOWN") || direction.equals("LEFT")
                || direction.equals("RIGHT"))) {
            this.direction = direction;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        if (damage < 0)
            return; // Prevent negative damage

        int oldHp = hp;
        hp = Math.max(0, hp - damage);
        if (healthBar != null) {
            healthBar.setHealth(hp);
        }
        System.out.println("Character took " + (oldHp - hp) + " damage! HP: " + hp);

        // Check for game over after damage
        if (hp <= 0 && board != null) {
            // We need to cast the board to access GameUI
            // This assumes the board is created by GameUI
            GameUI gameUI = (GameUI) SwingUtilities.getWindowAncestor(healthBar);
            if (gameUI != null) {
                gameUI.checkGameOver();
            }
        }
    }

    public void resetTemporaryAttackBonus() {
        temporaryAttackBonus = 0;
    }
}
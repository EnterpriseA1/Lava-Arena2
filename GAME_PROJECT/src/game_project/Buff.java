package game_project;

public abstract class Buff {
    protected int x, y;
    protected int value;
    
    public Buff(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = calculateValue();
    }
    
    // Abstract methods that subclasses must implement
    protected abstract int calculateValue();
    public abstract String getSymbol();
    public abstract boolean isAttackBuff();
    public abstract boolean isHealBuff();
    
    // Common getters that all buffs share
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getValue() {
        return value;
    }
}

class AttackBuff extends Buff {
    public static final String SYMBOL = "ATK";
    
    public AttackBuff(int x, int y) {
        super(x, y);
    }
    
    @Override
    protected int calculateValue() {
        return 5; // +5 damage boost
    }
    
    @Override
    public String getSymbol() {
        return SYMBOL;
    }
    
    @Override
    public boolean isAttackBuff() {
        return true;
    }
    
    @Override
    public boolean isHealBuff() {
        return false;
    }
}
class HealBuff extends Buff {
    public static final String SYMBOL = "HP";
    
    public HealBuff(int x, int y) {
        super(x, y);
    }
    
    @Override
    protected int calculateValue() {
        return 15; // +15 HP heal
    }
    
    @Override
    public String getSymbol() {
        return SYMBOL;
    }
    
    @Override
    public boolean isAttackBuff() {
        return false;
    }
    
    @Override
    public boolean isHealBuff() {
        return true;
    }
}
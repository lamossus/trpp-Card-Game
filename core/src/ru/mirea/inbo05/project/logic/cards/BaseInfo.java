package ru.mirea.inbo05.project.logic.cards;

public class BaseInfo extends CardInfo{
    public boolean isTaunt;
    public int health;

    public BaseInfo(String textureName) {
        super(textureName);
    }

    @Override
    public Card CreateInstance() {
        Base base = new Base(this);
        instance = base;
        return base;
    }
}

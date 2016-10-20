package ru.alexkulikov.firstfame.objects;

public class BoxData {

    private ObjectType type;

    public BoxData(ObjectType type) {
        this.type = type;
    }

    public ObjectType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BoxData{" +
                "type=" + type +
                '}';
    }
}

package Entities;

public class PriorityEntity {

    private Entity entity;
    private int priority;

    public PriorityEntity(Entity entity, int priority) {
        this.entity = entity;
        this.priority = priority;
    }

    public Entity getEntity() { return entity; }
    public int getPriority()  { return priority; }
}

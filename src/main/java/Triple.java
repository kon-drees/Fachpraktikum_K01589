public class Triple {

    private Entity subject;
    private Predicate predicate;
    private Entity object;

    public int getArgStrenght() {
        return argStrenght;
    }

    public void setArgStrenght(int argStrenght) {
        this.argStrenght = argStrenght;
    }

    private int argStrenght = 1;
     // Metadata


    public Triple(Entity subject, Predicate predicate, Entity object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Entity getSubject() {
        return subject;
    }

    public void setSubject(Entity subject) {
        this.subject = subject;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public Entity getObject() {
        return object;
    }

    public void setObject(Entity object) {
        this.object = object;
    }
}

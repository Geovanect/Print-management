package Print.management.model;

public class Document implements Comparable<Document> {
    private String Name;
    private boolean isPriority;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public Document(String n, boolean isP){
        this.Name = n;
        this.isPriority = isP;
    }
    @Override
    public int compareTo(Document other){
        if(this.isPriority && !other.isPriority){
            return -1;
        } else if (!this.isPriority && other.isPriority) {
            return 1;
        }
        else {
            return 0;
        }
    }
}


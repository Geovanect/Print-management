package Print.management.model;

public class Document implements Comparable<Document> {
    private String name;
    private boolean priority;

    public Document() {} // Necessário para @RequestBody no Spring

    public Document(String name, boolean priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Document other) {
        // Documentos prioritários vêm antes
        return Boolean.compare(!this.priority, !other.priority);
    }

    @Override
    public String toString() {
        return name + " (priority: " + priority + ")";
    }
}


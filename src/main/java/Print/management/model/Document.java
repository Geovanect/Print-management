package Print.management.model;

public class Document implements Comparable<Document> {
    private String name;
    private boolean priority;
    private long arrivalOrder;

    public Document() {} // Necessário para @RequestBody no Spring

    public Document(String name, boolean priority, long arrivalOrder) {
        this.name = name;
        this.priority = priority;
        this.arrivalOrder = arrivalOrder;
    }

    public long getArrivalOrder(){
        return arrivalOrder;
    }
    public void setArrivalOrder(long arrivalOrder){
        this.arrivalOrder = arrivalOrder;
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
        if(this.priority && other.priority){
            return Long.compare(this.arrivalOrder, other.arrivalOrder);
        }
        if (this.priority && !other.priority) return -1;
        if (!this.priority && other.priority) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return name + " (priority: " + priority + ", order: " + arrivalOrder + ")";
    }
}


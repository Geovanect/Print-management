package Print.management.service;

import Print.management.model.Document;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrintQueueService {

    private final PriorityQueue<Document> priorityQueue = new PriorityQueue<>();
    private final Queue<Document> normalQueue = new LinkedList<>();
    private long arrivalCounter = 0;

    public void addDocument(Document doc) {
        if (doc.isPriority()) {
            doc.setArrivalOrder(arrivalCounter++);
            priorityQueue.offer(doc);
        } else {
            normalQueue.offer(doc);
        }
    }

    public Document printDocument() {
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.poll();
        } else {
            return normalQueue.poll();
        }
    }

    public List<Document> listDocument() {
        List<Document> allDocs = new ArrayList<>();
        allDocs.addAll(priorityQueue.stream()
                .sorted()
                .toList());
        allDocs.addAll(normalQueue);
        return allDocs;
    }
}
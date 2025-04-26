package Print.management.service;
import org.springframework.stereotype.Service;
import Print.management.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class PrintQueueService {

        PriorityQueue<Document> printQueue = new PriorityQueue<>();

        public void addDocument(Document doc){
            printQueue.add(doc);
        }

        public Document printDocument(){
            return printQueue.poll();
        }

        public void listQueue(){
            if(printQueue.isEmpty()){
                System.out.println("A fila de impressão está vazia");
                return;
            }
            System.out.println("Documentos na fila de impressão:");
            for(Document doc : printQueue){
                System.out.println("-" + doc.getName() + (doc.isPriority() ? " (Prioritario) " : ""));
            }
        }

        public boolean isqueueEmpty(){
            return printQueue.isEmpty();
        }

        public List<Document> listDocument(){
            return new ArrayList<>(printQueue);
        }
    }


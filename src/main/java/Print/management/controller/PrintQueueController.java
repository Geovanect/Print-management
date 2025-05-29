package Print.management.controller;

import Print.management.model.Document;
import Print.management.service.PrintQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class PrintQueueController {

    private final PrintQueueService printQueueService;

    @Autowired
    public PrintQueueController(PrintQueueService printQueueService){
        this.printQueueService = printQueueService;
    }

    @PostMapping
    public ResponseEntity<Void> addDocument(@RequestBody Document document) {
        printQueueService.addDocument(document);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listdocs")
    public ResponseEntity<List<Document>> listDocument() {
        return ResponseEntity.ok(printQueueService.listDocument());
    }

    @PostMapping("/print")
    public ResponseEntity<Document> printDocument() {
        Document printedDoc = printQueueService.printDocument();
        if (printedDoc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(printedDoc);
    }
}

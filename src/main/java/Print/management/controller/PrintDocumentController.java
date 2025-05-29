package Print.management.controller;

import Print.management.model.PrintDocument;
import Print.management.repository.PrintDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class PrintDocumentController {

    @Autowired
    private PrintDocumentRepository repository;

    @GetMapping
    public ResponseEntity<List<PrintDocument>> getAllDocuments() {
        return ResponseEntity.ok(repository.findAllOrderByPriorityAndId());
    }

    @PostMapping
    public ResponseEntity<PrintDocument> createDocument(@RequestBody PrintDocument document) {
        document.setStatus("PENDING");
        return ResponseEntity.ok(repository.save(document));
    }

    @PostMapping("/print")
    public ResponseEntity<PrintDocument> printDocument() {
        List<PrintDocument> pendingDocs = repository.findByStatusOrderByPriorityAndId("PENDING");
        if (pendingDocs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PrintDocument docToPrint = pendingDocs.get(0);
        docToPrint.setStatus("COMPLETED");
        return ResponseEntity.ok(repository.save(docToPrint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        return repository.findById(id)
                .map(document -> {
                    repository.delete(document);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 
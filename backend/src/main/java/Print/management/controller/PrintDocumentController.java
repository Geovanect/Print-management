package Print.management.controller;

import Print.management.model.PrintDocument;
import Print.management.repository.PrintDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class PrintDocumentController {

    @Autowired
    private PrintDocumentRepository repository;

    @GetMapping
    public ResponseEntity<Page<PrintDocument>> getAllDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("priority").descending().and(Sort.by("id").ascending()));
        Page<PrintDocument> documents = repository.findAll(pageable);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/status/{status}")
    public List<PrintDocument> getDocumentsByStatus(@PathVariable String status) {
        return repository.findByStatusOrderByPriorityAndId(status);
    }

    @PostMapping
    public PrintDocument createDocument(@RequestBody PrintDocument document) {
        return repository.save(document);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrintDocument> updateDocument(@PathVariable Long id, @RequestBody PrintDocument document) {
        return repository.findById(id)
                .map(existingDoc -> {
                    existingDoc.setName(document.getName());
                    existingDoc.setPriority(document.isPriority());
                    existingDoc.setStatus(document.getStatus());
                    return ResponseEntity.ok(repository.save(existingDoc));
                })
                .orElse(ResponseEntity.notFound().build());
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

    @PostMapping("/print")
    public ResponseEntity<PrintDocument> printNextDocument() {
        List<PrintDocument> pendingDocuments = repository.findByStatusOrderByPriorityAndId("PENDING");
        
        if (pendingDocuments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PrintDocument nextDocument = pendingDocuments.get(0);
        nextDocument.setStatus("IMPRIMINDO");
        PrintDocument printingDoc = repository.save(nextDocument);

        // Atualiza para COMPLETED após 5 segundos em uma thread separada
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                nextDocument.setStatus("COMPLETED");
                repository.save(nextDocument);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        return ResponseEntity.ok(printingDoc);
    }

    @DeleteMapping("/clean-completed")
    @Transactional
    public ResponseEntity<?> cleanCompletedDocuments() {
        repository.deleteByStatus("COMPLETED");
        return ResponseEntity.ok().build();
    }
} 
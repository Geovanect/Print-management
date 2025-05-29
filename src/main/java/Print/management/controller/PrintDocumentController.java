package Print.management.controller;

import Print.management.model.PrintDocument;
import Print.management.repository.PrintDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class PrintDocumentController {

    @Autowired
    private PrintDocumentRepository repository;

    @GetMapping
    public List<PrintDocument> getAllDocuments() {
        return repository.findAll();
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
} 
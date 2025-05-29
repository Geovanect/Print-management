package Print.management.repository;

import Print.management.model.PrintDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintDocumentRepository extends JpaRepository<PrintDocument, Long> {
} 
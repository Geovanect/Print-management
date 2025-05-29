package Print.management.repository;

import Print.management.model.PrintDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrintDocumentRepository extends JpaRepository<PrintDocument, Long> {
    
    @Query("SELECT p FROM PrintDocument p ORDER BY p.priority DESC, p.id ASC")
    List<PrintDocument> findAllOrderByPriorityAndId();
    
    @Query("SELECT p FROM PrintDocument p WHERE p.status = :status ORDER BY p.priority DESC, p.id ASC")
    List<PrintDocument> findByStatusOrderByPriorityAndId(@Param("status") String status);
} 
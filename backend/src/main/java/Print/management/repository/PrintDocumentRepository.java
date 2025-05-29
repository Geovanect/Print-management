package Print.management.repository;

import Print.management.model.PrintDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PrintDocumentRepository extends JpaRepository<PrintDocument, Long> {
    
    @Query("SELECT p FROM PrintDocument p WHERE p.status = :status ORDER BY p.priority DESC, p.id ASC")
    List<PrintDocument> findByStatusOrderByPriorityAndId(@Param("status") String status);
    
    @Query("SELECT p FROM PrintDocument p WHERE p.status = :status")
    Page<PrintDocument> findByStatus(@Param("status") String status, Pageable pageable);

    @Modifying
    @Query("DELETE FROM PrintDocument p WHERE p.status = :status")
    void deleteByStatus(@Param("status") String status);
} 
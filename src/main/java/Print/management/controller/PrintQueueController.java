package Print.management.controller;

import Print.management.model.Document;
import Print.management.service.PrintQueueService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void addDocument(@RequestBody Document document ){
        printQueueService.addDocument(document);
    }

    @GetMapping()
    public List<Document> listDocument(){
        return printQueueService.listDocument();
    }
    @PostMapping("/print")
    public List<Document> printDocument(){
        return printQueueService.listDocument();
    }
}

package com.bandtech.eventech.controller.V2;

import com.bandtech.eventech.Service.V2.FileService;
import com.bandtech.eventech.model.V2.EventJPA;
import com.bandtech.eventech.Repository.IEventRepository;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/v2/events/EventJPA")
public class EventControllerJPA {
    @Autowired
    private IEventRepository repository;
    @Autowired
    private  FileService fileService;
    List<EventJPA> event;
    Optional<EventJPA> eventGet;
    Long id ;

    //TODO Endpoint criar eventos com novos campos
    //Campo de imagem(ok), cidade, preço e categoria

    @PostMapping
    public ResponseEntity create(@RequestBody EventJPA event) {
        repository.save(event);
        return status(201).build();
    }
    @GetMapping("/{eventId}")
    public ResponseEntity get(@PathVariable int eventId){
        eventGet = repository.findById(eventId);

            if (!eventGet.isPresent())
            {
                return  badRequest().build();
            }
            else{
                return ok(eventGet);
        }
    }

    @GetMapping
    public ResponseEntity getListId(){
        event = repository.findAll();

        if (event.isEmpty())
            return noContent().build();
        else
            return ok(event);
    }

    @DeleteMapping("/{eventd}")
    public ResponseEntity delete(@PathVariable int eventId){
        if (this.repository.existsById(eventId)) {
            this.repository.deleteById(eventId);
            return ok().build();
        }
        else{
            return notFound().build();

        }
    }
    @PutMapping("/{eventId}")
    public ResponseEntity update(@PathVariable int eventId,
                                 @RequestBody EventJPA event){
        Optional<EventJPA> consultaExistente = this.repository.findById(eventId);
        if(consultaExistente.isPresent()) {
            EventJPA eventEncontrado = consultaExistente.get();

             eventEncontrado.setName(event.getName());
             eventEncontrado.setInitialDate(event.getInitialDate());
             eventEncontrado.setFinalDate(event.getFinalDate());
             eventEncontrado.setCategoryId(event.getCategoryId());
             eventEncontrado.setPlaceId(event.getPlaceId());
             eventEncontrado.setCreatedBy(event.getCreatedBy());
             eventEncontrado.setDescription(event.getDescription());
             eventEncontrado.isCancelled();
             eventEncontrado.setAgeRange(event.getAgeRange());


            this.repository.save(eventEncontrado);
            return ok().build();
        }
        else{
            return notFound().build();
        }
    }
    @GetMapping("/file")
    public ResponseEntity getFile() throws CsvRequiredFieldEmptyException,
            IOException, CsvDataTypeMismatchException {

        fileService.montaArquivo(id);

        return ok().build();
    }

    @GetMapping("/dash")
    public ResponseEntity getCoutEvents(){
        Integer count = repository.getCountEvent();

        if (count != null)
            return ok(count);
        else return noContent().build();
    }
}

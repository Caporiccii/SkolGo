package com.bandtech.eventech.controller.V2;

import com.bandtech.eventech.Repository.ITicketRepository;
import com.bandtech.eventech.model.V2.TicketJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;


@RestController
@RequestMapping("/v2/tickets/TicketJPA")
public class TicketControllerJPA {

    @Autowired
    private ITicketRepository repository;

    @PostMapping
    public ResponseEntity create(@RequestBody TicketJPA ticket){
        repository.save(ticket);
        return status(201).build();
    }

    @GetMapping
    public ResponseEntity getByList(){
        List<TicketJPA> ticket = repository.findAll();
        if (ticket.isEmpty())
        {
            return  badRequest().build();
        }
        else{
            return ok(ticket);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity get(@PathVariable int ticketId){
        Optional<TicketJPA> ticket = repository.findById(ticketId);
            if (!ticket.isPresent())
            {
                return  badRequest().build();
            }
            else{
                return ok(ticket);
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity delete(@PathVariable int ticketId){
        if (this.repository.existsById(ticketId)) {
            this.repository.deleteById(ticketId);
            return ok().build();
        }
        else{
            return notFound().build();

        }
    }
    @PutMapping("/{ticketId}")
    public ResponseEntity update(@PathVariable int ticketId,
                                 @RequestBody TicketJPA ticket){
        Optional<TicketJPA> consultaExistente = this.repository.findById(ticketId);
        if(consultaExistente.isPresent()) {
            TicketJPA  ticketEncontrado = consultaExistente.get();

            ticketEncontrado.setName(ticket.getName());
            ticketEncontrado.setInitialDate((ticket.getInitialDate()));
            ticketEncontrado.setFinalDate(ticket.getFinalDate());
            ticketEncontrado.setEvent_Id(ticket.getEvent_Id());
            ticketEncontrado.setQtd(ticket.getQtd());
            ticketEncontrado.setQtdPerPerson(ticket.getQtdPerPerson());
            ticketEncontrado.setBuyer(ticket.getBuyer());
            ticketEncontrado.isPayment();
            ticketEncontrado.setPrice(ticket.getPrice());

            this.repository.save(ticketEncontrado);
            return ok().build();
        }
        else{
            return notFound().build();
        }
    }

    @GetMapping("/dash")
    public ResponseEntity getCoutTicket(){
        Integer count = repository.getCountTicket();

        if (count != null)
            return ok(count);
        else
            return noContent().build();
    }

    @GetMapping("/total-sale")
    public ResponseEntity getSumTicket(){
        Double sum = repository.getTotalSaleTicket();

        if (sum != null)
            return ok(sum);
        else
            return noContent().build();
    }

    @GetMapping("/event/{event_Id}")
    public ResponseEntity getTicketByEventId(@PathVariable("event_Id") Integer eventId) {

        List<TicketJPA> tickets = repository.getTicketsByEventId(eventId);

        if (tickets.isEmpty())
            return noContent().build();
        else
            return ok(tickets);
    }

}

package br.unibh.sdm.backend_cliente.rest;

import java.util.Collections;
import java.util.List;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.unibh.sdm.backend_cliente.entidades.Cliente;
import br.unibh.sdm.backend_cliente.negocio.ClienteService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;

/**
 * Classe contendo as definicoes de servicos REST/JSON para Cliente
 * @author jhcru
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "cliente")
public class ClienteController {
   
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    @GetMapping(value = "")
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }
    
    @GetMapping(value="{id}")
    public Object getClienteById(@PathVariable @NotNull Long id) throws Exception{
        if (clienteService.clienteExists(id)){
            return clienteService.getClienteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap("errorMessage", "Objeto não encontrado"));
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object createCliente(@RequestBody @NotNull Cliente cliente) throws Exception {
        try {
            return clienteService.saveCliente(cliente);
        } catch (TransactionSystemException e){
            if (e.getRootCause() instanceof ConstraintViolationException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Collections.singletonMap("errorMessage", e.getRootCause().getMessage()));
            } else {
                throw e;
            }
        }         
    }
    
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateCliente(@PathVariable Long id, 
    		@RequestBody @NotNull Cliente cliente) throws Exception {
        if (clienteService.clienteExists(id)){
            try {
                return clienteService.saveCliente(cliente);
            } catch (TransactionSystemException e){
                if (e.getRootCause() instanceof ConstraintViolationException){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Collections.singletonMap("errorMessage", e.getRootCause().getMessage()));
                } else {
                    throw e;
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap("errorMessage", "Objeto não encontrado para alteração"));
        }
    }

    @DeleteMapping(value = "{id}")
    public Object updateCliente(@PathVariable Long id) throws Exception {
        if (clienteService.clienteExists(id)){
            clienteService.deleteCliente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                Collections.singletonMap("message", "Objeto excluído com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap("errorMessage", "Objeto não encontrado para exclusão"));
        }
    }
    
}
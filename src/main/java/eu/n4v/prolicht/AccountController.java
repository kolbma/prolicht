package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import eu.n4v.prolicht.model.Account;
import eu.n4v.prolicht.model.AccountRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class AccountController {

    private final AccountRepository repository;

    private final AccountResourceAssembler assembler;

    AccountController(AccountRepository repository, AccountResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/account", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "all", authorizations = {@Authorization(value = "basicAuth")})
    Resources<Resource<Account>> all() {
        List<Resource<Account>> accounts = repository.findAll().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(accounts,
                linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/account", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newAccount", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newAccount(@RequestBody Account newAccount) throws URISyntaxException {
        Resource<Account> resource = assembler.toResource(repository.save(newAccount));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/account/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "one", authorizations = {@Authorization(value = "basicAuth")})
    Resource<Account> one(@PathVariable Long id) {
        Account account = repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(account);
    }

    @PutMapping(value = "/account/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "replaceAccount", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceAccount(@RequestBody Account newAccount, @PathVariable Long id)
            throws URISyntaxException {
        Account updatedAccount = repository.findById(id).map(account -> {
            account.setUsername(newAccount.getUsername());
            account.setPassword(newAccount.getPassword());
            return repository.save(account);
        }).orElseGet(() -> {
            newAccount.setId(id);
            return repository.save(newAccount);
        });
        Resource<Account> resource = assembler.toResource(updatedAccount);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/account/{id}")
    @ApiOperation(value = "deleteAccount", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

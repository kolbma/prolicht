package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Account;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class AccountResourceAssembler implements ResourceAssembler<Account, Resource<Account>> {

    @Override
    public Resource<Account> toResource(Account account) {

        return new Resource<>(account,
                linkTo(methodOn(AccountController.class).one(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).all()).withRel("accounts"));
    }
}

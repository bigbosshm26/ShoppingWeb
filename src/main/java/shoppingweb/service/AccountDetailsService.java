package shoppingweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import shoppingweb.entity.Account;
import shoppingweb.repository.AccountRepository;

@Service
public class AccountDetailsService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = accountRepo.findByUsername(username).orElse(null);
		if(account != null) {
			return account;
		}
		throw new UsernameNotFoundException(
                "Account '" + username + "' not found");
	}
	
	
}

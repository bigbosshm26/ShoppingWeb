package shoppingweb.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account implements UserDetails{
	 
	private static final long serialVersionUID = 964284896958397417L;
	public static final String ROLE_MANAGER = "MANAGER";
	public static final String ROLE_EMPLOYEE = "EMPLOYEE";

	@Id
	@Size(max = 20)
	@NotNull
	@Column(name = "USER_NAME")
	private String username;
	
	@Size(max = 128)
	@NotNull
	@Column(name = "ENCRYPTED_PASSWORD")
	private String password;
	
	@NotNull
	@Column(name = "ACTIVE")
	private boolean active;
	
	@NotNull
	@Column(name = "USER_ROLE")
	private String userRole;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(this.getUserRole()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}

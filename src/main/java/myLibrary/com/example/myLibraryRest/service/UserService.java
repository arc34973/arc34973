package myLibrary.com.example.myLibraryRest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import myLibrary.com.example.myLibraryRest.model.User;
import myLibrary.com.example.myLibraryRest.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByUserName(userName);
			if (user == null) {
				throw new UsernameNotFoundException("User does not exist");
			}

			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			if (!(StringUtils.isEmpty(user.getRole()))) {
				String[] roles = user.getRole().split(",");
				for (String role : roles) {
					if (user.getRole() != null) {
						authorities.add(new SimpleGrantedAuthority(role.trim()));
					}
				}
			}
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword().trim(),
					authorities);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

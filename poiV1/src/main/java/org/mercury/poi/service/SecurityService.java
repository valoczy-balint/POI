package org.mercury.poi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.mercury.poi.dao.Dao;
import org.mercury.poi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SecurityService implements UserDetailsService {

	protected static Logger logger = Logger.getLogger("Security Service");
	
	@Autowired
	Dao securityDao; 

	/*public Dao getDao() {
		return securityDao;
	}

	public void setDao(Dao dao) {
		this.securityDao = dao;
	}*/

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		logger.info("loadUserByUsername called for username: " + username);
		UserDetails user = null;
		
		try {
			User dbUser = securityDao.getUser(username);
			user = new org.springframework.security.core.userdetails.User(
					dbUser.getUsername(), 
					dbUser.getPassword().toLowerCase(),
					true,
					true,
					true,
					true,
					getAuthorities(dbUser.getAccess()) );
			logger.info(dbUser.getUsername() + " " + dbUser.getPassword().toLowerCase() + getAuthorities(dbUser.getAccess()));
		} catch (Exception e) {
			logger.error("Error in retrieving user", e);
			//throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;
	}
	
	/**
	 * Retrieves the correct ROLE type depending on the access level, where access level is an Integer.
	 * Basically, this interprets the access value whether it's for a regular user or admin.
	 * 
	 * @param access an integer value representing the access of the user
	 * @return collection of granted authorities
	 */
	 public Collection<GrantedAuthority> getAuthorities(Integer access) {

			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
			
			/** 	All users are granted with ROLE_USER access		*/
			logger.debug("Grant ROLE_USER to this user");
			authList.add(new GrantedAuthorityImpl("ROLE_USER"));
			
			/** 	Check if this user has admin access 
			 		We interpret Integer(1) as an admin user		*/
			if ( access.compareTo(1) == 0) {
				logger.debug("Grant ROLE_ADMIN to this user");
				authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			}

			return authList;
	  }
}

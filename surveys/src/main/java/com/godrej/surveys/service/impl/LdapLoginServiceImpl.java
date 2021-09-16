package com.godrej.surveys.service.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.godrej.surveys.dto.LoginUserDto;
import com.godrej.surveys.service.LoginService;
import com.godrej.surveys.util.AppConstants;

@Service
public class LdapLoginServiceImpl implements LoginService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Hashtable<String, String> env = new Hashtable<>();

	@PostConstruct
	public void init() {

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + AppConstants.LDAP_HOST + ":" + AppConstants.LDAP_PORT);
		env.put(Context.SECURITY_AUTHENTICATION, AppConstants.LDAP_AUTH_METHOD);
		env.put(Context.SECURITY_PRINCIPAL, AppConstants.LDAP_DN);
		env.put(Context.SECURITY_CREDENTIALS, AppConstants.LDAP_PW);
		env.put("java.naming.ldap.version", AppConstants.LDAP_VERSION);
		env.put(Context.REFERRAL, "ignore");

	}

	@Override
	public LoginUserDto getldapUserData(LoginUserDto dto) {

		Attributes attrs;
		String[] attrIDs = { "distinguishedName", "sn", "givenname", "mail", "telephonenumber", "sAMAccountName",
				"postalCode", "cn", "accountExpires", "initials", "description", "physicalDeliveryOfficeName",
				"postOfficeBox", "l", "c", "company", "manager", "title", "managedBy", "memberOf", "department",
				"targetAddress", "userAccountControl", "mobile" };
		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(attrIDs);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String filter = "(&(objectClass=user)(mail=" + dto.getEmail() + "))";
		try {
			LdapContext ctx = new InitialLdapContext(env, null);
			NamingEnumeration<?> answer = ctx.search(AppConstants.BASE_DN, filter, ctls);
			if (answer.hasMore()) {
				Properties authEnv = new Properties();
				authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				authEnv.put(Context.PROVIDER_URL, "ldap://" + AppConstants.LDAP_HOST + ":" + AppConstants.LDAP_PORT);
				authEnv.put(Context.SECURITY_PRINCIPAL, dto.getEmail());
				authEnv.put(Context.SECURITY_CREDENTIALS, dto.getPassword());
				new InitialDirContext(authEnv);
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				if (attrs != null) {
					NamingEnumeration<?> nameAttr = attrs.getAll();
					dto=getUserDetails(nameAttr);
					dto.setValid(true);
				}
			}
			ctx.close();
			return dto;
		} catch (Exception e) {
			log.error("LDAP Employee connection failed", e);
			dto.setValid(false);
			return dto;
		}
	}

	private LoginUserDto getUserDetails(NamingEnumeration<?> nameAttr) throws NamingException {
		LoginUserDto ldapDto=new LoginUserDto();
		while (nameAttr.hasMoreElements()) {
			Attribute attr = (Attribute) nameAttr.next();
			String attrId = attr.getID();
			Enumeration<?> vals = attr.getAll();
			while (vals.hasMoreElements()) {
				String attrVal = (String) vals.nextElement();
				if (attrId.equals("sn")) {
					ldapDto.setLastName(attrVal);
				}
				if (attrId.equals("mail")) {
					ldapDto.setEmail(attrVal);
				}
				if (attrId.equals("givenName")) {
					ldapDto.setFirstName(attrVal);
				}
				if (attrId.equals("accountExpires")) {
					ldapDto.setAccountExpires(attrVal);
				}
				if (attrId.equals("distinguishedName")) {
					ldapDto.setDistinguishedName(attrVal);
				}
				if (attrId.equals("cn")) {
					ldapDto.setUserName(attrVal);
				}
				if (attrId.equals("sAMAccountName")) {
					ldapDto.setAccountName(attrVal);
				}
				if (attrId.equals("telephonenumber")) {
					ldapDto.setTelephonenumber(attrVal);
				}
				if (attrId.equals("mobile")) {
					ldapDto.setMobile(attrVal);
				}
				if (attrId.equals("postalCode")) {
					ldapDto.setPostalCode(attrVal);
				}
				if (attrId.equals("company")) {
					ldapDto.setCompany(attrVal);
				}
				if (attrId.equals("department")) {
					ldapDto.setDepartment(attrVal);
				}
				if (attrId.equals("memberOf")) {
					ldapDto.setGroups(attrVal);
				}
				if (attrId.equals("physicalDeliveryOfficeName")) {
					ldapDto.setOfficeName(attrVal);
				}
				if (attrId.equals("l")) {
					ldapDto.setCity(attrVal);
				}
				if (attrId.equals("targetAddress")) {
					ldapDto.setTargetAddress(attrVal);
				}
				if (attrId.equals("description")) {
					ldapDto.setDescription(attrVal);
				}
				if (attrId.equals("managedBy")) {
					ldapDto.setManagedBy(attrVal);
				}
				if (attrId.equals("title")) {
					ldapDto.setTitle(attrVal);
				}
				if (attrId.equals("postOfficeBox")) {
					ldapDto.setPostOffice(attrVal);
				}
				if (attrId.equals("initials")) {
					ldapDto.setMiddleName(attrVal);
				}							
			}
		}
		return ldapDto;
	}


	public static void main(String[] args) {
		LoginUserDto login = new LoginUserDto();
		login.setEmail("sathish.kyatham@godrejproperties.com");
		login.setPassword("Gpl@2020");
		LdapLoginServiceImpl service =  new LdapLoginServiceImpl();
		service.init();
		
		System.out.println("Test");
		
		service.getldapUserData(login);
	}
}

package com.example.bloomgift.service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.utils.EmailUtil;
import com.example.bloomgift.utils.OtpUtil;


@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired 
    private RoleRepository roleRepository;

    
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private OtpUtil otpUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException("Email không tồn tại.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(account.getRoleID().getRoleName());

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                Collections.singletonList(authority));
    }

public Page<AccountReponse> getAllAccounts(Pageable pageable) {
    return accountRepository.findAll(pageable)
            .map(account -> new AccountReponse(
                    account.getAccountID(),
                    account.getFullname(),
                    account.getEmail(),
                    account.getPassword(),
                    account.getAddress(),
                    account.getGender(),
                    account.getAvatar(),
                    account.getBirthday(),
                    account.getPhone(),
                    account.getAccountStatus(),
                    account.getRoleName() 
            ));
}
public AccountReponse getAccountById(int accountID) {
    Account account = accountRepository.findById(accountID).orElseThrow();
    return new AccountReponse(
            account.getAccountID(),
            account.getFullname(),
            account.getEmail(),
            account.getPassword(),
            account.getAddress(),
            account.getGender(),
            account.getAvatar(),
            account.getBirthday(),
            account.getPhone(),
            account.getAccountStatus(),
            account.getRoleName() 
        );
    }

    public void deleteAccount(Integer accountID) {
        Account existingAccount = accountRepository.findById(accountID)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if ("ADMIN".equals(existingAccount.getRoleName())) {
            throw new RuntimeException("Cannot delete admin account");
        }

        accountRepository.delete(existingAccount);
    }
    public void createAccount(AccountRequest accountRequest){
        checkvalidateAccount(accountRequest);
        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String roleName = accountRequest.getRoleName();
        String gender = accountRequest.getGender();
        String avatar = accountRequest.getAvatar();
        Account existingEmail = accountRepository.findByEmail(email);
        if (existingEmail != null) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        Account existingPhone = accountRepository.findByPhone(phone);
        if (existingPhone != null) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        com.example.bloomgift.model.Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        Account account = new Account();
        account.setRoleID(role);
        account.setFullname(fullname);
        account.setEmail(email);
        account.setAddress(address);
        account.setBirthday(birthday);
        account.setPhone(phone);
        account.setPassword(password);
        account.setAccountStatus(true);
        account.setGender(gender);
        account.setAvatar(avatar);
        accountRepository.save(account);
        

    }

    public Account updateAccount(Integer id,AccountRequest accountRequest){
        checkvalidateAccount(accountRequest) ;
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
           throw new RuntimeException("Không tìm thấy tài khoản"); 
        }

        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String roleName = accountRequest.getRoleName();
        String gender = accountRequest.getGender();
        Boolean accountstatus = accountRequest.getAccountStatus();
        String avatar = accountRequest.getAvatar();

        //--------------------------------------------//
        existingAccount.setFullname(fullname);
        existingAccount.setEmail(email);
        existingAccount.setPassword(password);
        existingAccount.setPhone(phone);
        existingAccount.setAddress(address);
        existingAccount.setBirthday(birthday);
        existingAccount.setGender(gender);
        existingAccount.setAccountStatus(accountstatus);
        existingAccount.setAvatar(avatar);

        if (roleName != null && !roleName.isEmpty()) {
            com.example.bloomgift.model.Role role = roleRepository.findByRoleName(roleName);
            if (role == null) {
                throw new RuntimeException("Role not found");
            }
            existingAccount.setRoleID(role);
        }
           if(accountRequest.getPassword() !=null && !accountRequest.getPassword().isEmpty()
                    && !accountRequest.getPassword().equals(existingAccount.getPassword())){
                             BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                             String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());
                             existingAccount.setPassword(encodedPassword);
        }
       
        return accountRepository.save(existingAccount);



    }
    private void checkvalidateAccount(AccountRequest accountRequest){
        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String rolename = accountRequest.getRoleName();
        String gender = accountRequest.getGender();
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
            throw new RuntimeException("Invalid email format");
        }
        if (birthday.after(new Date())) {
            throw new RuntimeException("Birthday cannot be in the future.");
        }
        if (!StringUtils.hasText(fullname)
        || !StringUtils.hasText(password) 
            || !StringUtils.hasText(email)
                || !StringUtils.hasText(address)
                    || !StringUtils.hasText(rolename)
                        || gender == null
                            || phone == null
                                || birthday == null) {
        throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
    }

    }


    public Map<String, String> setPassword(String email, String newPassword) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return Collections.singletonMap("message", "Email không tồn tại.");
        }
        account.setPassword(newPassword);
        accountRepository.save(account);
        return Collections.singletonMap("message", "Mật khẩu đã được thiết lập. Vui lòng đăng nhập.");
    }

    public Map<String, String> forgetPassword(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return Collections.singletonMap("message", "Email không tồn tại.");
        }
        try {
            emailUtil.sendForgetPasswordEmail(email);
        } catch (Exception e) {
            return Collections.singletonMap("message", "Có lỗi xảy ra khi gửi email.");
        }
        return Collections.singletonMap("message", "Mã OTP đã được gửi đến email của bạn.");
    }

}

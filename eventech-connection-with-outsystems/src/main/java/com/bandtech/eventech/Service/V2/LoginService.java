package com.bandtech.eventech.Service.V2;

import com.bandtech.eventech.Repository.ICompanyRepository;
import com.bandtech.eventech.Repository.IUserRepository;
import com.bandtech.eventech.model.V1.Company;
import com.bandtech.eventech.model.V1.User;
import com.bandtech.eventech.model.V2.CompanyJPA;
import com.bandtech.eventech.model.V2.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
@Service
public class LoginService  {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private ICompanyRepository companyRepository;
    List<UserJPA> usuarios;
    List<CompanyJPA> empresas;
    String loginUser;
    String senhaUser;
    private UserJPA user;
    private CompanyJPA company;
    private boolean result;
    public Boolean logUsu(@PathParam("nome") String nome,
                           @PathParam("senha") String senha) {

        usuarios = repository.getNameandPassword(nome,senha);
        user= new UserJPA();
        usuarios.add(user);
        for (UserJPA user : usuarios) {
            loginUser = user.getName();
            senhaUser = user.getPassword();
            if (!nome.equals(loginUser) && !senha.equals(senhaUser)) {
                System.out.println("Login inválido");
                return  result =false;
            } else {
                System.out.println("Login Válido");
                return result=  true;
            }
        }
  return result;

    }
    public Boolean logEmp(@PathParam("empresa") String empresa,
                          @PathParam("senha") String senha) {

        empresas = companyRepository.getCompanyandPassword(empresa,senha);
        company = new CompanyJPA();
        empresas.add(company);

        //empresas.forEach(emp -> !emp.getName().equals("") &&  !emp.getPassword().equals("") );
        for (CompanyJPA company : empresas) {
            loginUser = user.getName();
            senhaUser = user.getPassword();
            if (!empresa.equals(loginUser) && !senha.equals(senhaUser)) {
                System.out.println("Login inválido");
                return  result =false;
            } else {
                System.out.println("Login Válido");
                return result=  true;
            }
        }
        return result;
}
}
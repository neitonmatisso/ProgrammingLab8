package ru.nik.serverPackage.accounts;

import com.google.gson.Gson;
import org.hibernate.loader.plan.build.internal.returns.ScalarReturnImpl;
import ru.nik.businessLogic.Exceptions.AccountRegisterException;
import ru.nik.businessLogic.Exceptions.LogInException;
import ru.nik.clientPackage.Account;
import ru.nik.connectionPackage.Connection;
import ru.nik.connectionPackage.TransferJsonObject;
import ru.nik.dbpackage.userDAO.UserDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.ResultType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountManager {
    private Map<String, User> accounts;
    private UserDAO userDAO = new UserDAO();
    private TokenManager tokenManager;
    private PasswordIncoder passwordIncoder;
    public AccountManager(TokenManager manager){
        tokenManager = manager;
        passwordIncoder = new PasswordIncoder();
        accounts = new HashMap<>();
        loadUsers(userDAO.loadUsers());
        User root = new User("root","root",UserPriority.ROOT, true);
        accounts.put(root.getLogin(),root);
    }
    public void registerAcc(ResultShell res, Account account, Connection connection) throws AccountRegisterException, IOException {
        if(accounts.containsKey(account.getLogin())){
            throw new AccountRegisterException();
        }
        String token = tokenManager.getToken();
        String solt = passwordIncoder.getSalt();
        User user = new User();
        user.setLogin(account.getLogin());
        user.setPass(passwordIncoder.hash(account.getPassword(),solt));
        user.setPriority(UserPriority.USER);
        user.setConnection(connection);
        user.setToken(token);
        user.setSolt(solt);
        accounts.put(user.getLogin(),user);
        userDAO.addNewUser(user);
        res.checkResult(token);
//        ResultShell resultShell = new ResultShell();
//        resultShell.setResultType(ResultType.USER);
//        resultShell.checkResult(new Gson().toJson(user));
//        connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(resultShell)));
    }
    public void checkAcc(ResultShell res, Account account, Connection connection) throws LogInException, IOException {
        if(!accounts.containsKey(account.getLogin())){
            res.checkResult("Аккаунта с таким логином не существует! Повторите попытку");
            throw new LogInException();
        }
        if(!accounts.get(account.getLogin()).getPass()
                .equals(passwordIncoder.hash(account.getPassword(),accounts.get(account.getLogin()).getSolt()))){
            res.checkResult("Введен неверный пароль!");
            throw new LogInException();
        }
        accounts.get(account.getLogin()).setConnection(connection);
        String s = tokenManager.getToken();
        accounts.get(account.getLogin()).setToken(s);
        res.checkResult(s);
//        User currentUser = accounts.get(account.getLogin());
//        ResultShell resultShell = new ResultShell();
//        resultShell.setResultType(ResultType.USER);
//        resultShell.checkResult(new Gson().toJson(currentUser));
//        System.out.println(resultShell);
//        connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(resultShell)));
    }
    public User getUser(String name){
        return accounts.get(name);
    }
    public void loadUsers(List<User> userList ){
        for(User user : userList ){
            accounts.put(user.getLogin(),user);
        }
    }
    public void updateConnection(String name , Connection connection){
        accounts.get(name).setConnection(connection);
    }
    public Set<String> gerUserNames(){
        return accounts.keySet();
    }

    public void removeDisconnectToken(String token, String login){
        accounts.get(login).setToken(null);
        tokenManager.removeToken(token);
        System.out.println("Текущие токены: " + tokenManager.showTokens());
    }
    public String userInfo(){
        return accounts.toString();
    }
    public boolean checkToken(String token){
        return tokenManager.checkContains(token);
    }


}


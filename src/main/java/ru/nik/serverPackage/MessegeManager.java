package ru.nik.serverPackage;

import com.google.gson.Gson;
import ru.nik.connectionPackage.TransferJsonObject;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.User;

import java.io.IOException;

public class MessegeManager {
    private AccountManager accountManager;
    public MessegeManager(AccountManager accountManager){
        this.accountManager = accountManager;
    }
    public void sendMassege(User user, String userName, String messege) throws NullPointerException {
        try {
            ResultShell messRes = new ResultShell();
            messRes.setResultType(ResultType.MESSAGE);
            messRes.checkResult(user.getLogin() +": " + messege);
            accountManager.getUser(userName).getConnection().sendTransferObject(new TransferJsonObject(new Gson().toJson(messRes)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendAll(User user, String messege) throws NullPointerException{
        for(String userName : accountManager.gerUserNames()){
            try {
                ResultShell messRes = new ResultShell();
                messRes.setResultType(ResultType.MESSAGE);
                User reciver = accountManager.getUser(userName);
                messRes.checkResult(user.getLogin()+ ": " + messege);
                reciver.getConnection().sendTransferObject(new TransferJsonObject(new Gson().toJson(messRes)));
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException ex) {
                System.out.println("Не в сети " + userName);
            }
        }
    }
}

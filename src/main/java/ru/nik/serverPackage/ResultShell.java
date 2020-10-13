package ru.nik.serverPackage;

import ru.nik.serverPackage.accounts.User;

import javax.jws.soap.SOAPBinding;

public class ResultShell {
    private  String resulst;
    private ResultType resultType;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResultShell(){
        resulst = "";
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public void checkResult(String s){
        resulst += s + "\n";
    }
    public String getResulst(){
        return resulst;
    }
}

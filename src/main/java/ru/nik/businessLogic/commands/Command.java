package ru.nik.businessLogic.commands;

import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 * Общий интерефейс для любой команды 
 */
public interface Command { //TODO сделать отмену команд будет реализовано оч не скоро
    void execute(String Option, ResultShell resultShell, User user);
}

package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

public class FilterGreaterThanPriceCommand implements Command {
    ColletionWorker colletionWorker;
    ControlUnit controlUnit;
    public FilterGreaterThanPriceCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("filter_price",this);
        controlUnit.addNewSettings(CommandType.ARGS_COMMAND,"filter_price");
    }

    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        resultShell.checkResult(colletionWorker.priceFilter(Long.parseLong(Option)));
    }
}

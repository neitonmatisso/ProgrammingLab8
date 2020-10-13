package ru.nik.businessLogic.mainPackage;

import ru.nik.businessLogic.ArrayListWorker;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.businessLogic.commands.*;

import java.io.IOException;

public class Components {
    public void load() throws IOException {
        ColletionWorker coll = new ArrayListWorker();
        ControlUnit cunit = new ControlUnit();
        FileManager fi = new FileManager(coll,"SaveFile");
        Command add = new AddElementCommand(cunit,coll);
        Command help = new HelpCommand(cunit);
        Command load = new LoadCommand(cunit,fi);
        Command save = new SaveCommand(cunit,fi);


    }
}

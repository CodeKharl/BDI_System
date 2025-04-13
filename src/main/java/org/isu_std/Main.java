package org.isu_std;

import org.isu_std.dao.*;
import org.isu_std.io.WindowsCLIClear;
import org.isu_std.io.SystemInput;

import java.util.Scanner;

public final class Main {
  public static void main(String[] args){
    SystemInput.setScan(new Scanner(System.in));

    DaoFactory daoFactory = DaoFactory.getInstance();

    SystemService systemService = new SystemService(daoFactory);
    SystemController systemController = new SystemController(systemService);
    SystemUI systemUI = new SystemUI(systemController);

    systemUI.start();
    SystemInput.scanClose();
    System.exit(0);

  }
}

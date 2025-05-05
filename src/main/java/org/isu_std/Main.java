package org.isu_std;

import org.isu_std.dao.*;
import org.isu_std.io.SystemInput;
import org.isu_std.login_signup.LogSignFactory;

import java.util.Scanner;

public final class Main {
  public static void main(String[] args){
    SystemInput.setScan(new Scanner(System.in));

    DaoFactory daoFactory = new DaoFactory();

    UserDao userDao = daoFactory.createUserDao();
    AdminDao adminDao = daoFactory.createAdminDao();
    BarangayDao barangayDao = daoFactory.createBrgyDao();
    LogSignFactory logSignFactory = new LogSignFactory(userDao, adminDao, barangayDao);

    PostLogNavFactory postLogNavFactory = new PostLogNavFactory(daoFactory);

    SystemService systemService = new SystemService(logSignFactory, postLogNavFactory);
    SystemController systemController = new SystemController(systemService);
    SystemUI systemUI = new SystemUI(systemController);

    systemUI.start();
    SystemInput.scanClose();
    System.exit(0);

  }
}

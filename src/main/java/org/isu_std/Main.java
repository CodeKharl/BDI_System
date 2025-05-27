package org.isu_std;

import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.*;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemInput;
import org.isu_std.login_signup.LogSignFactory;

import java.util.Scanner;

public final class Main {
  public static void main(String[] args){
    SystemInput.setScan(new Scanner(System.in));

    var mySQLDBConfig = new MySQLDBConfig();
    var jdbcHelper = new JDBCHelper(mySQLDBConfig);
    var daoFactory = new DaoFactory(jdbcHelper);

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

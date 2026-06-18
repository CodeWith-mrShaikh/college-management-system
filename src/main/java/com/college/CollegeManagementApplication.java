package com.college;

import com.college.menu.ConsoleMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CollegeManagementApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CollegeManagementApplication.class, args);
        if (ctx.containsBean("consoleMenu")) {
            ConsoleMenu menu = ctx.getBean(ConsoleMenu.class);
            menu.start();
        }
    }
}

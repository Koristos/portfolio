package ru.Koristos;

import java.nio.file.Paths;

public class RepoClientBoot {

    public static void main(String[] args) {
        new ClientModuleManager(Paths.get("ClientConfig.cfg")).start();

    }

}

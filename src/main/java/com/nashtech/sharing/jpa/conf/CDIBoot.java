package com.nashtech.sharing.jpa.conf;

import java.util.function.Consumer;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class CDIBoot {
    private CDIBoot () {

    }

    public static void run (Consumer<SeContainer> containerConsumer) {
        try (var container = SeContainerInitializer.newInstance().initialize()) {
            containerConsumer.accept(container);

        }
    }

}

plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.0.0"

prism {
    metadata {
        modId = "simple_villager_follow"
        name = "Simple Villager Follow"
        description = "Villagers follow players holding emeralds."
        license = "MIT"
        author("Leclowndu93150")
        author("Lupin")
    }

    version("26.1.2") {
        accessWidener("simple_villager_follow.accesswidener")

        fabric {
            loaderVersion = "0.19.2"
            fabricApi("0.146.1+26.1.2")
        }

        neoforge {
            loaderVersion = "26.1.2.29-beta"
            loaderVersionRange = "[26.1.2,)"
        }
    }
}

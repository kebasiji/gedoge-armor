include(
    "gedoge-armor-core",
    "gedoge-armor-autoconfigure",
    "gedoge-armor-starter",
    "example:gedoge-armor-example-rabbitmq-producer",
    "example:gedoge-armor-example-rabbitmq-consumer",
)

rootProject.name = "gedoge-armor"
rootProject.children.forEach { sub ->
    sub.buildFileName = "${sub.name}.gradle.kts"
}
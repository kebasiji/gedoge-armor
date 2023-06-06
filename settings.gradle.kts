include(
    "gedoge-armor-core",
)

rootProject.name = "gedoge-armor"
rootProject.children.forEach { sub ->
    sub.buildFileName = "${sub.name}.gradle.kts"
}
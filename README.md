## Notes

- clone bongo, and checkout gwt branch

- bongo can be in any directory, you just need to make sure `settings.gradle` is referencing bongo properly
`../bongo` for the case where `bongo` and `talossample` or your project, share the same common parent.

- Make sure version in gradle properties is 7.4 (matching this one)


- To GdxDefinition.gwt.xml add
```
<!--	hack-->
	<define-configuration-property name="artemis.reflect.include" is-multi-valued="true" />
	<inherits name='bongo-talos' />

	<inherits name="ru.finam.slf4jgwt.api.API"/>
	<inherits name="ru.finam.slf4jgwt.logging.gwt.Logging"/>
```

- In `build.gradle` if you have mavenLocal(), put that at the bottom of the repositories list, for some reason it breaks a dependency.

- To `build.gradle` add to core
```
        api "com.rockbite.bongo:talos:1.0-SNAPSHOT"
```

add to html
```
        api "ru.finam:slf4j-gwt:1.7.7.1"
        api "ru.finam:slf4j-gwt:1.7.7.1:sources"

        api "com.rockbite.bongo:talos:1.0-SNAPSHOT:web"
```

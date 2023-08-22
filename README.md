# About

Sourcecode (asciidoc) for my most famous psychology wiki, available here:

https://christophpickl.github.io/psywiki

How to release?

```shell
./gradlew build copyLocalDocs && \
git add . && \
git commit -m "publish new docs" && \
git push
```

# Gradle Tasks

* `build`: trigger asciidoc HTML generation
* `checkLinks`: checks all links via custom plugin
  * or use this guy here for free: https://www.deadlinkchecker.com/ 
* `copyLocalSite`: copy to `/Users/cpickl/Sites/psywiki` so it will be visible via http://localhost/~cpickl/psywiki/
* `copyLocalDocs`: copy to `/doc` where it will be used for public visibility

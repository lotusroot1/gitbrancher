# gitbrancher
This project aims to enable users to create their own feature branches on a remote system they don't have access to.
It can be used to faciliate pushing changes to a remote staging branch.

## Compile Requirements
- Maven 3+
- Project requires [gitbrancher-core](https://github.com/lotusroot1/gitbrancher-core#gitbrancher-core)


## Runtime Requirements
- Servlet 3.0 compatiable application container
- File named brancher.properties availabie in the classpath or by specifying a directory where it resides
- Your git personal token

If you are using Tomcat, you can set your JAVA_OPTS in bin/setenv.sh
```
export JAVA_OPTS=$JAVA_OPTS -Dcom.alex.brancher.config.dir=\<directory of brancher.properties\>
```

In the location of \<directory of brancher.properties\>, the following file and its contents must be present.

```
\#Sample brancher.properties
brancher.github.base.url=https://api.github.com
brancher.github.owner.username=<your git username>
brancher.github.token=<your git personal token>
```

Your git token must have:
- public_repo
- repo:status

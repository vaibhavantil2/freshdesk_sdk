## Setting up development environment

Setting development environment facilitates quick cycle of code, change, execute, debug. First prerequisite is to set the environment variables:

```
export FRSH_SDK_DEV=/path/to/code/freshapps_sdk/sdk/build/install/frsh
export PATH=$FRSH_SDK_DEV/bin:$PATH
```

To build the exploded directory of SDK:

```
$ gradle installDist
```

The exploded installation directory is created in the folder `/path/to/code/freshapps_sdk/sdk/build/install/frsh`.

### Note on the env variable FRSH_SDK_DEV

`FRSH_SDK_DEV` is a **developer-only** environment configuration. This environment variable must NOT be present when testing a SDK distribution. `FRSH_HOME` is the environment variable that must be used by testers and end users. `FRSH_SDK_DEV` is a convenience variable that overrides the need for the directory-structure dictated by `FRSH_HOME` variable.

### Working with `frsh init` command

When using `FRSH_SDK_DEV`, the `init` command is bound to fail if the `FRSH_HOME` environment variable does not have the file `$FRSH_HOME/template/plug-template.zip`.

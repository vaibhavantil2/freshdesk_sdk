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

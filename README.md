## Freshapps SDK

This project houses the SDK codebase. We have two sub-modules:

1. `sdk/`: This houses the SDK codebase.
2. `sdk-update/`: This houses the SDK update shell.

### Building the downloadable distribution

The below command packages the current version of the `sdk-update` with `sdk`.

```
$ gradle -b dist-build.gradle pkg
```

# SDK Update

This project provides a shell folder structure, and facility to check and install the latest release of SDK.

Folder structure when this app is installed:

```
.
├── bin
├── lib
└── sdk
    └── frsh-1.0
    └── frsh-1.1.0
```

`bin` and `lib` are managed by the `sdk-update` project. Every time a new version of SDK is downloaded from the web, it is installed in the `sdk/` folder. In the above folder structure, `sdk/frsh-1.1.0` is the latest version. Links to `sdk/frsh-1.1.0/bin/frsh` and `sdk/frsh-1.1.0/bin/frsh.bat` are created in `bin/`.

### Environment Configuration

Following environment variables need to be set:

```
export FRSH_HOME=/path/to/sdk-update
export PATH=$PATH:$FRSH_HOME/bin
```

### Version Check Logic

We use the [WizTools.org App Update Framework](https://github.com/wiztools/app-update-framework) for validating if there are any updated version available. The URL currently configured in the tool is:

http://s3.amazonaws.com/freshapps-staging-pub/sdk/version.json

This file serves content like:

```json
{
  "version": "1.0",
  "dl_url": "http://s3.amazonaws.com/freshapps-staging-pub/sdk/frsh-1.0.zip"
}
```

The version shown in the above service is checked with the SDK installations available in the local system. If local system is already updated, no operation is performed. Else, new version is downloaded and installed.

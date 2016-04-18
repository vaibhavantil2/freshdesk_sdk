# Frsh SDK in Node

## Run tests

Install mocha globally:

    npm install mocha -g

Run test:

    mocha test/

## Creating local distribution

    npm pack

This creates the file `frsh-sdk-0.1.0.tgz`. This pack can be hosted in any HTTP server, and installed thus:

    npm install http://host/path/frsh-sdk-0.1.0.tgz -g

For developers who want to install from local git clone:

    npm install /path/to/frsh-sdk -g


Build status: [![Build Status](https://travis-ci.org/bitcoin-solutions/multibit-hd.png?branch=develop)](https://travis-ci.org/bitcoin-solutions/multibit-hd)

Project status: Pre-release. Expect minor bugs and UI adjustments. Suitable for small scale production.

### MultiGroestl HD (MGHD)

A desktop Hierarchical Deterministic Wallet (HDW) for Groestlcoin using the Simplified Payment Verification (SPV) mode to provide very fast block chain synchronization.

The target audience is "international mainstream" which compels the user interface to remain as simple and consistent as possible while still retaining advanced capabilities
under the covers.

### Main website

Installers are available from the [Groestlcoin website](http://www.groestlcoin.org).

### Technologies

* Java 7 and Swing
* [groestlcoinj](https://github.com/groestlcoin/groestlcoinj) - Providing various Groestlcoin protocol utilities (GitHub is the reference)
* [hid4java](https://github.com/gary-rowe/hid4java) - Java library providing USB Human Interface Device (HID) native interface
* [MultiBit Hardware](https://github.com/bitcoin-solutions/multibit-hardware) - Java library providing Trezor support
* [Google Protocol Buffers](https://code.google.com/p/protobuf/) (protobuf) - For use with serialization and hardware communications
* [Install4j](https://www.ej-technologies.com/download/install4j/files) - for a smooth installation and update process

### Getting started

MultiGroestl HD is a standard Maven build from a GitHub repository and currently relies on some builds of libraries which aren't available in Maven Central.

Below are some basic instructions for developers - there is [more information in the MultiBit HD wiki](https://github.com/bitcoin-solutions/multibit-hd/wiki).

#### Verify you have Git

```
$ git --version
```

[Install git](https://help.github.com/articles/set-up-git/) if necessary.

Then, if this is your first time working with MultiGroestl HD source code, clone the source code repository (over HTTPS) using:

```
$ git clone https://github.com/groestlcoin/multigroestl-hd.git
```
A sub-directory called `multigroestl-hd` will be created which is your project root directory.

To update a previous clone of MultiGroestl HD use a pull instead:

```
$ cd <project root>
$ git pull
```

#### Verify you have Maven 3+

Most IDEs (such as [Intellij Community Edition](http://www.jetbrains.com/idea/download/)) come with support for Maven built in, but if not then you may need
 to [install it manually](http://maven.apache.org/download.cgi).

IDEs such as Eclipse may require the [m2eclipse plugin](http://www.sonatype.org/m2eclipse) to be configured.

To quickly check that you have Maven 3+ installed check on the command line:
```
$ mvn --version
```
Maven uses a file called `pom.xml` present in the MultiGroestl HD source code project directory to provide all the build information.

#### We currently use a forked version of Groestlcoinj

Our release cycle is different to that of bitcoinj and our version reflects as accurately as we can the state of play
when the bitcoinj fork code was frozen. For example `groestlcoinj-0.13-SNAPSHOT-alice-0.0.9` should be interpreted as
"a snapshot of upstream groestlcoinj 0.13 that has additional code (alice) that is released under version 0.0.9".

Deeper analysis of the actual git upstream can be seen through the tagging of the `master` branch.

Wherever possible, and time permitting, we will introduce our forked changes as a pull request into the upstream groestlcoinj
so that other projects can benefit but we must use a fork to ensure rapid updates are possible during development.

Anyone wishing to handle this part of the process is very welcome to offer up their assistance!

#### Start the application (from an IDE)

To run the application within an IDE, simply execute `MultiGroestlHD.main()` in the `mbhd-swing` module. No command line parameters are needed, although a Groestlcoin URI is accepted.

#### Start the application (from the command line)

To run the application from the command line, first build from the project root directory (pulling in all sources from upstream):
```
$ cd <project root>
$ mvn clean dependency:sources install
```
then start the application using the shaded JAR:
```
$ java -jar mbhd-swing/target/multigroestl-hd.jar
```
No command line parameters are needed, although BIP 21 and BIP 72 Groestlcoin URIs are accepted. In the example below a BIP 21 Groestlcoin URI
is presented, the quotes are required to avoid URL decoding:
```
$ java -jar mbhd-swing/target/multigroestl-hd.jar "groestlcoin:FZ4AfMJjHNzjtMHvFR244TZf2934Y6J5Fr?amount=0.01&label=Please%20donate%20to%20groestlcoin.org"
```
#### Multiple instances

MultiGroestl HD will avoid multiple instances by using port 8330 as a method of detecting another running instance. If port 8330 cannot be bound MultiGroestl HD will assume that
another instance is running and hand over any Bitcoin URI arguments present when it started. It will then perform a hard shutdown terminating its own JVM. The other instance will
react to receiving a Bitcoin URI message on port 8330 by displaying an alert bar requesting the user to act upon the Groestlcoin URI payment request.

### Frequently asked questions (FAQ)

Here are some common questions that developers ask when they first encounter MGHD.

### How can I contribute ?

Simply add yourself as a watcher to the repository and keep and eye on issues that interest you.

In general issues are labelled with a yellow `awaiting review` or a blue `in progress` to indicate where our attention is focused. We would appreciate you updating and running up
the code and verifying that an `awaiting review` does what it is supposed to. If you could then post a comment similar to `Works for me on Linux` then that would help us to close
off the issue faster, or engage further with it to get bugs fixed.

Of course, if you want to contribute coding effort or deeper code review and commentary that would be most appreciated as well. We want MultiGroestl HD to be as solid as we can make it.

As always, donations to the MultiGroestl address are welcome: [FZ4AfMJjHNzjtMHvFR244TZf2934Y6J5Fr](groestlcoin:FZ4AfMJjHNzjtMHvFR244TZf2934Y6J5Fr?amount=0.01&label=Please%20donate%20to%20groestlcoin.org).

#### Why not Java 8 ?

At the time MGHD was being written (Q4 2013 - Q2 2014) Java 8 was not in production release and the sheer size of the packaged download was coming in at 150Mb (18x MultiGroestl
Classic and 3x the standard Java 7 packaged footprints). That footprint alone would be sufficient to dramatically increase the cost of serving the application and deter people
from downloading in countries where bandwidth is less available.

We will revisit this once we have suitable Install4j JREs available.

#### Why not JavaFX ?

JavaFX was only available as version 2.2 on Java 7 and the move to Java 8 was not going to happen. There were many significant features missing in JavaFX 2.2 which would only be
fixed in Java 8:

* no right to left languages (Hebrew, Farsi, Arabic, etc)
* no integration with native platform for Groestlcoin URI protocol handling (no BIP 21 or 72 support)
* no reporting uncaught exceptions (no error reporting)

Thus this technology was not suitable for the very wide range of people using MultiGroestl in all corners of the globe.

#### Why Swing ?

There is a vast amount of support for Swing. The code is near bullet-proof for most use cases and it fully supports internationalization which is a key requirement for MultiGroestl HD.
Also, many of the supporting libraries for Swing
pre-date 2009 making it much harder for [dependency chain attacks](http://gary-rowe.com/agilestack/2013/07/03/preventing-dependency-chain-attacks-in-maven/) to take place.

With some effort Swing can be made to look quite modern.

Swing also allows us to smoothly integrate with the native platform which puts it ahead of JavaFX until at least Q3 2015.

#### Why not SwingX ?

SwingX is a large support library that introduces a lot of additional functionality to Swing applications. Much of this additional functionality is not required by MultiGroestl or
can be relatively easily worked around. Consequently including it would increase the available attack surface.

#### Why the Nimbus look and feel ?

In Java 7 the Nimbus look and feel became integrated with the JDK. It provides a modern 2D rendered UI that is the same across all platforms. It is highly customisable through
simple themes and provides consistent painting behaviour across platforms. For example to paint a button red in Swing using the Mac-only Aqua theme requires complex custom
ButtonUI code.

Using Nimbus ensures that we don't have this or similar problems.

[Technical details on the default colours](http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary)

### Changing resource bundles

Use the `ResourceBundleTools` to find similar entries and to arrange keys in the same order across all bundles.

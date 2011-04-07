#!/usr/bin/env bash
if [ ! -e lib ]; then
  mkdir lib
fi
cd lib

# jMonkeyEngine 3
if [ ! -e jME.zip -a ! -e jME ]; then
  echo "Downloading jME ..."
  curl http://eregon.me/pylos/jME-light.zip -o jME.zip
fi

if [ ! -e jME ]; then
  mkdir jME
fi
cd jME

if [ ! -e jMonkeyEngine3.jar ]; then
  echo "Unpacking jME.zip"
  unzip -q ../jME.zip
  rm ../jME.zip
fi

# jUnit
cd ..

if [ ! -e junit-4.8.2.jar ]; then
  echo "Downloading jUnit ..."
  curl https://github.com/downloads/KentBeck/junit/junit-4.8.2.jar -o junit-4.8.2.jar
fi

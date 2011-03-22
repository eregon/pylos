#!/usr/bin/env bash
if [ ! -e lib ]; then
  mkdir lib
fi
cd lib

if [ ! -e jME.zip -a ! -e jME ]; then
  echo "Downloading jME ..."
  curl http://eregon.me/pylos/jME.zip -o jME.zip
fi

if [ ! -e jME ]; then
  mkdir jME
fi
cd jME

if [ ! -e jMonkeyEngine3.jar ]; then
  unzip -q ../jME.zip
fi

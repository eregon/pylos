#!/usr/bin/env bash
if [ ! -e lib ]; then
  mkdir lib
fi

cd lib

if [ ! -e jME.zip ]; then
  echo "Downloading jME ..."
  curl http://eregon.me/pylos/jME.zip -o jME.zip
fi

if [ ! -e jMonkeyEngine3.jar ]; then
  unzip -q jME.zip
fi

#!/usr/bin/env bash
if [ ! -e lib ]; then
  mkdir lib
fi

cd lib

if [ ! -e jME ]; then
  echo "Downloading jME ..."
  curl http://eregon.me/pylos/jME -o jME
fi

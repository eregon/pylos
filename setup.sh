#!/usr/bin/env bash
if [ ! -e lib ]; then
  mkdir lib
fi

if [ ! -e lib/jME ]; then
  curl http://eregon.me/pylos/jME -O jME
fi

#!/bin/bash

$HOME/.jabba/jabba.sh

if [ -z "$(jabba which adopt-openj9@1.11.0-2)" ]; then 
  jabba install adopt-openj9@1.11.0-2
fi

#!/bin/bash

. $HOME/.bash_profile

if [ -z "$(jabba which adopt-openj9@1.11.0-2)" ]; then 
  jabba install adopt-openj9@1.11.0-2
fi

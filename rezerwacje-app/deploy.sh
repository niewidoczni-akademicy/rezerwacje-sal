#!/bin/bash

#TODO: Check if remote exist before splitting
git push heroku-backend `git subtree split --prefix rezerwacje-app`:master --force

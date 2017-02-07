# Calculator v 3

This is my solution for the simple Calculator exercise, with the
added code to retain the last calculation done (input & result) between 
runtimes.   It also stil retains the state of the result displayed on the screen
from version 2.   

Shared preferences are used to save the current result in onStop() then
retrieve it in onCreate()

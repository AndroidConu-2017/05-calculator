# Calculator v 4

This is my solution for the simple Calculator exercise, with the
added code to retain the last calculation done (input & result) between 
runtimes.   It also  retains the state of the result displayed on the screen
from version 2.  It uses Shared preferences to save the current result
in onStop() then retrieve it in onCreate() from version 3

This version has alternate layouts for portrait and landscape and
it has different strings for French, defaulting to English
